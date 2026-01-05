package ch.m324.controller;

import ch.m324.dto.response.CriterionResponse;
import ch.m324.model.Criterion;
import ch.m324.repository.CriterionRepository;
import ch.m324.service.KriterienLoaderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/criteria")
@CrossOrigin(origins = "*")
public class CriterionController {
    
    private final CriterionRepository criterionRepository;
    private final KriterienLoaderService kriterienLoaderService;
    
    public CriterionController(CriterionRepository criterionRepository, KriterienLoaderService kriterienLoaderService) {
        this.criterionRepository = criterionRepository;
        this.kriterienLoaderService = kriterienLoaderService;
    }
    
    /**
     * GET /api/criteria
     * Lädt alle Kriterien aus der Datenbank.
     */
    @GetMapping
    public ResponseEntity<List<CriterionResponse>> getAllCriteria() {
        List<Criterion> criteria = criterionRepository.findAll();
        List<CriterionResponse> responses = criteria.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    /**
     * GET /api/criteria/{id}
     * Lädt ein spezifisches Kriterium.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CriterionResponse> getCriterionById(@PathVariable String id) {
        return criterionRepository.findById(id)
            .map(criterion -> ResponseEntity.ok(mapToResponse(criterion)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET /api/criteria/teil/{teil}
     * Lädt alle Kriterien für einen bestimmten Teil (Teil1, Teil2, Dokumentation).
     */
    @GetMapping("/teil/{teil}")
    public ResponseEntity<List<CriterionResponse>> getCriteriaByTeil(@PathVariable String teil) {
        List<Criterion> criteria = criterionRepository.findByTeil(teil);
        List<CriterionResponse> responses = criteria.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    /**
     * POST /api/criteria/reload
     * Lädt die Kriterien neu aus der JSON-Datei.
     */
    @PostMapping("/reload")
    public ResponseEntity<String> reloadCriteria() {
        try {
            kriterienLoaderService.reloadCriteria();
            return ResponseEntity.ok("Kriterien erfolgreich neu geladen");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Fehler beim Neuladen der Kriterien: " + e.getMessage());
        }
    }
    
    /**
     * Mappt Entity zu Response DTO.
     */
    private CriterionResponse mapToResponse(Criterion criterion) {
        CriterionResponse response = new CriterionResponse();
        response.setId(criterion.getId());
        response.setTitel(criterion.getTitel());
        response.setLeitfrage(criterion.getLeitfrage());
        response.setTeil(criterion.getTeil());
        response.setAnforderungen(criterion.getAnforderungen());
        return response;
    }
}

