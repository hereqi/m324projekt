# KI-Nutzung Dokumentation

## Übersicht

Dieses Dokument dokumentiert alle Stellen im Projekt, an denen KI-Tools verwendet wurden.

**Wichtig:** Es werden nur die **Ergebnisse** dokumentiert, nicht die Prompts oder der Prozess.

**Letzte Aktualisierung:** 2026-01-12

---

## Dokumentationsstruktur

Für jede KI-Nutzung wird dokumentiert:
- **Was:** Was wurde mit KI erstellt?
- **Wo:** Dateipfad und relevante Code-Stellen
- **Notiz:** Kurze Beschreibung des Zwecks

---

## KI-Nutzungen

### Projekt-Setup

#### Backend-Struktur
- **Was:** Spring Boot Projekt-Setup, pom.xml Konfiguration
- **Wo:** `backend/pom.xml`
- **Notiz:** Maven-Dependencies und Build-Konfiguration wurden mit KI-Unterstützung erstellt

#### Frontend-Struktur
- **Was:** React Projekt-Setup, package.json Konfiguration
- **Wo:** `frontend/package.json`
- **Notiz:** Dependencies und Scripts wurden mit KI-Unterstützung konfiguriert

#### Docker-Konfiguration
- **Was:** Docker Compose und Dockerfile Konfigurationen
- **Wo:** `docker-compose.yml`, `docker-compose.db.yml`, `backend/Dockerfile`, `frontend/Dockerfile`
- **Notiz:** Container-Konfigurationen wurden mit KI-Unterstützung erstellt

---

### Backend-Entwicklung

#### Entity-Klassen
- **Was:** JPA Entity Definitionen
- **Wo:** `backend/src/main/java/ch/m324/model/`
  - `Person.java` (Zeile 1-50)
  - `Criterion.java` (Zeile 1-60)
  - `CriterionProgress.java` (Zeile 1-55)
- **Notiz:** Datenbankmodelle mit JPA-Annotationen

#### Service Layer
- **Was:** Business-Logik Services
- **Wo:** `backend/src/main/java/ch/m324/service/`
  - `GuetestufeService.java` - Gütestufen-Berechnungslogik
  - `CriterionProgressService.java` - Fortschrittsverwaltung
  - `KriterienLoaderService.java` - JSON-Laden
- **Notiz:** Kern-Business-Logik mit Gütestufen-Berechnung

#### REST-Controller
- **Was:** REST API Endpoints
- **Wo:** `backend/src/main/java/ch/m324/controller/`
  - `PersonController.java`
  - `CriterionController.java`
  - `CriterionProgressController.java`
- **Notiz:** CRUD-Operationen und API-Endpunkte

#### DTOs
- **Was:** Data Transfer Objects
- **Wo:** `backend/src/main/java/ch/m324/dto/`
  - `request/CriterionProgressRequest.java`
  - `response/CriterionProgressResponse.java`
  - `response/CriterionResponse.java`
- **Notiz:** Validierung und Response-Strukturen

---

### Frontend-Entwicklung

#### React-Komponenten
- **Was:** UI-Komponenten
- **Wo:** `frontend/src/components/`
  - `PersonForm.js` - Formular für Personendaten
  - `CriterionCard.js` - Kriterium-Anzeige mit Checkboxen
  - `CriterionList.js` - Liste aller Kriterien
  - `GradesDisplay.js` - Noten-Anzeige
- **Notiz:** React-Komponenten mit State-Management

#### API-Services
- **Was:** HTTP-Client Services
- **Wo:** `frontend/src/services/`
  - `api.js` - Axios-Konfiguration
  - `personService.js`
  - `criterionService.js`
  - `criterionProgressService.js`
- **Notiz:** API-Kommunikation mit dem Backend

#### CSS-Styling
- **Was:** Komponenten-Styles
- **Wo:** `frontend/src/components/*.css`
- **Notiz:** Responsive Design und Layout

---

### Testing

#### Backend Unit-Tests
- **Was:** JUnit 5 Tests mit Mockito
- **Wo:** `backend/src/test/java/ch/m324/service/`
  - `GuetestufeServiceTest.java` - 19 Unit-Tests für Gütestufen-Berechnung
  - `CriterionProgressServiceTest.java` - 12 Unit-Tests für Service-Layer
  - `KriterienLoaderServiceTest.java` - 4 Unit-Tests für JSON-Laden
- **Notiz:** Umfassende Test-Coverage für Business-Logik

#### Backend Integration-Tests
- **Was:** Spring Boot Integration-Tests
- **Wo:** `backend/src/test/java/ch/m324/controller/`
  - `PersonControllerIntegrationTest.java` - 10 API-Tests
  - `CriterionControllerIntegrationTest.java` - 7 API-Tests
  - `CriterionProgressControllerIntegrationTest.java` - 8 API-Tests
- **Notiz:** REST-API Testing mit MockMvc

#### Frontend Tests
- **Was:** React Testing Library Tests
- **Wo:** `frontend/src/components/`
  - `PersonForm.test.js` - 15+ Tests
  - `CriterionCard.test.js` - 10+ Tests
  - `GradesDisplay.test.js` - 8 Tests
- **Notiz:** Komponenten-Tests mit User-Event Simulation

#### E2E-Tests
- **Was:** Cypress End-to-End Tests
- **Wo:** `frontend/cypress/e2e/`
  - `person-flow.cy.js` - User-Flow Tests
- **Notiz:** Automatisierte Browser-Tests für kritische Flows

---

### CI/CD

#### GitHub Actions Pipeline
- **Was:** CI/CD Workflow-Konfiguration
- **Wo:** `.github/workflows/ci.yml`
- **Notiz:** Automatisierte Build-, Test- und Deployment-Pipeline mit kommentierter YAML

#### Checkstyle-Konfiguration
- **Was:** Code-Qualitäts-Regeln
- **Wo:** `backend/checkstyle.xml`
- **Notiz:** Java Code-Style Prüfung

---

### Dokumentation

#### Projektdokumentation
- **Was:** Umfassende Dokumentation
- **Wo:** `docs/PROJEKTDOKUMENTATION.md`
- **Notiz:** Vorgehen, Architektur, Testkonzept, Pipeline

#### Testkonzept
- **Was:** Test-Strategie und Testfälle
- **Wo:** 
  - `docs/testkonzept/TESTKONZEPT.md`
  - `docs/testkonzept/TESTERGEBNISSE.md`
  - `docs/testkonzept/TRACEABILITY_MATRIX.md`
- **Notiz:** Testarten, Testfälle, Ergebnisse, Anforderung-Test-Mapping

#### Pipeline-Dokumentation
- **Was:** CI/CD Ablauf-Erklärung
- **Wo:** `docs/PROJEKT_ABLAUF.md`
- **Notiz:** Detaillierte Pipeline-Schritte mit Erklärungen

#### Branch Protection
- **Was:** Git-Workflow Anleitung
- **Wo:** `docs/projekt/BRANCH_PROTECTION.md`
- **Notiz:** Anleitung für GitHub Branch Protection Rules

---

## Zusammenfassung nach Kategorie

| Kategorie | Anzahl KI-Nutzungen | Hauptdateien |
|-----------|---------------------|--------------|
| Backend-Entwicklung | 5 | Model, Service, Controller, DTO |
| Frontend-Entwicklung | 3 | Components, Services, Styles |
| Testing | 4 | Unit-, Integration-, E2E-Tests |
| CI/CD | 2 | Pipeline, Checkstyle, Docker |
| Dokumentation | 2 | Projekt-, Test-, Pipeline-Docs |
| **Gesamt** | **16** | |

---

## KI-Tools verwendet

- **Claude (Anthropic)** - Code-Generierung, Dokumentation, Tests

---

## Hinweise

1. Alle generierten Code-Stellen wurden manuell überprüft und angepasst
2. KI wurde hauptsächlich für Boilerplate-Code und Dokumentation verwendet
3. Kritische Business-Logik wurde manuell verifiziert
4. Tests wurden geschrieben um KI-generierten Code zu validieren
