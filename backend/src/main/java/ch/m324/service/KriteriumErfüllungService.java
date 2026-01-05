package ch.m324.service;

import ch.m324.model.KriteriumErfüllung;
import ch.m324.repository.KriteriumErfüllungRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KriteriumErfüllungService {
    
    private final KriteriumErfüllungRepository erfüllungRepository;
    
    public KriteriumErfüllungService(KriteriumErfüllungRepository erfüllungRepository) {
        this.erfüllungRepository = erfüllungRepository;
    }
    
    public List<KriteriumErfüllung> getErfüllungenByPersonId(Long personId) {
        return erfüllungRepository.findByPersonId(personId);
    }
    
    public KriteriumErfüllung saveOrUpdateErfüllung(Long personId, String kriteriumId, 
                                                      List<Integer> erfüllteAnforderungen, 
                                                      String notizen) {
        Optional<KriteriumErfüllung> existing = erfüllungRepository
            .findByPersonIdAndKriteriumId(personId, kriteriumId);
        
        if (existing.isPresent()) {
            KriteriumErfüllung erfüllung = existing.get();
            erfüllung.setErfüllteAnforderungen(erfüllteAnforderungen);
            erfüllung.setNotizen(notizen);
            return erfüllungRepository.save(erfüllung);
        } else {
            KriteriumErfüllung neueErfüllung = new KriteriumErfüllung();
            neueErfüllung.setPersonId(personId);
            neueErfüllung.setKriteriumId(kriteriumId);
            neueErfüllung.setErfüllteAnforderungen(erfüllteAnforderungen);
            neueErfüllung.setNotizen(notizen);
            return erfüllungRepository.save(neueErfüllung);
        }
    }
}

