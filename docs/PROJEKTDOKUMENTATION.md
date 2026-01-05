# Projektdokumentation

Kriterien-Tracking Webapplikation für M324/M450

**Version:** 1.0  
**Datum:** 2026-01-05  
**Autoren:** Mustafa Sagaaro

---

## Inhaltsverzeichnis

1. [Projektübersicht](#projektübersicht)
2. [Vorgehen](#vorgehen)
3. [Architektur](#architektur)
4. [Testkonzept](#testkonzept)
5. [CI/CD Pipeline](#cicd-pipeline)
6. [Technologien](#technologien)
7. [Setup und Installation](#setup-und-installation)

---

## Projektübersicht

### Zielsetzung

Die Webapplikation ermöglicht die Nachverfolgung erfüllter Anforderungen pro Kriterium für individuelle praktische Arbeiten. Basierend auf den abgehakten Anforderungen werden automatisch Gütestufen (0-3) und mutmassliche Noten für Teil 1 und Teil 2 berechnet.

### Funktionalität

- Erfassung von Personendaten (Name, Vorname, Thema, Abgabedatum)
- Verwaltung von Kriterien aus dem QV BiVO2021 Katalog
- Abhaken von Anforderungen pro Kriterium
- Automatische Berechnung von Gütestufen (0-3) basierend auf erfüllten Anforderungen
- Automatische Berechnung mutmasslicher Noten für Teil 1 und Teil 2
- Notizen pro Kriterium

---

## Vorgehen

### Projektplanung

Das Projekt wurde agil umgesetzt mit kontinuierlicher Entwicklung in kleinen, abgeschlossenen Schritten. Jeder Arbeitsschritt wurde nach Abschluss in Git committed.

### Entwicklungsphasen

1. **Phase 1: Projektstruktur und Dokumentation**
   - Repository-Analyse
   - Dokumentationsstruktur erstellt
   - Umstrukturierungspläne dokumentiert

2. **Phase 2: Backend-Grundlagen**
   - JPA Entities (Person, Criterion, CriterionProgress)
   - Repository Interfaces
   - H2 Datenbank-Konfiguration

3. **Phase 3: Backend-Services**
   - KriterienLoaderService (Laden aus JSON)
   - GuetestufeService (Berechnungslogik)
   - CriterionProgressService (Fortschrittsverwaltung)

4. **Phase 4: REST API**
   - PersonController (CRUD)
   - CriterionController (Kriterien-Verwaltung)
   - CriterionProgressController (Fortschritt speichern)

5. **Phase 5: Frontend**
   - React-Komponenten (PersonForm, CriterionList, CriterionCard, GradesDisplay)
   - API-Services
   - Routing

6. **Phase 6: Tests**
   - Unit-Tests (GuetestufeService)
   - Integration-Tests (REST API)
   - E2E-Tests (manuell)

7. **Phase 7: CI/CD**
   - GitHub Actions Pipeline
   - Automatische Tests
   - Coverage-Berichte

### Git-Workflow

- Feature-Branches für jede Funktionalität
- Merge nach main nach erfolgreichen Tests
- Strukturierte Commit-Messages (feat, test, docs, ci)
- Regelmässige Commits nach jedem abgeschlossenen Arbeitsschritt

---

## Architektur

### Systemarchitektur

Die Anwendung folgt einer 3-Tier-Architektur:

```
┌─────────────┐
│   Frontend  │  React SPA
│  (Port 3000)│
└──────┬──────┘
       │ HTTP/REST
┌──────▼──────┐
│   Backend   │  Spring Boot
│  (Port 8080)│
└──────┬──────┘
       │ JPA
┌──────▼──────┐
│  Datenbank  │  H2 (Development)
│             │  PostgreSQL (Production)
└─────────────┘
```

### Backend-Architektur

Das Backend folgt dem Layered Architecture Pattern:

#### Controller Layer
- **PersonController**: CRUD-Operationen für Personen
- **CriterionController**: Verwaltung von Kriterien
- **CriterionProgressController**: Verwaltung von Fortschritt

**Verantwortlichkeiten:**
- HTTP-Request-Handling
- Request-Validierung
- Response-Mapping
- HTTP-Status-Codes

#### Service Layer
- **KriterienLoaderService**: Lädt Kriterien aus JSON beim Start
- **GuetestufeService**: Berechnet Gütestufen (0-3)
- **CriterionProgressService**: Verwaltet Fortschritt mit automatischer Gütestufen-Berechnung

**Verantwortlichkeiten:**
- Business-Logik
- Datenvalidierung
- Koordination zwischen Repository und Controller

#### Repository Layer
- **PersonRepository**: Datenbankzugriffe für Personen
- **CriterionRepository**: Datenbankzugriffe für Kriterien
- **CriterionProgressRepository**: Datenbankzugriffe für Fortschritt

**Verantwortlichkeiten:**
- Datenbank-Operationen
- Query-Implementierung

#### Model Layer
- **Person**: Entity für Personendaten
- **Criterion**: Entity für Kriterien
- **CriterionProgress**: Entity für Fortschritt

#### DTO Layer
- **Request DTOs**: CriterionProgressRequest (mit Validierung)
- **Response DTOs**: CriterionResponse, CriterionProgressResponse

### Frontend-Architektur

#### Komponentenstruktur
```
App.js
├── HomePage
│   └── PersonForm
│   └── PersonenListe
└── PersonDetailPage
    ├── GradesDisplay
    └── CriterionList
        └── CriterionCard
```

#### Services
- **api.js**: Axios-Instanz mit Base-URL
- **personService.js**: CRUD für Personen
- **criterionService.js**: Laden von Kriterien
- **criterionProgressService.js**: Speichern/Aktualisieren von Fortschritt

### Datenmodell

#### Person
- `id`: Long (Primary Key, auto-generated)
- `name`: String
- `vorname`: String
- `thema`: String
- `abgabedatum`: LocalDate

#### Criterion
- `id`: String (Primary Key)
- `titel`: String
- `leitfrage`: String
- `teil`: String (Teil1, Teil2, Dokumentation)
- `anforderungen`: List<String>

#### CriterionProgress
- `id`: Long (Primary Key, auto-generated)
- `personId`: Long (Foreign Key zu Person)
- `criterionId`: String (Foreign Key zu Criterion)
- `erfuellteAnforderungen`: List<Integer> (Indizes der erfüllten Anforderungen)
- `notizen`: String

### Gütestufen-Berechnung

Die Gütestufe wird basierend auf dem Prozentsatz erfüllter Anforderungen berechnet:

- **Gütestufe 0**: 0% erfüllt (0 Anforderungen)
- **Gütestufe 1**: < 33% erfüllt (1-2 Anforderungen bei 6 Anforderungen)
- **Gütestufe 2**: 33-66% erfüllt (3-4 Anforderungen bei 6 Anforderungen)
- **Gütestufe 3**: > 66% erfüllt (5-6 Anforderungen bei 6 Anforderungen)

**Noten-Mapping:**
- Gütestufe 0 → Note 1.0
- Gütestufe 1 → Note 2.5
- Gütestufe 2 → Note 4.0
- Gütestufe 3 → Note 6.0

Die mutmassliche Note für Teil 1/Teil 2 wird als Durchschnitt aller Gütestufen des jeweiligen Teils berechnet.

---

## Testkonzept

### Testarten

#### Unit-Tests
**Framework:** JUnit 5, Mockito  
**Ziel:** Isolierte Tests einzelner Komponenten

**Abgedeckte Komponenten:**
- `GuetestufeService`: 19 Testfälle
  - Alle Grenzfälle (0-6 erfüllte Anforderungen)
  - Null-Werte Handling
  - Edge Cases

#### Integration-Tests
**Framework:** Spring Boot Test, MockMvc  
**Ziel:** Testen der REST-API-Endpoints

**Abgedeckte Endpoints:**
- `PersonController`: 9 Testfälle (GET, POST, PUT, DELETE)
- `CriterionController`: 7 Testfälle (GET, Filter, Reload)
- `CriterionProgressController`: 8 Testfälle (POST, GET, Gütestufen-Berechnung)

#### End-to-End Tests
**Status:** Manuell durchgeführt  
**Ziel:** Vollständige User-Flows

**Getestete Szenarien:**
- Person erstellen
- Kriterien abhaken
- Gütestufen-Berechnung
- Noten-Anzeige

### Testabdeckung

**Aktuelle Coverage:** 80%+ (Ziel erreicht)

| Package | Instructions | Branches | Lines |
|---------|--------------|----------|-------|
| `ch.m324.service` | 91% | 78% | 91% |
| `ch.m324.model` | 79% | n/a | 79% |
| `ch.m324.controller` | 80%+ | 70%+ | 80%+ |
| **Gesamt** | **80%+** | **71%+** | **80%+** |

### Test-Ausführung

**Lokal:**
```bash
cd backend
mvn test                    # Alle Tests
mvn test jacoco:report     # Mit Coverage
```

**CI/CD:**
Tests werden automatisch bei jedem Commit/Pull Request ausgeführt.

### Test-Daten

- H2 In-Memory Datenbank für Tests
- Test-Daten werden in `@BeforeEach` erstellt
- `@Transactional` sorgt für automatisches Cleanup

---

## CI/CD Pipeline

### Pipeline-Struktur

Die GitHub Actions Pipeline besteht aus folgenden Jobs:

#### 1. Backend Build & Test
**Zweck:** Kompilierung, Tests, Coverage

**Schritte:**
1. Repository auschecken
2. Java 17 Setup mit Maven Caching
3. Dependencies herunterladen
4. Backend kompilieren
5. Tests ausführen mit JaCoCo Coverage
6. Coverage-Bericht hochladen
7. Test-Ergebnisse veröffentlichen
8. JAR erstellen und als Artifact speichern

**Abbruchbedingungen:**
- Build-Fehler
- Test-Fehler
- Coverage unter 80% (konfiguriert in JaCoCo)

#### 2. Code Quality Checks
**Zweck:** Code-Qualität überprüfen

**Schritte:**
1. Checkstyle ausführen
2. Code Quality Berichte speichern

**Abbruchbedingungen:**
- Checkstyle-Fehler (failsOnError: true)

#### 3. Frontend Build (optional)
**Zweck:** Frontend-Produktions-Build

**Trigger:** Nur bei Frontend-Änderungen

**Schritte:**
1. Node.js 20 Setup
2. Dependencies installieren
3. Production Build
4. Build-Artifact speichern

#### 4. Pipeline Status
**Zweck:** Gesamtstatus überprüfen

**Abbruchbedingungen:**
- Backend-Tests fehlgeschlagen

### Pipeline-Konfiguration

**Trigger:**
- Push auf `main` oder `develop`
- Pull Requests zu `main` oder `develop`

**Concurrency:**
- Läuft parallel bei mehreren Commits
- Neue Pipeline storniert laufende Pipelines

**Secrets:**
- `CODECOV_TOKEN` (optional, für Codecov Integration)
- `REACT_APP_API_URL` (optional, für Frontend Build)

### Fehlerbehandlung

Die Pipeline gibt spezifische Fehlermeldungen aus:
- Build-Fehler: Zeigt Maven-Output
- Test-Fehler: Zeigt fehlgeschlagene Tests
- Checkstyle-Fehler: Zeigt Code-Qualitätsprobleme
- Coverage-Fehler: Zeigt Coverage-Statistiken

---

## Technologien

### Backend
- **Java:** 17
- **Spring Boot:** 3.2.0
- **Spring Data JPA:** Für Datenbankzugriffe
- **H2 Database:** In-Memory für Development/Testing
- **PostgreSQL:** Für Production (vorbereitet)
- **Maven:** 3.9.5
- **Lombok:** Für weniger Boilerplate-Code
- **JaCoCo:** Für Code Coverage

### Frontend
- **React:** 18.x
- **React Router:** Für Navigation
- **Axios:** Für HTTP-Requests
- **Node.js:** 20.x
- **npm:** Für Dependency Management

### Testing
- **JUnit 5:** Unit- und Integration-Tests
- **Mockito:** Für Mocking
- **MockMvc:** Für REST-API-Tests
- **JaCoCo:** Code Coverage

### CI/CD
- **GitHub Actions:** CI/CD Pipeline
- **Checkstyle:** Code-Qualität
- **Codecov:** Coverage-Tracking (optional)

### Entwicklungstools
- **Git:** Versionskontrolle
- **Maven:** Build-Tool
- **npm:** Frontend Package Manager

---

## Setup und Installation

### Voraussetzungen

- Java 17 oder höher
- Maven 3.9+
- Node.js 20.x
- npm oder yarn

### Backend Setup

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Backend läuft auf: `http://localhost:8080`

### Frontend Setup

```bash
cd frontend
npm install
npm start
```

Frontend läuft auf: `http://localhost:3000`

### Datenbank

**Development:**
- H2 In-Memory Datenbank
- Automatisches Schema-Update (`ddl-auto: update`)
- H2 Console: `http://localhost:8080/h2-console`

**Production:**
- PostgreSQL (konfigurierbar über `application.yml`)

### Konfiguration

**Backend:** `backend/src/main/resources/application.yml`

**Wichtige Einstellungen:**
- Datenbank-URL
- H2 Console (nur Development)
- JPA DDL-Auto-Mode

**Frontend:** `frontend/.env` (optional)

**Umgebungsvariablen:**
- `REACT_APP_API_URL`: Backend API URL (Standard: `http://localhost:8080/api`)

---

## API-Dokumentation

### Personen-Endpunkte

#### GET /api/personen
Lädt alle Personen.

**Response:**
```json
[
  {
    "id": 1,
    "name": "Mustafa",
    "vorname": "Sagaaro",
    "thema": "Kriterien-Tracking",
    "abgabedatum": "2026-06-15"
  }
]
```

#### GET /api/personen/{id}
Lädt eine spezifische Person.

**Response:** 200 OK mit Person-Objekt oder 404 Not Found

#### POST /api/personen
Erstellt eine neue Person.

**Request:**
```json
{
  "name": "Mustafa",
  "vorname": "Sagaaro",
  "thema": "Kriterien-Tracking",
  "abgabedatum": "2026-06-15"
}
```

**Response:** 201 Created mit erstellter Person

#### PUT /api/personen/{id}
Aktualisiert eine Person.

**Response:** 200 OK mit aktualisierter Person oder 404 Not Found

#### DELETE /api/personen/{id}
Löscht eine Person.

**Response:** 204 No Content oder 404 Not Found

### Kriterien-Endpunkte

#### GET /api/criteria
Lädt alle Kriterien.

#### GET /api/criteria/{id}
Lädt ein spezifisches Kriterium.

#### GET /api/criteria/teil/{teil}
Lädt alle Kriterien für einen Teil (Teil1, Teil2, Dokumentation).

#### POST /api/criteria/reload
Lädt Kriterien neu aus JSON-Datei.

### Fortschritt-Endpunkte

#### POST /api/criterion-progress
Speichert oder aktualisiert Fortschritt für ein Kriterium.

**Request:**
```json
{
  "personId": 1,
  "criterionId": "C02",
  "erfuellteAnforderungen": [0, 1, 2],
  "notizen": "Test Notizen"
}
```

**Response:** 201 Created mit Fortschritt und berechneter Gütestufe

#### GET /api/criterion-progress/person/{personId}
Lädt alle Fortschritte einer Person.

#### GET /api/criterion-progress/person/{personId}/criterion/{criterionId}
Lädt spezifischen Fortschritt.

---

## Datenbank-Schema

### Person
```sql
CREATE TABLE person (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    vorname VARCHAR(255),
    thema VARCHAR(255),
    abgabedatum DATE
);
```

### Criterion
```sql
CREATE TABLE criteria (
    id VARCHAR(255) PRIMARY KEY,
    titel VARCHAR(255),
    leitfrage TEXT,
    teil VARCHAR(255)
);

CREATE TABLE criterion_anforderungen (
    criterion_id VARCHAR(255),
    anforderung TEXT,
    FOREIGN KEY (criterion_id) REFERENCES criteria(id)
);
```

### CriterionProgress
```sql
CREATE TABLE criterion_progress (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    person_id BIGINT,
    criterion_id VARCHAR(255),
    notizen TEXT,
    FOREIGN KEY (person_id) REFERENCES person(id),
    FOREIGN KEY (criterion_id) REFERENCES criteria(id)
);

CREATE TABLE erfuellte_anforderungen (
    criterion_progress_id BIGINT,
    anforderung_index INTEGER,
    FOREIGN KEY (criterion_progress_id) REFERENCES criterion_progress(id)
);
```

---

## Versionskontrolle

### Branch-Strategie

- **main**: Produktions-Branch
- **develop**: Entwicklungs-Branch
- **feature/***: Feature-Branches

### Commit-Konventionen

- `feat`: Neue Features
- `test`: Tests
- `docs`: Dokumentation
- `ci`: CI/CD Änderungen
- `fix`: Bugfixes
- `chore`: Wartungsaufgaben

### Merge-Prozess

1. Feature-Branch erstellen
2. Änderungen implementieren
3. Tests ausführen
4. Commit mit beschreibender Message
5. Merge nach main (nur nach erfolgreichen Tests)

---

## Bekannte Einschränkungen

1. **Automatisierte E2E-Tests:** Aktuell nur manuell durchgeführt. Automatisierte E2E-Tests mit Cypress oder Playwright sind noch nicht implementiert.
2. **Frontend Unit-Tests:** Nur ein grundlegender Test vorhanden (`App.test.js`). Umfassende Komponenten-Tests für PersonForm, CriterionCard, CriterionList, GradesDisplay fehlen noch.
3. **Staging-Deployment:** Automatisches Deployment in eine Staging-Umgebung ist noch nicht konfiguriert. Die Pipeline erstellt nur Artifacts, führt aber kein Deployment durch.

---

## Nächste Schritte (Optional, nicht Teil der aktuellen Anforderungen)

Die folgenden Punkte sind für zukünftige Erweiterungen geplant, werden aber für die aktuelle Projektphase nicht benötigt:

1. **Automatisierte E2E-Tests:** Implementierung mit Cypress oder Playwright für kritische User-Flows
2. **Frontend Unit-Tests:** Umfassende Tests für alle React-Komponenten mit React Testing Library
3. **Staging-Deployment:** Automatisches Deployment in Staging-Umgebung nach erfolgreichen Tests
4. **Performance-Tests:** Load-Tests für die REST-API
5. **Erweiterte Fehlerbehandlung:** Zentrales Exception-Handling im Backend mit globalem Exception Handler

**Hinweis:** Die aktuelle Implementierung erfüllt alle Anforderungen der Aufgabenstellung. Die oben genannten Punkte sind optionale Erweiterungen für zukünftige Versionen.

---

## Anhang

### Quellen

- QV BiVO2021 Kriterienkatalog
- Spring Boot Dokumentation: https://spring.io/projects/spring-boot
- React Dokumentation: https://react.dev

### Kontakt

Bei Fragen zur Projektdokumentation oder Implementierung wenden Sie sich an die Projektmitglieder.

