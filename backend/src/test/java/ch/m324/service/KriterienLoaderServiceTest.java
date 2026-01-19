package ch.m324.service;

import ch.m324.model.Criterion;
import ch.m324.repository.CriterionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration-Tests für KriterienLoaderService.
 * Testet das Laden von Kriterien aus JSON.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class KriterienLoaderServiceTest {
    
    @Autowired
    private KriterienLoaderService kriterienLoaderService;
    
    @Autowired
    private CriterionRepository criterionRepository;
    
    @BeforeEach
    void setUp() {
        criterionRepository.deleteAll();
    }
    
    @Test
    void testLoadCriteriaFromJson_LoadsCriteria() {
        // Act
        kriterienLoaderService.loadCriteriaFromJson();
        
        // Assert
        List<Criterion> criteria = criterionRepository.findAll();
        assertFalse(criteria.isEmpty(), "Kriterien sollten geladen worden sein");
    }
    
    @Test
    void testLoadCriteriaFromJson_DoesNotLoadIfAlreadyExists() {
        // Arrange - Lade einmal
        kriterienLoaderService.loadCriteriaFromJson();
        long countAfterFirstLoad = criterionRepository.count();
        
        // Act - Lade erneut (sollte übersprungen werden)
        kriterienLoaderService.loadCriteriaFromJson();
        long countAfterSecondLoad = criterionRepository.count();
        
        // Assert - Anzahl sollte gleich sein
        assertEquals(countAfterFirstLoad, countAfterSecondLoad, 
            "Kriterien sollten nicht doppelt geladen werden");
    }
    
    @Test
    void testReloadCriteria_ReloadsAll() {
        // Arrange - Lade initial
        kriterienLoaderService.loadCriteriaFromJson();
        long initialCount = criterionRepository.count();
        
        // Act - Lade neu
        kriterienLoaderService.reloadCriteria();
        long afterReloadCount = criterionRepository.count();
        
        // Assert - Anzahl sollte gleich sein (wurden neu geladen)
        assertEquals(initialCount, afterReloadCount, 
            "Nach Reload sollten gleich viele Kriterien vorhanden sein");
    }
    
    @Test
    void testReloadCriteria_DeletesAndReloads() {
        // Arrange - Lade initial
        kriterienLoaderService.loadCriteriaFromJson();
        
        // Füge manuell ein Kriterium hinzu
        Criterion extraCriterion = new Criterion(
            "EXTRA01", 
            "Extra", 
            "Leitfrage", 
            "Teil1", 
            List.of("A1", "A2")
        );
        criterionRepository.save(extraCriterion);
        long countWithExtra = criterionRepository.count();
        
        // Act - Reload sollte das Extra-Kriterium löschen
        kriterienLoaderService.reloadCriteria();
        
        // Assert - Extra-Kriterium sollte weg sein
        assertFalse(criterionRepository.existsById("EXTRA01"), 
            "Extra-Kriterium sollte nach Reload nicht mehr existieren");
    }
    
    @Test
    void testLoadCriteriaFromJson_LoadsCorrectData() {
        // Act
        kriterienLoaderService.loadCriteriaFromJson();
        
        // Assert - Prüfe ob C02 existiert (aus criteria.json)
        assertTrue(criterionRepository.existsById("C02"), 
            "Kriterium C02 sollte geladen worden sein");
        
        Criterion c02 = criterionRepository.findById("C02").orElse(null);
        assertNotNull(c02);
        assertEquals("Datenmodell entwickeln", c02.getTitel());
        assertEquals("Teil1", c02.getTeil());
        assertFalse(c02.getAnforderungen().isEmpty());
    }
}
