# M324/M450 Projekt - Kriterien-Tracking Webapplikation

## Projektübersicht

Eine Webapplikation zur Nachverfolgung der Erfüllung von Kriterien für die individuelle praktische Arbeit. Die Applikation berechnet automatisch die mutmassliche Note basierend auf erfüllten Anforderungen.

## Technologie-Stack

- **Frontend**: React
- **Backend**: Spring Boot (Java)
- **Datenbank**: PostgreSQL
- **Containerisierung**: Docker & Docker Compose
- **CI/CD**: GitHub Actions

## Projektstruktur

```
m324-projekt/
├── frontend/          # React Frontend
├── backend/           # Spring Boot Backend
├── docker-compose.yml # Docker Compose Konfiguration
└── .github/
    └── workflows/     # GitHub Actions CI/CD Pipeline
```

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

- Docker & Docker Compose
- Node.js 18+ (für lokale Frontend-Entwicklung)
- Java 17+ (für lokale Backend-Entwicklung)
- PostgreSQL (optional, wenn nicht Docker verwendet)

### Mit Docker (Empfohlen)

```bash
# Alle Services starten
docker-compose up -d

# Logs anzeigen
docker-compose logs -f

# Services stoppen
docker-compose down
```

### Lokale Entwicklung

#### Frontend
```bash
cd frontend
npm install
npm start
```

#### Backend
```bash
cd backend
./mvnw spring-boot:run
```

## CI/CD Pipeline

Die GitHub Actions Pipeline führt automatisch aus:

1. **Build**: Frontend und Backend werden gebaut
2. **Linting**: Code-Qualitätsprüfung
3. **Tests**: Unit- und Integrationstests (mind. 80% müssen bestehen)
4. **Deployment**: Automatisches Deployment in Staging bei Erfolg

Siehe `.github/workflows/ci.yml` für Details.

## API-Endpunkte (Backend)

- `GET /api/personen` - Alle Personen abrufen
- `POST /api/personen` - Neue Person erstellen
- `GET /api/kriterien` - Alle Kriterien abrufen
- `PUT /api/kriterien/{id}/anforderungen` - Anforderungen aktualisieren
- `GET /api/noten/{personId}` - Mutmassliche Note berechnen

## Datenmodell

### Person
- id, name, vorname, thema, abgabedatum

### Kriterium
- id, titel, anforderungen[], gütestufen

### KriteriumErfüllung
- personId, kriteriumId, erfüllteAnforderungen[], notizen

## KI-Nutzung

Alle KI-Nutzungen werden im Repository dokumentiert:
- Was wurde mit KI erstellt?
- Wo im Code?
- Kurze Notiz zur Verwendung

## Team-Mitglieder

- [Name 1]
- [Name 2]
- [Name 3]

## Entwicklung

### Projektstatus

✅ **Projektstruktur erstellt**
- Frontend (React) Setup
- Backend (Spring Boot) Setup
- Docker & Docker Compose Konfiguration
- GitHub Actions CI/CD Pipeline Grundgerüst
- Basis-Tests vorhanden

### Nächste Schritte

1. ⏳ Frontend-Komponenten implementieren
2. ⏳ Backend-API implementieren (Controller, Service, Repository)
3. ⏳ Datenbank-Modelle erstellen
4. ⏳ Kriterien JSON-Datei erstellen (3 Kriterien)
5. ⏳ Tests erweitern (Unit, Integration, E2E)
6. ⏳ CI/CD Pipeline anpassen und testen

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

