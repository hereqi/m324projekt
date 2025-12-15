package ch.m324.controller;

import ch.m324.service.NotenBerechnungService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/noten")
@CrossOrigin(origins = "*")
public class NotenController {
    
    private final NotenBerechnungService notenBerechnungService;
    
    public NotenController(NotenBerechnungService notenBerechnungService) {
        this.notenBerechnungService = notenBerechnungService;
    }
    
    @GetMapping("/{personId}")
    public ResponseEntity<Map<String, Object>> getNoten(@PathVariable Long personId) {
        Map<String, Object> noten = notenBerechnungService.berechneNoten(personId);
        return ResponseEntity.ok(noten);
    }
}

