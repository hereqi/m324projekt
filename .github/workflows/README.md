# GitHub Actions CI/CD Pipeline

## Übersicht

Diese Pipeline führt automatisch Build, Tests und Code Quality Checks für das Projekt durch.

## Workflow-Dateien

- `ci.yml` - Haupt-CI-Pipeline für Backend und Frontend

## Pipeline-Jobs

### 1. Backend Build & Test
- Kompiliert das Backend
- Führt alle Tests aus
- Generiert Code Coverage Berichte (JaCoCo)
- Erstellt JAR-Artifact

### 2. Frontend Build (optional)
- Läuft nur wenn Frontend-Dateien geändert wurden
- Installiert Dependencies
- Baut Frontend-Produktions-Bundle

### 3. Code Quality
- Führt Code Quality Checks aus (Checkstyle, SpotBugs)
- Generiert Berichte

### 4. Pipeline Status
- Überprüft den Gesamtstatus der Pipeline
- Bricht bei Fehlern ab

## GitHub Secrets Konfiguration

### Erforderliche Secrets

Die folgenden Secrets können in den GitHub Repository Settings konfiguriert werden:
`Settings > Secrets and variables > Actions > New repository secret`

#### Optional (für Codecov Integration)
- `CODECOV_TOKEN` - Token für Codecov Coverage-Upload
  - Erstellen: https://codecov.io/
  - Hinzufügen: Repository Settings > Secrets

#### Optional (für Frontend Build)
- `REACT_APP_API_URL` - API URL für Frontend Build
  - Standard: `http://localhost:8080/api`
  - Für Production: `https://api.example.com/api`

### Secrets hinzufügen

1. Gehe zu deinem GitHub Repository
2. Klicke auf `Settings`
3. Wähle `Secrets and variables` > `Actions`
4. Klicke auf `New repository secret`
5. Füge Name und Wert hinzu
6. Klicke auf `Add secret`

## Pipeline Trigger

Die Pipeline läuft automatisch bei:
- Push auf `main` oder `develop` Branch
- Pull Requests zu `main` oder `develop` Branch

## Manuelles Ausführen

Die Pipeline kann auch manuell ausgeführt werden:
1. Gehe zu `Actions` Tab im Repository
2. Wähle `CI Pipeline`
3. Klicke auf `Run workflow`
4. Wähle Branch und klicke auf `Run workflow`

## Artifacts

Die Pipeline erstellt folgende Artifacts:
- `backend-jar` - Kompiliertes Backend JAR (7 Tage gespeichert)
- `backend-coverage-report` - Coverage Berichte (30 Tage gespeichert)
- `frontend-build` - Frontend Build (7 Tage gespeichert)
- `code-quality-reports` - Code Quality Berichte (30 Tage gespeichert)

## Fehlerbehandlung

- Die Pipeline bricht bei Build-Fehlern ab
- Die Pipeline bricht bei Test-Fehlern ab
- Coverage-Upload-Fehler brechen die Pipeline nicht ab (optional)
- Code Quality Warnungen brechen die Pipeline nicht ab (optional)

## Performance

- Maven Dependencies werden gecacht
- Node.js Dependencies werden gecacht
- Parallele Job-Ausführung für bessere Performance

## Erweiterte Konfiguration

### Deployment (später hinzufügen)

Für Deployment können weitere Jobs hinzugefügt werden:
- Docker Build
- Container Registry Push
- Deployment zu Cloud Providern (AWS, Azure, GCP)

### Beispiel für Deployment-Secrets:
- `DOCKER_USERNAME` - Docker Hub Username
- `DOCKER_PASSWORD` - Docker Hub Password
- `AWS_ACCESS_KEY_ID` - AWS Access Key
- `AWS_SECRET_ACCESS_KEY` - AWS Secret Key

