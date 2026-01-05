package ch.m324.controller;

import ch.m324.model.Criterion;
import ch.m324.repository.CriterionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class CriterionControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private CriterionRepository criterionRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() {
        criterionRepository.deleteAll();
        
        // Erstelle Test-Kriterien
        Criterion criterion1 = new Criterion(
            "C02",
            "Datenmodell entwickeln",
            "Test Leitfrage",
            "Teil1",
            Arrays.asList("Anforderung 1", "Anforderung 2", "Anforderung 3", "Anforderung 4", "Anforderung 5", "Anforderung 6")
        );
        
        Criterion criterion2 = new Criterion(
            "B05",
            "Backend-Architektur implementieren",
            "Test Leitfrage 2",
            "Teil2",
            Arrays.asList("Anforderung 1", "Anforderung 2", "Anforderung 3")
        );
        
        criterionRepository.saveAll(Arrays.asList(criterion1, criterion2));
    }
    
    @Test
    void testGetAllCriteria() throws Exception {
        mockMvc.perform(get("/api/criteria"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is("C02")))
            .andExpect(jsonPath("$[0].titel", is("Datenmodell entwickeln")))
            .andExpect(jsonPath("$[0].teil", is("Teil1")))
            .andExpect(jsonPath("$[0].anforderungen", hasSize(6)))
            .andExpect(jsonPath("$[1].id", is("B05")))
            .andExpect(jsonPath("$[1].teil", is("Teil2")));
    }
    
    @Test
    void testGetCriterionById() throws Exception {
        mockMvc.perform(get("/api/criteria/C02"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is("C02")))
            .andExpect(jsonPath("$.titel", is("Datenmodell entwickeln")))
            .andExpect(jsonPath("$.teil", is("Teil1")))
            .andExpect(jsonPath("$.anforderungen", hasSize(6)));
    }
    
    @Test
    void testGetCriterionById_NotFound() throws Exception {
        mockMvc.perform(get("/api/criteria/UNKNOWN"))
            .andExpect(status().isNotFound());
    }
    
    @Test
    void testGetCriteriaByTeil() throws Exception {
        mockMvc.perform(get("/api/criteria/teil/Teil1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is("C02")))
            .andExpect(jsonPath("$[0].teil", is("Teil1")));
    }
    
    @Test
    void testGetCriteriaByTeil_Teil2() throws Exception {
        mockMvc.perform(get("/api/criteria/teil/Teil2"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is("B05")))
            .andExpect(jsonPath("$[0].teil", is("Teil2")));
    }
    
    @Test
    void testGetCriteriaByTeil_Empty() throws Exception {
        mockMvc.perform(get("/api/criteria/teil/Teil3"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));
    }
    
    @Test
    void testReloadCriteria() throws Exception {
        mockMvc.perform(post("/api/criteria/reload"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("erfolgreich")));
    }
}

