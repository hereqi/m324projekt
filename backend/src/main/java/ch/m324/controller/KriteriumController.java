package ch.m324.controller;

import ch.m324.model.Kriterium;
import ch.m324.repository.KriteriumRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kriterien")
@CrossOrigin(origins = "*")
public class KriteriumController {
    
    private final KriteriumRepository kriteriumRepository;
    
    public KriteriumController(KriteriumRepository kriteriumRepository) {
        this.kriteriumRepository = kriteriumRepository;
    }
    
    @GetMapping
    public ResponseEntity<List<Kriterium>> getAllKriterien() {
        List<Kriterium> kriterien = kriteriumRepository.findAll();
        return ResponseEntity.ok(kriterien);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Kriterium> getKriterium(@PathVariable String id) {
        return kriteriumRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}

