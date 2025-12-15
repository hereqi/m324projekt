package ch.m324.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kriterium_erfuellungen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KriteriumErfüllung {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long personId;
    
    @Column(nullable = false)
    private String kriteriumId;
    
    @ElementCollection
    @CollectionTable(name = "erfuellte_anforderungen", joinColumns = @JoinColumn(name = "erfuellung_id"))
    @Column(name = "anforderung_index")
    private List<Integer> erfüllteAnforderungen = new ArrayList<>();
    
    @Column(columnDefinition = "TEXT")
    private String notizen;
}

