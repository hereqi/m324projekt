package ch.m324.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "personen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name ist erforderlich")
    @Column(nullable = false)
    private String name;
    
    @NotBlank(message = "Vorname ist erforderlich")
    @Column(nullable = false)
    private String vorname;
    
    @NotBlank(message = "Thema ist erforderlich")
    @Column(nullable = false)
    private String thema;
    
    @NotNull(message = "Abgabedatum ist erforderlich")
    @Column(nullable = false)
    private LocalDate abgabedatum;
}

