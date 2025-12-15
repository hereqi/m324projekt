package ch.m324.service;

import ch.m324.model.Kriterium;
import ch.m324.repository.KriteriumRepository;
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
    private static final String KRITERIEN_JSON_PATH = "kriterien.json";
    
    private final KriteriumRepository kriteriumRepository;
    private final ObjectMapper objectMapper;
    
    public KriterienLoaderService(KriteriumRepository kriteriumRepository, ObjectMapper objectMapper) {
        this.kriteriumRepository = kriteriumRepository;
        this.objectMapper = objectMapper;
    }
    
    @PostConstruct
    public void loadKriterien() {
        try {
            ClassPathResource resource = new ClassPathResource(KRITERIEN_JSON_PATH);
            InputStream inputStream = resource.getInputStream();
            
            List<Kriterium> kriterien = objectMapper.readValue(
                inputStream,
                new TypeReference<List<Kriterium>>() {}
            );
            
            // Nur laden wenn noch keine Kriterien in DB sind
            if (kriteriumRepository.count() == 0) {
                kriteriumRepository.saveAll(kriterien);
                logger.info("{} Kriterien erfolgreich aus JSON geladen", kriterien.size());
            } else {
                logger.info("Kriterien bereits in Datenbank vorhanden, überspringe Laden");
            }
            
        } catch (IOException e) {
            logger.error("Fehler beim Laden der Kriterien aus JSON: {}", e.getMessage(), e);
        }
    }
}

