package ch.m324.service;

import ch.m324.model.Criterion;
import ch.m324.model.CriterionProgress;
import ch.m324.repository.CriterionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GuetestufeServiceTest {
    
    @Mock
    private CriterionRepository criterionRepository;
    
    @InjectMocks
    private GuetestufeService guetestufeService;
    
    private Criterion testCriterion;
    
    @BeforeEach
    void setUp() {
        // Erstelle Test-Kriterium mit 6 Anforderungen
        List<String> anforderungen = Arrays.asList(
            "Anforderung 1",
            "Anforderung 2",
            "Anforderung 3",
            "Anforderung 4",
            "Anforderung 5",
            "Anforderung 6"
        );
        
        testCriterion = new Criterion("TEST01", "Test Kriterium", "Test Leitfrage", "Teil1", anforderungen);
    }
    
    @Test
    void testBerechneGuetestufe_0Anforderungen_Erfuellt() {
        // Arrange
        CriterionProgress progress = new CriterionProgress(1L, "TEST01");
        progress.setErfuellteAnforderungen(new ArrayList<>());
        
        when(criterionRepository.findById("TEST01")).thenReturn(Optional.of(testCriterion));
        
        // Act
        Integer guetestufe = guetestufeService.berechneGuetestufe(progress);
        
        // Assert
        assertEquals(0, guetestufe, "0 erfüllte Anforderungen sollte Gütestufe 0 ergeben");
    }
    
    @Test
    void testBerechneGuetestufe_1Anforderung_Erfuellt() {
        // Arrange
        CriterionProgress progress = new CriterionProgress(1L, "TEST01");
        progress.setErfuellteAnforderungen(Arrays.asList(0));
        
        when(criterionRepository.findById("TEST01")).thenReturn(Optional.of(testCriterion));
        
        // Act
        Integer guetestufe = guetestufeService.berechneGuetestufe(progress);
        
        // Assert
        assertEquals(1, guetestufe, "1 erfüllte Anforderung (16.7%) sollte Gütestufe 1 ergeben");
    }
    
    @Test
    void testBerechneGuetestufe_2Anforderungen_Erfuellt() {
        // Arrange
        CriterionProgress progress = new CriterionProgress(1L, "TEST01");
        progress.setErfuellteAnforderungen(Arrays.asList(0, 1));
        
        when(criterionRepository.findById("TEST01")).thenReturn(Optional.of(testCriterion));
        
        // Act
        Integer guetestufe = guetestufeService.berechneGuetestufe(progress);
        
        // Assert
        assertEquals(2, guetestufe, "2 erfüllte Anforderungen (33.3%) sollte Gütestufe 2 ergeben (>= 33%)");
    }
    
    @Test
    void testBerechneGuetestufe_3Anforderungen_Erfuellt() {
        // Arrange
        CriterionProgress progress = new CriterionProgress(1L, "TEST01");
        progress.setErfuellteAnforderungen(Arrays.asList(0, 1, 2));
        
        when(criterionRepository.findById("TEST01")).thenReturn(Optional.of(testCriterion));
        
        // Act
        Integer guetestufe = guetestufeService.berechneGuetestufe(progress);
        
        // Assert
        assertEquals(2, guetestufe, "3 erfüllte Anforderungen (50%) sollte Gütestufe 2 ergeben (33-66%)");
    }
    
    @Test
    void testBerechneGuetestufe_4Anforderungen_Erfuellt() {
        // Arrange
        CriterionProgress progress = new CriterionProgress(1L, "TEST01");
        progress.setErfuellteAnforderungen(Arrays.asList(0, 1, 2, 3));
        
        when(criterionRepository.findById("TEST01")).thenReturn(Optional.of(testCriterion));
        
        // Act
        Integer guetestufe = guetestufeService.berechneGuetestufe(progress);
        
        // Assert
        assertEquals(3, guetestufe, "4 erfüllte Anforderungen (66.7%) sollte Gütestufe 3 ergeben (>= 66%)");
    }
    
    @Test
    void testBerechneGuetestufe_5Anforderungen_Erfuellt() {
        // Arrange
        CriterionProgress progress = new CriterionProgress(1L, "TEST01");
        progress.setErfuellteAnforderungen(Arrays.asList(0, 1, 2, 3, 4));
        
        when(criterionRepository.findById("TEST01")).thenReturn(Optional.of(testCriterion));
        
        // Act
        Integer guetestufe = guetestufeService.berechneGuetestufe(progress);
        
        // Assert
        assertEquals(3, guetestufe, "5 erfüllte Anforderungen (83.3%) sollte Gütestufe 3 ergeben (> 66%)");
    }
    
    @Test
    void testBerechneGuetestufe_6Anforderungen_Erfuellt() {
        // Arrange
        CriterionProgress progress = new CriterionProgress(1L, "TEST01");
        progress.setErfuellteAnforderungen(Arrays.asList(0, 1, 2, 3, 4, 5));
        
        when(criterionRepository.findById("TEST01")).thenReturn(Optional.of(testCriterion));
        
        // Act
        Integer guetestufe = guetestufeService.berechneGuetestufe(progress);
        
        // Assert
        assertEquals(3, guetestufe, "6 erfüllte Anforderungen (100%) sollte Gütestufe 3 ergeben (> 66%)");
    }
    
    @Test
    void testBerechneGuetestufe_NullProgress() {
        // Act
        Integer guetestufe = guetestufeService.berechneGuetestufe(null);
        
        // Assert
        assertEquals(0, guetestufe, "Null Progress sollte Gütestufe 0 ergeben");
    }
    
    @Test
    void testBerechneGuetestufe_NullErfuellteAnforderungen() {
        // Arrange
        CriterionProgress progress = new CriterionProgress(1L, "TEST01");
        progress.setErfuellteAnforderungen(null);
        
        // Act
        Integer guetestufe = guetestufeService.berechneGuetestufe(progress);
        
        // Assert
        assertEquals(0, guetestufe, "Null erfüllte Anforderungen sollte Gütestufe 0 ergeben");
    }
    
    @Test
    void testBerechneGuetestufe_CriterionNichtGefunden() {
        // Arrange
        CriterionProgress progress = new CriterionProgress(1L, "UNKNOWN");
        progress.setErfuellteAnforderungen(Arrays.asList(0, 1));
        
        when(criterionRepository.findById("UNKNOWN")).thenReturn(Optional.empty());
        
        // Act
        Integer guetestufe = guetestufeService.berechneGuetestufe(progress);
        
        // Assert
        assertEquals(0, guetestufe, "Nicht gefundenes Kriterium sollte Gütestufe 0 ergeben");
    }
    
    @Test
    void testBerechneGuetestufe_CriterionOhneAnforderungen() {
        // Arrange
        Criterion criterionOhneAnforderungen = new Criterion("TEST02", "Test", "Leitfrage", "Teil1", new ArrayList<>());
        CriterionProgress progress = new CriterionProgress(1L, "TEST02");
        progress.setErfuellteAnforderungen(Arrays.asList(0));
        
        when(criterionRepository.findById("TEST02")).thenReturn(Optional.of(criterionOhneAnforderungen));
        
        // Act
        Integer guetestufe = guetestufeService.berechneGuetestufe(progress);
        
        // Assert
        assertEquals(0, guetestufe, "Kriterium ohne Anforderungen sollte Gütestufe 0 ergeben");
    }
    
    @Test
    void testBerechneGuetestufe_MitCriterionId_0Anforderungen() {
        // Arrange
        when(criterionRepository.findById("TEST01")).thenReturn(Optional.of(testCriterion));
        
        // Act
        Integer guetestufe = guetestufeService.berechneGuetestufe("TEST01", new ArrayList<>());
        
        // Assert
        assertEquals(0, guetestufe);
    }
    
    @Test
    void testBerechneGuetestufe_MitCriterionId_3Anforderungen() {
        // Arrange
        when(criterionRepository.findById("TEST01")).thenReturn(Optional.of(testCriterion));
        
        // Act
        Integer guetestufe = guetestufeService.berechneGuetestufe("TEST01", Arrays.asList(0, 1, 2));
        
        // Assert
        assertEquals(2, guetestufe, "3 von 6 Anforderungen (50%) sollte Gütestufe 2 ergeben");
    }
    
    @Test
    void testBerechneGuetestufe_MitCriterionId_6Anforderungen() {
        // Arrange
        when(criterionRepository.findById("TEST01")).thenReturn(Optional.of(testCriterion));
        
        // Act
        Integer guetestufe = guetestufeService.berechneGuetestufe("TEST01", Arrays.asList(0, 1, 2, 3, 4, 5));
        
        // Assert
        assertEquals(3, guetestufe, "6 von 6 Anforderungen (100%) sollte Gütestufe 3 ergeben");
    }
    
    @Test
    void testBerechneGuetestufe_MitCriterionId_NullListe() {
        // Act - Null-Check erfolgt vor Repository-Zugriff
        Integer guetestufe = guetestufeService.berechneGuetestufe("TEST01", null);
        
        // Assert
        assertEquals(0, guetestufe, "Null Liste sollte Gütestufe 0 ergeben");
    }
    
    @Test
    void testBerechneGuetestufe_Grenzfall_33Prozent() {
        // Arrange: 2 von 6 = 33.3% (sollte Gütestufe 2 sein, da >= 33%)
        CriterionProgress progress = new CriterionProgress(1L, "TEST01");
        progress.setErfuellteAnforderungen(Arrays.asList(0, 1));
        
        when(criterionRepository.findById("TEST01")).thenReturn(Optional.of(testCriterion));
        
        // Act
        Integer guetestufe = guetestufeService.berechneGuetestufe(progress);
        
        // Assert
        assertEquals(2, guetestufe, "33.3% sollte Gütestufe 2 ergeben (>= 33%)");
    }
    
    @Test
    void testBerechneGuetestufe_Grenzfall_66Prozent() {
        // Arrange: 4 von 6 = 66.7% (sollte Gütestufe 3 sein, da >= 66%)
        CriterionProgress progress = new CriterionProgress(1L, "TEST01");
        progress.setErfuellteAnforderungen(Arrays.asList(0, 1, 2, 3));
        
        when(criterionRepository.findById("TEST01")).thenReturn(Optional.of(testCriterion));
        
        // Act
        Integer guetestufe = guetestufeService.berechneGuetestufe(progress);
        
        // Assert
        assertEquals(3, guetestufe, "66.7% sollte Gütestufe 3 ergeben (>= 66%)");
    }
    
    @Test
    void testBerechneGuetestufe_Grenzfall_KnappUnter33Prozent() {
        // Arrange: 1 von 6 = 16.7% (sollte Gütestufe 1 sein, da < 33%)
        CriterionProgress progress = new CriterionProgress(1L, "TEST01");
        progress.setErfuellteAnforderungen(Arrays.asList(0));
        
        when(criterionRepository.findById("TEST01")).thenReturn(Optional.of(testCriterion));
        
        // Act
        Integer guetestufe = guetestufeService.berechneGuetestufe(progress);
        
        // Assert
        assertEquals(1, guetestufe, "16.7% sollte Gütestufe 1 ergeben (< 33%)");
    }
    
    @Test
    void testBerechneGuetestufe_Grenzfall_KnappUnter66Prozent() {
        // Arrange: 3 von 6 = 50% (sollte Gütestufe 2 sein, da < 66%)
        CriterionProgress progress = new CriterionProgress(1L, "TEST01");
        progress.setErfuellteAnforderungen(Arrays.asList(0, 1, 2));
        
        when(criterionRepository.findById("TEST01")).thenReturn(Optional.of(testCriterion));
        
        // Act
        Integer guetestufe = guetestufeService.berechneGuetestufe(progress);
        
        // Assert
        assertEquals(2, guetestufe, "50% sollte Gütestufe 2 ergeben (< 66%)");
    }
}

