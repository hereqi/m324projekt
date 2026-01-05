# Backend Umstrukturierungsvorschlag

## Aktuelle Struktur

```
backend/src/main/java/ch/m324/
├── controller/
│   └── KriteriumController.java
└── KriterienApplication.java
```

## Probleme

1. ❌ Fehlende Package-Struktur (model, service, repository, dto, exception, config)
2. ❌ Controller importiert direkt Model-Klassen (sollte DTOs verwenden)
3. ❌ Keine Service-Layer-Trennung
4. ❌ Keine Exception-Handling-Struktur
5. ❌ Keine Konfigurationsklassen

---

## Empfohlene Struktur

```
backend/src/main/java/ch/m324/
├── KriterienApplication.java          # Spring Boot Main Class
│
├── controller/                          # REST-Controller
│   ├── PersonController.java
│   ├── KriteriumController.java
│   ├── KriteriumErfuellungController.java
│   └── NotenController.java
│
├── service/                             # Business-Logik
│   ├── PersonService.java
│   ├── KriteriumService.java
│   ├── KriteriumErfuellungService.java
│   ├── NotenBerechnungService.java
│   └── KriterienLoaderService.java
│
├── repository/                          # Datenbank-Zugriff
│   ├── PersonRepository.java
│   ├── KriteriumRepository.java
│   └── KriteriumErfuellungRepository.java
│
├── model/                               # JPA-Entitäten
│   ├── Person.java
│   ├── Kriterium.java
│   └── KriteriumErfuellung.java
│
├── dto/                                 # Data Transfer Objects
│   ├── request/
│   │   ├── PersonRequest.java
│   │   ├── KriteriumErfuellungRequest.java
│   │   └── AnforderungUpdateRequest.java
│   └── response/
│       ├── PersonResponse.java
│       ├── KriteriumResponse.java
│       ├── KriteriumErfuellungResponse.java
│       └── NotenResponse.java
│
├── exception/                            # Exception-Handling
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   ├── ValidationException.java
│   └── ErrorResponse.java
│
├── config/                              # Konfiguration
│   ├── CorsConfig.java
│   └── JacksonConfig.java
│
└── util/                                # Utilities
    └── DateUtil.java
```

---

## Detaillierte Beschreibung

### 1. Controller Layer

**Zweck:** REST-API Endpoints, Request/Response-Mapping

**Best Practices:**
- Controller sollten **dünn** sein (nur HTTP-Logik)
- Business-Logik in Services
- DTOs für Request/Response verwenden
- Exception-Handling über `@ControllerAdvice`

**Beispiel:**
```java
@RestController
@RequestMapping("/api/personen")
public class PersonController {
    private final PersonService personService;
    
    @GetMapping
    public ResponseEntity<List<PersonResponse>> getAllPersonen() {
        return ResponseEntity.ok(personService.findAll());
    }
    
    @PostMapping
    public ResponseEntity<PersonResponse> createPerson(@Valid @RequestBody PersonRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(personService.create(request));
    }
}
```

---

### 2. Service Layer

**Zweck:** Business-Logik, Orchestrierung

**Best Practices:**
- Services enthalten die Business-Logik
- Services verwenden Repositories für Datenbank-Zugriff
- Services können andere Services verwenden
- Transaktions-Management mit `@Transactional`

**Beispiel:**
```java
@Service
@Transactional
public class PersonService {
    private final PersonRepository personRepository;
    
    public PersonResponse create(PersonRequest request) {
        Person person = new Person();
        person.setName(request.getName());
        // ... Mapping
        Person saved = personRepository.save(person);
        return mapToResponse(saved);
    }
}
```

---

### 3. Repository Layer

**Zweck:** Datenbank-Zugriff (JPA)

**Best Practices:**
- Erweitert `JpaRepository<Entity, ID>`
- Custom Queries mit `@Query` Annotation
- Method-Namen für automatische Queries

**Beispiel:**
```java
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByNameAndVorname(String name, String vorname);
}
```

---

### 4. Model Layer (Entities)

**Zweck:** JPA-Entitäten, Datenbank-Mapping

**Best Practices:**
- `@Entity` Annotation
- `@Id` für Primary Key
- Relationships mit `@OneToMany`, `@ManyToOne`, etc.
- Keine Business-Logik in Entities

**Beispiel:**
```java
@Entity
@Table(name = "personen")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    // ... weitere Felder
}
```

---

### 5. DTO Layer

**Zweck:** API-Kommunikation, Trennung von Entity und API

**Best Practices:**
- Separate DTOs für Request und Response
- Validation mit Bean Validation (`@Valid`, `@NotNull`, etc.)
- Mapping zwischen Entity und DTO (MapStruct oder manuell)

**Beispiel:**
```java
public class PersonRequest {
    @NotBlank(message = "Name ist erforderlich")
    private String name;
    
    @NotBlank(message = "Vorname ist erforderlich")
    private String vorname;
    
    // ... weitere Felder
}
```

---

### 6. Exception Layer

**Zweck:** Zentrale Fehlerbehandlung

**Best Practices:**
- Custom Exceptions für verschiedene Fehlertypen
- `@ControllerAdvice` für globale Exception-Behandlung
- Strukturierte Error-Responses

**Beispiel:**
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(ex.getMessage()));
    }
}
```

---

### 7. Config Layer

**Zweck:** Spring-Konfigurationen

**Best Practices:**
- CORS-Konfiguration
- Jackson-Serialisierung
- Security-Konfiguration (falls nötig)

**Beispiel:**
```java
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}
```

---

## Migrationsschritte

### Schritt 1: Package-Struktur erstellen
```bash
mkdir -p backend/src/main/java/ch/m324/{model,repository,service,dto/request,dto/response,exception,config,util}
```

### Schritt 2: Model-Klassen erstellen
- `Person.java`
- `Kriterium.java`
- `KriteriumErfuellung.java`

### Schritt 3: Repository-Interfaces erstellen
- `PersonRepository.java`
- `KriteriumRepository.java`
- `KriteriumErfuellungRepository.java`

### Schritt 4: Service-Klassen erstellen
- `PersonService.java`
- `KriteriumService.java`
- `NotenBerechnungService.java`

### Schritt 5: DTOs erstellen
- Request-DTOs für alle Create/Update-Operationen
- Response-DTOs für alle GET-Operationen

### Schritt 6: Controller refactoren
- Controller verwenden Services statt Repositories direkt
- Controller verwenden DTOs statt Entities

### Schritt 7: Exception-Handling hinzufügen
- Custom Exceptions erstellen
- GlobalExceptionHandler implementieren

### Schritt 8: Tests aktualisieren
- Alle Tests an neue Struktur anpassen
- Mocking für Services statt Repositories

---

## Vorteile der neuen Struktur

✅ **Separation of Concerns:** Klare Trennung der Verantwortlichkeiten
✅ **Wartbarkeit:** Einfacher zu verstehen und zu erweitern
✅ **Testbarkeit:** Services können isoliert getestet werden
✅ **Sicherheit:** Entities werden nicht direkt exponiert
✅ **Flexibilität:** DTOs ermöglichen API-Änderungen ohne Entity-Änderungen
✅ **Best Practices:** Folgt Spring Boot Best Practices

---

## Wichtige Hinweise

⚠️ **Funktionalität erhalten:**
- Alle bestehenden Imports aktualisieren
- Docker-Konfigurationen anpassen (falls nötig)
- CI/CD-Pipelines aktualisieren
- Tests müssen weiterhin funktionieren

⚠️ **Schrittweise Migration:**
- Nicht alles auf einmal ändern
- Schritt für Schritt migrieren
- Nach jedem Schritt testen

