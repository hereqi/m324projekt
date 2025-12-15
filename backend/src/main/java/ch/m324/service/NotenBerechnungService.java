package ch.m324.service;

import ch.m324.model.Kriterium;
import ch.m324.model.KriteriumErfüllung;
import ch.m324.repository.KriteriumRepository;
import ch.m324.repository.KriteriumErfüllungRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotenBerechnungService {
    
    private final KriteriumRepository kriteriumRepository;
    private final KriteriumErfüllungRepository erfüllungRepository;
    
    public NotenBerechnungService(KriteriumRepository kriteriumRepository, 
                                  KriteriumErfüllungRepository erfüllungRepository) {
        this.kriteriumRepository = kriteriumRepository;
        this.erfüllungRepository = erfüllungRepository;
    }
    
    public Map<String, Object> berechneNoten(Long personId) {
        List<Kriterium> alleKriterien = kriteriumRepository.findAll();
        List<KriteriumErfüllung> erfüllungen = erfüllungRepository.findByPersonId(personId);
        
        Map<String, Integer> gütestufen = new HashMap<>();
        Map<String, Integer> teil1Gütestufen = new HashMap<>();
        Map<String, Integer> teil2Gütestufen = new HashMap<>();
        
        for (Kriterium kriterium : alleKriterien) {
            KriteriumErfüllung erfüllung = erfüllungen.stream()
                .filter(e -> e.getKriteriumId().equals(kriterium.getId()))
                .findFirst()
                .orElse(null);
            
            int erfüllteAnzahl = erfüllung != null ? erfüllung.getErfüllteAnforderungen().size() : 0;
            int gütestufe = berechneGütestufe(kriterium, erfüllteAnzahl);
            
            gütestufen.put(kriterium.getId(), gütestufe);
            
            if ("Teil1".equals(kriterium.getTeil())) {
                teil1Gütestufen.put(kriterium.getId(), gütestufe);
            } else if ("Teil2".equals(kriterium.getTeil())) {
                teil2Gütestufen.put(kriterium.getId(), gütestufe);
            }
        }
        
        double teil1Note = berechneNoteAusGütestufen(teil1Gütestufen);
        double teil2Note = berechneNoteAusGütestufen(teil2Gütestufen);
        
        Map<String, Object> result = new HashMap<>();
        result.put("gütestufen", gütestufen);
        result.put("teil1", Map.of("note", teil1Note, "gütestufen", teil1Gütestufen));
        result.put("teil2", Map.of("note", teil2Note, "gütestufen", teil2Gütestufen));
        
        return result;
    }
    
    private int berechneGütestufe(Kriterium kriterium, int erfüllteAnzahl) {
        if (kriterium.getGütestufen() == null || kriterium.getGütestufen().isEmpty()) {
            // Fallback: Standard-Berechnung
            int anzahlAnforderungen = kriterium.getAnforderungen().size();
            if (erfüllteAnzahl == anzahlAnforderungen) return 3;
            if (erfüllteAnzahl >= anzahlAnforderungen * 0.67) return 2;
            if (erfüllteAnzahl >= anzahlAnforderungen * 0.33) return 1;
            return 0;
        }
        
        // Gütestufe aus Mapping
        return kriterium.getGütestufen().entrySet().stream()
            .filter(e -> erfüllteAnzahl >= e.getKey())
            .mapToInt(Map.Entry::getValue)
            .max()
            .orElse(0);
    }
    
    private double berechneNoteAusGütestufen(Map<String, Integer> gütestufen) {
        if (gütestufen.isEmpty()) return 0.0;
        
        double summe = gütestufen.values().stream()
            .mapToInt(Integer::intValue)
            .sum();
        
        double maxSumme = gütestufen.size() * 3.0;
        
        // Note 1-6 basierend auf Prozentsatz
        return 1.0 + (summe / maxSumme) * 5.0;
    }
}

