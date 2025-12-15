package ch.m324.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "kriterien")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kriterium {
    
    @Id
    private String id; // z.B. "C02"
    
    @Column(nullable = false)
    private String titel;
    
    @ElementCollection
    @CollectionTable(name = "kriterium_anforderungen", joinColumns = @JoinColumn(name = "kriterium_id"))
    @Column(name = "anforderung")
    private List<String> anforderungen;
    
    @ElementCollection
    @CollectionTable(name = "kriterium_guetestufen", joinColumns = @JoinColumn(name = "kriterium_id"))
    @MapKeyColumn(name = "erfuellt_anzahl")
    @Column(name = "guetestufe")
    private Map<Integer, Integer> gütestufen; // Anzahl erfüllt -> Gütestufe
    
    private String teil; // "Teil1" oder "Teil2"
}

