package ch.m324.controller;

import ch.m324.model.KriteriumErfüllung;
import ch.m324.service.KriteriumErfüllungService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class KriteriumErfüllungController {
    
    private final KriteriumErfüllungService erfüllungService;
    
    public KriteriumErfüllungController(KriteriumErfüllungService erfüllungService) {
        this.erfüllungService = erfüllungService;
    }
    
    @GetMapping("/personen/{personId}/erfuellungen")
    public ResponseEntity<List<KriteriumErfüllung>> getErfüllungen(@PathVariable Long personId) {
        List<KriteriumErfüllung> erfüllungen = erfüllungService.getErfüllungenByPersonId(personId);
        return ResponseEntity.ok(erfüllungen);
    }
    
    @PutMapping("/kriterien/{kriteriumId}/anforderungen")
    public ResponseEntity<KriteriumErfüllung> updateAnforderungen(
            @PathVariable String kriteriumId,
            @RequestBody Map<String, Object> request) {
        
        Long personId = Long.valueOf(request.get("personId").toString());
        @SuppressWarnings("unchecked")
        List<Integer> erfüllteAnforderungen = (List<Integer>) request.get("erfüllteAnforderungen");
        String notizen = request.get("notizen") != null ? request.get("notizen").toString() : null;
        
        KriteriumErfüllung erfüllung = erfüllungService.saveOrUpdateErfüllung(
            personId, kriteriumId, erfüllteAnforderungen, notizen
        );
        
        return ResponseEntity.ok(erfüllung);
    }
}

