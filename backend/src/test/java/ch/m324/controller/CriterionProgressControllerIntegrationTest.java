package ch.m324.controller;

import ch.m324.model.Criterion;
import ch.m324.model.Person;
import ch.m324.repository.CriterionProgressRepository;
import ch.m324.repository.CriterionRepository;
import ch.m324.repository.PersonRepository;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class CriterionProgressControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private CriterionRepository criterionRepository;
    
    @Autowired
    private CriterionProgressRepository criterionProgressRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private Person testPerson;
    private Criterion testCriterion;
    
    @BeforeEach
    void setUp() {
        criterionProgressRepository.deleteAll();
        criterionRepository.deleteAll();
        personRepository.deleteAll();
        
        // Erstelle Test-Person
        testPerson = new Person("Mustafa", "Sagaaro", "Kriterien-Tracking", LocalDate.of(2026, 6, 15));
        testPerson = personRepository.save(testPerson);
        
        // Erstelle Test-Kriterium
        testCriterion = new Criterion(
            "C02",
            "Datenmodell entwickeln",
            "Test Leitfrage",
            "Teil1",
            Arrays.asList("Anforderung 1", "Anforderung 2", "Anforderung 3", "Anforderung 4", "Anforderung 5", "Anforderung 6")
        );
        testCriterion = criterionRepository.save(testCriterion);
    }
    
    @Test
    void testPostProgress_CreateNew() throws Exception {
        String requestBody = """
            {
                "personId": %d,
                "criterionId": "C02",
                "erfuellteAnforderungen": [0, 1, 2],
                "notizen": "Test Notizen"
            }
            """.formatted(testPerson.getId());
        
        mockMvc.perform(post("/api/criterion-progress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.personId", is(testPerson.getId().intValue())))
            .andExpect(jsonPath("$.criterionId", is("C02")))
            .andExpect(jsonPath("$.erfuellteAnforderungen", hasSize(3)))
            .andExpect(jsonPath("$.erfuellteAnforderungen[0]", is(0)))
            .andExpect(jsonPath("$.erfuellteAnforderungen[1]", is(1)))
            .andExpect(jsonPath("$.erfuellteAnforderungen[2]", is(2)))
            .andExpect(jsonPath("$.notizen", is("Test Notizen")))
            .andExpect(jsonPath("$.guetestufe", is(2))); // 3 von 6 = 50% = Gütestufe 2
    }
    
    @Test
    void testPostProgress_UpdateExisting() throws Exception {
        // Erstelle zunächst einen Fortschritt
        String initialBody = """
            {
                "personId": %d,
                "criterionId": "C02",
                "erfuellteAnforderungen": [0, 1],
                "notizen": "Initial Notizen"
            }
            """.formatted(testPerson.getId());
        
        mockMvc.perform(post("/api/criterion-progress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(initialBody))
            .andExpect(status().isCreated());
        
        // Aktualisiere den Fortschritt
        String updateBody = """
            {
                "personId": %d,
                "criterionId": "C02",
                "erfuellteAnforderungen": [0, 1, 2, 3, 4],
                "notizen": "Aktualisierte Notizen"
            }
            """.formatted(testPerson.getId());
        
        mockMvc.perform(post("/api/criterion-progress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateBody))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.erfuellteAnforderungen", hasSize(5)))
            .andExpect(jsonPath("$.notizen", is("Aktualisierte Notizen")))
            .andExpect(jsonPath("$.guetestufe", is(3))); // 5 von 6 = 83.3% = Gütestufe 3
    }
    
    @Test
    void testPostProgress_InvalidPersonId() throws Exception {
        String requestBody = """
            {
                "personId": 99999,
                "criterionId": "C02",
                "erfuellteAnforderungen": [0, 1]
            }
            """;
        
        mockMvc.perform(post("/api/criterion-progress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    void testGetProgressByPersonId() throws Exception {
        // Erstelle Fortschritt
        String requestBody = """
            {
                "personId": %d,
                "criterionId": "C02",
                "erfuellteAnforderungen": [0, 1, 2],
                "notizen": "Test"
            }
            """.formatted(testPerson.getId());
        
        mockMvc.perform(post("/api/criterion-progress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isCreated());
        
        // Hole Fortschritt
        mockMvc.perform(get("/api/criterion-progress/person/" + testPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].personId", is(testPerson.getId().intValue())))
            .andExpect(jsonPath("$[0].criterionId", is("C02")))
            .andExpect(jsonPath("$[0].guetestufe", is(2)));
    }
    
    @Test
    void testGetProgressByPersonAndCriterion() throws Exception {
        // Erstelle Fortschritt
        String requestBody = """
            {
                "personId": %d,
                "criterionId": "C02",
                "erfuellteAnforderungen": [0, 1, 2, 3],
                "notizen": "Test Notizen"
            }
            """.formatted(testPerson.getId());
        
        mockMvc.perform(post("/api/criterion-progress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isCreated());
        
        // Hole spezifischen Fortschritt
        mockMvc.perform(get("/api/criterion-progress/person/" + testPerson.getId() + "/criterion/C02"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.personId", is(testPerson.getId().intValue())))
            .andExpect(jsonPath("$.criterionId", is("C02")))
            .andExpect(jsonPath("$.erfuellteAnforderungen", hasSize(4)))
            .andExpect(jsonPath("$.notizen", is("Test Notizen")))
            .andExpect(jsonPath("$.guetestufe", is(3))); // 4 von 6 = 66.7% = Gütestufe 3
    }
    
    @Test
    void testGetProgressByPersonAndCriterion_NotFound() throws Exception {
        mockMvc.perform(get("/api/criterion-progress/person/" + testPerson.getId() + "/criterion/UNKNOWN"))
            .andExpect(status().isNotFound());
    }
    
    @Test
    void testPostProgress_AllAnforderungenErfuellt() throws Exception {
        String requestBody = """
            {
                "personId": %d,
                "criterionId": "C02",
                "erfuellteAnforderungen": [0, 1, 2, 3, 4, 5],
                "notizen": "Alle erfüllt"
            }
            """.formatted(testPerson.getId());
        
        mockMvc.perform(post("/api/criterion-progress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.erfuellteAnforderungen", hasSize(6)))
            .andExpect(jsonPath("$.guetestufe", is(3))); // 6 von 6 = 100% = Gütestufe 3
    }
    
    @Test
    void testPostProgress_KeineAnforderungenErfuellt() throws Exception {
        String requestBody = """
            {
                "personId": %d,
                "criterionId": "C02",
                "erfuellteAnforderungen": [],
                "notizen": "Keine erfüllt"
            }
            """.formatted(testPerson.getId());
        
        mockMvc.perform(post("/api/criterion-progress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.erfuellteAnforderungen", hasSize(0)))
            .andExpect(jsonPath("$.guetestufe", is(0))); // 0 von 6 = 0% = Gütestufe 0
    }
}

