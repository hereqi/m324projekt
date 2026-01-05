# M324/M450 Projekt - Kriterien-Tracking Webapplikation

## Projektübersicht

Eine Webapplikation zur Nachverfolgung der Erfüllung von Kriterien für die individuelle praktische Arbeit (IPA) nach QV BiVO2021. Die Applikation berechnet automatisch Gütestufen (0-3) und mutmassliche Noten für Teil 1 und Teil 2 basierend auf erfüllten Anforderungen.

## Technologie-Stack

- **Frontend**: React 18, React Router, Axios
- **Backend**: Spring Boot 3.2.0 (Java 17)
- **Datenbank**: H2 (Entwicklung/Tests), PostgreSQL (Produktion)
- **Build-Tools**: Maven, npm
- **CI/CD**: GitHub Actions
- **Tests**: JUnit 5, Mockito, MockMvc, JaCoCo (88% Coverage)
- **Code-Qualität**: Checkstyle

## Projektstruktur

```
m324projekt/
├── frontend/          # React Frontend
├── backend/           # Spring Boot Backend
├── docs/              # Projekt-Dokumentation
│   ├── projekt/       # Projekt-Dokumentation
│   ├── ki-nutzung/    # KI-Nutzung Dokumentation
│   ├── testkonzept/   # Testkonzept
│   └── setup/         # Setup-Anleitungen
├── docker-compose.yml # Docker Compose Konfiguration
└── .github/
    └── workflows/     # GitHub Actions CI/CD Pipeline
```

**Detaillierte Dokumentation:** Siehe [docs/README.md](./docs/README.md)

## Git Branch-Workflow für 3 Personen

### Empfohlener Workflow

1. **Main Branch**: Stabile, produktionsreife Version
2. **Develop Branch**: Entwicklungsbranch für Integration
3. **Feature Branches**: Für neue Features (`feature/kriterien-erfassen`, `feature/noten-berechnung`, etc.)

### Workflow-Schritte

```bash
# 1. Develop Branch erstellen (einmalig)
git checkout -b develop
git push -u origin develop

# 2. Für neue Features: Feature Branch von develop erstellen
git checkout develop
git pull origin develop
git checkout -b feature/mein-feature

# 3. Arbeiten am Feature
# ... Code schreiben, committen ...

# 4. Feature Branch pushen
git push -u origin feature/mein-feature

# 5. Pull Request erstellen (auf GitHub)
# - Von feature/mein-feature → develop
# - Code Review durch Team
# - Nach Review: Merge in develop

# 6. Nach mehreren Features: develop → main mergen
git checkout main
git merge develop
git push origin main
```

### Branch-Namenskonventionen

- `feature/` - Neue Features (z.B. `feature/kriterien-ui`)
- `bugfix/` - Bugfixes (z.B. `bugfix/noten-berechnung`)
- `hotfix/` - Kritische Fixes direkt auf main
- `refactor/` - Code-Refactoring

### Wichtige Regeln

- ✅ **Niemals direkt auf main oder develop committen**
- ✅ **Jeder Feature-Branch sollte klein und fokussiert sein**
- ✅ **Regelmässig `git pull` machen, um aktuell zu bleiben**
- ✅ **Vor dem Merge: Tests lokal ausführen**
- ✅ **Commit-Messages sollten klar und beschreibend sein**

## Setup & Installation

### Voraussetzungen

- **Java 17+** (für Backend)
- **Node.js 20+** (für Frontend)
- **Maven 3.9+** (für Backend-Build)
- **npm** (für Frontend-Build)

### Lokale Entwicklung

#### 1. Backend starten

```bash
cd backend
mvn spring-boot:run
```

Backend läuft auf: `http://localhost:8080`  
H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:kriterien_db`)

#### 2. Frontend starten

```bash
cd frontend
npm install
npm start
```

Frontend läuft auf: `http://localhost:3000`

### Tests ausführen

#### Backend Tests
```bash
cd backend
mvn test                    # Alle Tests
mvn test jacoco:report      # Tests mit Coverage-Bericht
mvn verify                  # Tests + Coverage-Check (80%+)
```

#### Frontend Tests
```bash
cd frontend
npm test                    # Tests ausführen
npm run test -- --coverage  # Tests mit Coverage
```

## CI/CD Pipeline

Die GitHub Actions Pipeline führt automatisch aus:

1. **Backend Build & Test**
   - Kompilierung des Backends
   - Ausführung aller Unit- und Integrationstests
   - Code Coverage Prüfung (mind. 80% erforderlich)
   - Checkstyle Code-Qualitätsprüfung
   - Erstellung von JAR-Artifacts

2. **Frontend Build** (optional, bei Frontend-Änderungen)
   - Installation von Dependencies
   - Produktions-Build

3. **Code Quality**
   - Checkstyle-Linting
   - Code-Quality-Reports

**Status**: ✅ Pipeline aktiv und funktionsfähig  
**Coverage**: 88% (Instructions), 80% (Branches)  
**Tests**: 44 Tests (alle erfolgreich)

Siehe `.github/workflows/ci.yml` für Details.

## API-Endpunkte (Backend)

### Personen-API (`/api/personen`)
- `GET /api/personen` - Alle Personen abrufen
- `GET /api/personen/{id}` - Person nach ID abrufen
- `POST /api/personen` - Neue Person erstellen
- `PUT /api/personen/{id}` - Person aktualisieren
- `DELETE /api/personen/{id}` - Person löschen

### Kriterien-API (`/api/criteria`)
- `GET /api/criteria` - Alle Kriterien abrufen
- `GET /api/criteria/{id}` - Kriterium nach ID abrufen
- `GET /api/criteria/teil/{teil}` - Kriterien nach Teil (Teil1, Teil2, Dokumentation)
- `POST /api/criteria/reload` - Kriterien aus JSON neu laden

### Kriterien-Fortschritt-API (`/api/criterion-progress`)
- `GET /api/criterion-progress/person/{personId}` - Alle Fortschritte einer Person
- `GET /api/criterion-progress/person/{personId}/criterion/{criterionId}` - Spezifischer Fortschritt
- `POST /api/criterion-progress` - Fortschritt speichern/aktualisieren (Gütestufe wird automatisch berechnet)
- `PUT /api/criterion-progress` - Fortschritt aktualisieren

**Vollständige API-Dokumentation**: Siehe [PROJEKTDOKUMENTATION.md](./docs/PROJEKTDOKUMENTATION.md#api-dokumentation)

## Datenmodell

### Person
- `id` (Long, Primary Key)
- `name` (String)
- `vorname` (String)
- `thema` (String)
- `abgabedatum` (LocalDate)

### Criterion
- `id` (String, Primary Key, z.B. "C02", "B05", "DOC01")
- `titel` (String)
- `leitfrage` (String)
- `teil` (String: "Teil1", "Teil2", "Dokumentation")
- `anforderungen` (List<String>)

### CriterionProgress
- `id` (Long, Primary Key)
- `personId` (Long, Foreign Key zu Person)
- `criterionId` (String, Foreign Key zu Criterion)
- `erfuellteAnforderungen` (List<Integer>, Indizes der erfüllten Anforderungen)
- `notizen` (String)
- `guetestufe` (int, 0-3, automatisch berechnet)

**Gütestufen-Berechnung:**
- **3** (Vollständig erfüllt): ≥66% der Anforderungen erfüllt
- **2** (Grösstenteils erfüllt): ≥33% und <66% erfüllt
- **1** (Teilweise erfüllt): >0% und <33% erfüllt
- **0** (Nicht erfüllt): 0% erfüllt

## Dokumentation

Alle Projekt-Dokumentation befindet sich im `docs/` Verzeichnis:

- **[📋 Projektdokumentation](./docs/PROJEKTDOKUMENTATION.md)** - Vollständige Projektdokumentation (Vorgehen, Architektur, Testkonzept, Pipeline)
- **[📚 Dokumentation Übersicht](./docs/README.md)** - Übersicht aller Dokumente
- **[🧪 Testkonzept](./docs/testkonzept/TESTKONZEPT.md)** - Testkonzept und Testfälle
- **[📊 Testergebnisse](./docs/testkonzept/TESTERGEBNISSE.md)** - Testprotokolle und Coverage-Berichte
- **[🤖 KI-Nutzung](./docs/ki-nutzung/KI_NUTZUNG.md)** - Dokumentation aller KI-Nutzungen
- **[🐳 Docker Setup](./docs/setup/DOCKER_SETUP.md)** - Docker Setup-Anleitung

## Lizenz

Dieses Projekt wurde im Rahmen der Module M324 (DevOps) und M450 (Testing) erstellt.

## Kontakt

Bei Fragen zur Projektdokumentation oder Implementierung siehe [PROJEKTDOKUMENTATION.md](./docs/PROJEKTDOKUMENTATION.md).

## Projektstatus

### ✅ Implementiert

- **Backend**
  - ✅ Spring Boot REST-API mit JPA
  - ✅ H2 Datenbank (Entwicklung) / PostgreSQL (Produktion)
  - ✅ Entities: Person, Criterion, CriterionProgress
  - ✅ Services: KriterienLoader, GuetestufeService, CriterionProgressService
  - ✅ REST-Controller mit DTOs und Validierung
  - ✅ Automatische Gütestufen-Berechnung
  - ✅ 44 Tests (Unit + Integration), 88% Coverage

- **Frontend**
  - ✅ React-App mit Routing
  - ✅ Personendaten-Formular
  - ✅ Kriterien-Liste mit Checkboxen
  - ✅ Notizen-Funktionalität
  - ✅ Automatische Noten-Berechnung und Anzeige
  - ✅ Optimistic UI Updates
  - ✅ Fehlerbehandlung

- **CI/CD**
  - ✅ GitHub Actions Pipeline
  - ✅ Automatischer Build und Tests
  - ✅ Code Coverage Prüfung (80%+)
  - ✅ Checkstyle Linting
  - ✅ Artifact-Erstellung

- **Dokumentation**
  - ✅ Vollständige Projektdokumentation
  - ✅ Testkonzept und Testergebnisse
  - ✅ KI-Nutzung dokumentiert
  - ✅ API-Dokumentation

### ⏳ Optionale Erweiterungen

1. Automatisierte E2E-Tests (Cypress/Playwright)
2. Frontend Unit-Tests erweitern
3. Staging-Deployment konfigurieren
4. Performance-Tests

### Branch-Workflow starten

```bash
# 1. Repository initialisieren (falls noch nicht geschehen)
git init
git add .
git commit -m "Initial project setup"

# 2. Develop Branch erstellen
git checkout -b develop
git push -u origin develop

# 3. Feature Branches erstellen (jeder im Team)
git checkout -b feature/mein-feature
```

