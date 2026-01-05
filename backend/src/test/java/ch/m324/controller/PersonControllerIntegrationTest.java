package ch.m324.controller;

import ch.m324.model.Person;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class PersonControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() {
        personRepository.deleteAll();
    }
    
    @Test
    void testGetAllPersonen_Empty() throws Exception {
        mockMvc.perform(get("/api/personen"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));
    }
    
    @Test
    void testGetAllPersonen_WithData() throws Exception {
        Person person = new Person("Mustafa", "Sagaaro", "Test Thema", LocalDate.of(2026, 6, 15));
        personRepository.save(person);
        
        mockMvc.perform(get("/api/personen"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].name", is("Mustafa")))
            .andExpect(jsonPath("$[0].vorname", is("Sagaaro")));
    }
    
    @Test
    void testGetPersonById() throws Exception {
        Person person = new Person("Test", "User", "Thema", LocalDate.of(2026, 1, 1));
        person = personRepository.save(person);
        
        mockMvc.perform(get("/api/personen/" + person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(person.getId().intValue())))
            .andExpect(jsonPath("$.name", is("Test")))
            .andExpect(jsonPath("$.vorname", is("User")));
    }
    
    @Test
    void testGetPersonById_NotFound() throws Exception {
        mockMvc.perform(get("/api/personen/999"))
            .andExpect(status().isNotFound());
    }
    
    @Test
    void testCreatePerson() throws Exception {
        String requestBody = """
            {
                "name": "Neu",
                "vorname": "Person",
                "thema": "Test Thema",
                "abgabedatum": "2026-06-30"
            }
            """;
        
        mockMvc.perform(post("/api/personen")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", is("Neu")))
            .andExpect(jsonPath("$.vorname", is("Person")))
            .andExpect(jsonPath("$.thema", is("Test Thema")))
            .andExpect(jsonPath("$.id", notNullValue()));
    }
    
    @Test
    void testUpdatePerson() throws Exception {
        Person person = new Person("Alt", "Name", "Thema", LocalDate.of(2026, 1, 1));
        person = personRepository.save(person);
        
        String requestBody = """
            {
                "name": "Neu",
                "vorname": "Name",
                "thema": "Aktualisiertes Thema",
                "abgabedatum": "2026-12-31"
            }
            """;
        
        mockMvc.perform(put("/api/personen/" + person.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("Neu")))
            .andExpect(jsonPath("$.thema", is("Aktualisiertes Thema")));
    }
    
    @Test
    void testUpdatePerson_NotFound() throws Exception {
        String requestBody = """
            {
                "name": "Test",
                "vorname": "Test",
                "thema": "Test",
                "abgabedatum": "2026-01-01"
            }
            """;
        
        mockMvc.perform(put("/api/personen/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isNotFound());
    }
    
    @Test
    void testDeletePerson() throws Exception {
        Person person = new Person("Delete", "Test", "Thema", LocalDate.of(2026, 1, 1));
        person = personRepository.save(person);
        
        mockMvc.perform(delete("/api/personen/" + person.getId()))
            .andExpect(status().isNoContent());
        
        mockMvc.perform(get("/api/personen/" + person.getId()))
            .andExpect(status().isNotFound());
    }
    
    @Test
    void testDeletePerson_NotFound() throws Exception {
        mockMvc.perform(delete("/api/personen/999"))
            .andExpect(status().isNotFound());
    }
}

