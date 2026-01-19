package ch.m324.service;

import ch.m324.dto.request.CriterionProgressRequest;
import ch.m324.dto.response.CriterionProgressResponse;
import ch.m324.model.Criterion;
import ch.m324.model.CriterionProgress;
import ch.m324.model.Person;
import ch.m324.repository.CriterionProgressRepository;
import ch.m324.repository.CriterionRepository;
import ch.m324.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration-Tests für CriterionProgressService.
 * Testet die Business-Logik für Fortschrittsverwaltung.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CriterionProgressServiceTest {
    
    @Autowired
    private CriterionProgressService criterionProgressService;
    
    @Autowired
    private CriterionProgressRepository criterionProgressRepository;
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private CriterionRepository criterionRepository;
    
    private Person testPerson;
    private Criterion testCriterion;
    
    @BeforeEach
    void setUp() {
        criterionProgressRepository.deleteAll();
        criterionRepository.deleteAll();
        personRepository.deleteAll();
        
        // Erstelle Test-Person
        testPerson = new Person("Mustafa", "Sagaaro", "Test Thema", LocalDate.of(2026, 6, 15));
        testPerson = personRepository.save(testPerson);
        
        // Erstelle Test-Kriterium
        testCriterion = new Criterion(
            "TEST01",
            "Test Kriterium",
            "Test Leitfrage",
            "Teil1",
            new ArrayList<>(List.of("Anforderung 1", "Anforderung 2", "Anforderung 3", 
                         "Anforderung 4", "Anforderung 5", "Anforderung 6"))
        );
        testCriterion = criterionRepository.save(testCriterion);
    }
    
    @Test
    void testSaveOrUpdateProgress_NewProgress() {
        // Arrange
        CriterionProgressRequest request = new CriterionProgressRequest();
        request.setPersonId(testPerson.getId());
        request.setCriterionId("TEST01");
        request.setErfuellteAnforderungen(new ArrayList<>(List.of(0, 1, 2)));
        request.setNotizen("Test Notizen");
        
        // Act
        CriterionProgressResponse response = criterionProgressService.saveOrUpdateProgress(request);
        
        // Assert
        assertNotNull(response);
        assertEquals(testPerson.getId(), response.getPersonId());
        assertEquals("TEST01", response.getCriterionId());
        assertEquals(3, response.getErfuellteAnforderungen().size());
        assertEquals(2, response.getGuetestufe()); // 3 von 6 = 50% = Gütestufe 2
        assertEquals("Test Notizen", response.getNotizen());
    }
    
    @Test
    void testSaveOrUpdateProgress_UpdateExisting() {
        // Erstelle initialen Fortschritt
        CriterionProgressRequest initialRequest = new CriterionProgressRequest();
        initialRequest.setPersonId(testPerson.getId());
        initialRequest.setCriterionId("TEST01");
        initialRequest.setErfuellteAnforderungen(new java.util.ArrayList<>(List.of(0)));
        initialRequest.setNotizen("Alte Notizen");
        criterionProgressService.saveOrUpdateProgress(initialRequest);
        
        // Aktualisiere
        CriterionProgressRequest updateRequest = new CriterionProgressRequest();
        updateRequest.setPersonId(testPerson.getId());
        updateRequest.setCriterionId("TEST01");
        updateRequest.setErfuellteAnforderungen(new java.util.ArrayList<>(List.of(0, 1, 2, 3, 4)));
        updateRequest.setNotizen("Neue Notizen");
        
        // Act
        CriterionProgressResponse response = criterionProgressService.saveOrUpdateProgress(updateRequest);
        
        // Assert
        assertEquals(5, response.getErfuellteAnforderungen().size());
        assertEquals(3, response.getGuetestufe()); // 5 von 6 = 83% = Gütestufe 3
        assertEquals("Neue Notizen", response.getNotizen());
    }
    
    @Test
    void testSaveOrUpdateProgress_PersonNotFound() {
        // Arrange
        CriterionProgressRequest request = new CriterionProgressRequest();
        request.setPersonId(99999L); // Nicht existierende Person
        request.setCriterionId("TEST01");
        request.setErfuellteAnforderungen(new ArrayList<>(List.of(0, 1)));
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> criterionProgressService.saveOrUpdateProgress(request)
        );
        
        assertTrue(exception.getMessage().contains("existiert nicht"));
    }
    
    @Test
    void testGetProgressByPersonId() {
        // Erstelle Fortschritt
        CriterionProgressRequest request = new CriterionProgressRequest();
        request.setPersonId(testPerson.getId());
        request.setCriterionId("TEST01");
        request.setErfuellteAnforderungen(new ArrayList<>(List.of(0, 1, 2)));
        request.setNotizen("Test");
        criterionProgressService.saveOrUpdateProgress(request);
        
        // Act
        List<CriterionProgressResponse> responses = 
            criterionProgressService.getProgressByPersonId(testPerson.getId());
        
        // Assert
        assertEquals(1, responses.size());
        assertEquals("TEST01", responses.get(0).getCriterionId());
        assertEquals(2, responses.get(0).getGuetestufe());
    }
    
    @Test
    void testGetProgressByPersonId_EmptyList() {
        // Act
        List<CriterionProgressResponse> responses = 
            criterionProgressService.getProgressByPersonId(testPerson.getId());
        
        // Assert
        assertTrue(responses.isEmpty());
    }
    
    @Test
    void testGetProgressByPersonAndCriterion_Found() {
        // Erstelle Fortschritt
        CriterionProgressRequest request = new CriterionProgressRequest();
        request.setPersonId(testPerson.getId());
        request.setCriterionId("TEST01");
        request.setErfuellteAnforderungen(new ArrayList<>(List.of(0, 1, 2, 3)));
        request.setNotizen("Test Notizen");
        criterionProgressService.saveOrUpdateProgress(request);
        
        // Act
        Optional<CriterionProgressResponse> response = 
            criterionProgressService.getProgressByPersonAndCriterion(testPerson.getId(), "TEST01");
        
        // Assert
        assertTrue(response.isPresent());
        assertEquals("TEST01", response.get().getCriterionId());
        assertEquals(3, response.get().getGuetestufe()); // 4 von 6 = 66.7% = Gütestufe 3
    }
    
    @Test
    void testGetProgressByPersonAndCriterion_NotFound() {
        // Act
        Optional<CriterionProgressResponse> response = 
            criterionProgressService.getProgressByPersonAndCriterion(testPerson.getId(), "UNKNOWN");
        
        // Assert
        assertTrue(response.isEmpty());
    }
    
    @Test
    void testSaveOrUpdateProgress_NullErfuellteAnforderungen() {
        // Arrange
        CriterionProgressRequest request = new CriterionProgressRequest();
        request.setPersonId(testPerson.getId());
        request.setCriterionId("TEST01");
        request.setErfuellteAnforderungen(null);
        request.setNotizen("Test");
        
        // Act
        CriterionProgressResponse response = criterionProgressService.saveOrUpdateProgress(request);
        
        // Assert
        assertNotNull(response);
        assertEquals(0, response.getGuetestufe()); // Keine Anforderungen = Gütestufe 0
    }
    
    @Test
    void testSaveOrUpdateProgress_AllAnforderungenErfuellt() {
        // Arrange
        CriterionProgressRequest request = new CriterionProgressRequest();
        request.setPersonId(testPerson.getId());
        request.setCriterionId("TEST01");
        request.setErfuellteAnforderungen(new ArrayList<>(List.of(0, 1, 2, 3, 4, 5)));
        request.setNotizen("Alle erfüllt");
        
        // Act
        CriterionProgressResponse response = criterionProgressService.saveOrUpdateProgress(request);
        
        // Assert
        assertEquals(6, response.getErfuellteAnforderungen().size());
        assertEquals(3, response.getGuetestufe()); // 100% = Gütestufe 3
    }
    
    @Test
    void testSaveOrUpdateProgress_KeineAnforderungenErfuellt() {
        // Arrange
        CriterionProgressRequest request = new CriterionProgressRequest();
        request.setPersonId(testPerson.getId());
        request.setCriterionId("TEST01");
        request.setErfuellteAnforderungen(new ArrayList<>());
        request.setNotizen("Keine erfüllt");
        
        // Act
        CriterionProgressResponse response = criterionProgressService.saveOrUpdateProgress(request);
        
        // Assert
        assertEquals(0, response.getErfuellteAnforderungen().size());
        assertEquals(0, response.getGuetestufe()); // 0% = Gütestufe 0
    }
    
    @Test
    void testGetProgressByPersonId_MultipleProgress() {
        // Erstelle zweites Kriterium
        Criterion criterion2 = new Criterion(
            "TEST02",
            "Test Kriterium 2",
            "Leitfrage 2",
            "Teil2",
            new ArrayList<>(List.of("A1", "A2", "A3", "A4", "A5", "A6"))
        );
        criterionRepository.save(criterion2);
        
        // Erstelle Fortschritt für beide Kriterien
        CriterionProgressRequest request1 = new CriterionProgressRequest();
        request1.setPersonId(testPerson.getId());
        request1.setCriterionId("TEST01");
        request1.setErfuellteAnforderungen(new ArrayList<>(List.of(0, 1, 2)));
        criterionProgressService.saveOrUpdateProgress(request1);
        
        CriterionProgressRequest request2 = new CriterionProgressRequest();
        request2.setPersonId(testPerson.getId());
        request2.setCriterionId("TEST02");
        request2.setErfuellteAnforderungen(new ArrayList<>(List.of(0, 1)));
        criterionProgressService.saveOrUpdateProgress(request2);
        
        // Act
        List<CriterionProgressResponse> responses = 
            criterionProgressService.getProgressByPersonId(testPerson.getId());
        
        // Assert
        assertEquals(2, responses.size());
    }
}
