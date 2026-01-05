package ch.m324.service;

import ch.m324.model.Criterion;
import ch.m324.model.CriterionProgress;
import ch.m324.repository.CriterionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuetestufeService {
    
    private final CriterionRepository criterionRepository;
    
    public GuetestufeService(CriterionRepository criterionRepository) {
        this.criterionRepository = criterionRepository;
    }
    
    /**
     * Berechnet die Gütestufe (0-3) basierend auf erfüllten Anforderungen.
     * 
     * Gütestufen-Berechnung:
     * - 0: Keine Anforderungen erfüllt
     * - 1: 1-2 Anforderungen erfüllt (oder weniger als 33%)
     * - 2: 3-4 Anforderungen erfüllt (oder 33-66%)
     * - 3: Alle oder fast alle Anforderungen erfüllt (mehr als 66%)
     * 
     * @param criterionProgress Der Fortschritt mit erfüllten Anforderungen
     * @return Die berechnete Gütestufe (0-3)
     */
    public Integer berechneGuetestufe(CriterionProgress criterionProgress) {
        if (criterionProgress == null || criterionProgress.getErfuellteAnforderungen() == null) {
            return 0;
        }
        
        // Hole das Kriterium um die Gesamtzahl der Anforderungen zu kennen
        Criterion criterion = criterionRepository.findById(criterionProgress.getCriterionId())
            .orElse(null);
        
        if (criterion == null || criterion.getAnforderungen() == null) {
            return 0;
        }
        
        int anzahlErfuellt = criterionProgress.getErfuellteAnforderungen().size();
        int anzahlGesamt = criterion.getAnforderungen().size();
        
        if (anzahlGesamt == 0) {
            return 0;
        }
        
        // Berechne Prozentsatz
        double prozent = (double) anzahlErfuellt / anzahlGesamt * 100;
        
        // Bestimme Gütestufe basierend auf Prozentsatz
        if (prozent == 0) {
            return 0;
        } else if (prozent < 33.0) {
            return 1;
        } else if (prozent < 66.0) {
            return 2;
        } else {
            return 3;
        }
    }
    
    /**
     * Berechnet die Gütestufe für eine Liste von erfüllten Anforderungs-Indizes.
     * 
     * @param criterionId Die ID des Kriteriums
     * @param erfuellteAnforderungen Liste der Indizes der erfüllten Anforderungen
     * @return Die berechnete Gütestufe (0-3)
     */
    public Integer berechneGuetestufe(String criterionId, List<Integer> erfuellteAnforderungen) {
        if (erfuellteAnforderungen == null) {
            return 0;
        }
        
        Criterion criterion = criterionRepository.findById(criterionId)
            .orElse(null);
        
        if (criterion == null || criterion.getAnforderungen() == null) {
            return 0;
        }
        
        int anzahlErfuellt = erfuellteAnforderungen.size();
        int anzahlGesamt = criterion.getAnforderungen().size();
        
        if (anzahlGesamt == 0) {
            return 0;
        }
        
        // Berechne Prozentsatz
        double prozent = (double) anzahlErfuellt / anzahlGesamt * 100;
        
        // Bestimme Gütestufe basierend auf Prozentsatz
        if (prozent == 0) {
            return 0;
        } else if (prozent < 33.0) {
            return 1;
        } else if (prozent < 66.0) {
            return 2;
        } else {
            return 3;
        }
    }
}

