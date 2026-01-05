package ch.m324.service;

import ch.m324.model.Criterion;
import ch.m324.repository.CriterionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class KriterienLoaderService {
    
    private static final Logger logger = LoggerFactory.getLogger(KriterienLoaderService.class);
    private static final String CRITERIA_JSON_PATH = "criteria.json";
    
    private final CriterionRepository criterionRepository;
    private final ObjectMapper objectMapper;
    
    public KriterienLoaderService(CriterionRepository criterionRepository, ObjectMapper objectMapper) {
        this.criterionRepository = criterionRepository;
        this.objectMapper = objectMapper;
    }
    
    @PostConstruct
    public void loadCriteriaFromJson() {
        try {
            ClassPathResource resource = new ClassPathResource(CRITERIA_JSON_PATH);
            InputStream inputStream = resource.getInputStream();
            
            List<Criterion> criteria = objectMapper.readValue(
                inputStream,
                new TypeReference<List<Criterion>>() {}
            );
            
            // Prüfe ob Kriterien bereits geladen wurden
            if (criterionRepository.count() == 0) {
                criterionRepository.saveAll(criteria);
                logger.info("{} Kriterien erfolgreich aus JSON geladen", criteria.size());
            } else {
                logger.info("Kriterien bereits vorhanden, überspringe Laden");
            }
            
        } catch (IOException e) {
            logger.error("Fehler beim Laden der Kriterien aus JSON: {}", e.getMessage(), e);
        }
    }
    
    public void reloadCriteria() {
        try {
            ClassPathResource resource = new ClassPathResource(CRITERIA_JSON_PATH);
            InputStream inputStream = resource.getInputStream();
            
            List<Criterion> criteria = objectMapper.readValue(
                inputStream,
                new TypeReference<List<Criterion>>() {}
            );
            
            // Lösche alle bestehenden Kriterien und lade neu
            criterionRepository.deleteAll();
            criterionRepository.saveAll(criteria);
            logger.info("{} Kriterien erfolgreich neu geladen", criteria.size());
            
        } catch (IOException e) {
            logger.error("Fehler beim Neuladen der Kriterien aus JSON: {}", e.getMessage(), e);
            throw new RuntimeException("Fehler beim Neuladen der Kriterien", e);
        }
    }
}

