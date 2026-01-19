# 🚀 Vollständige Setup- und Ausführungsanleitung

## ✅ Pipeline-Status: ERFOLGREICH

Die CI/CD Pipeline läuft erfolgreich! Alle kritischen Jobs (Backend Build, Tests, Code Quality) bestehen.

**Letzte Aktualisierung:** 2026-01-19  
**Pipeline Run:** #22 ✅ completed successfully

---

## 📋 Voraussetzungen

### Benötigte Software

| Software | Version | Zweck |
|----------|---------|-------|
| Java JDK | 17+ | Backend |
| Maven | 3.9+ | Build-Tool |
| Node.js | 20+ | Frontend |
| npm | 10+ | Package Manager |
| Git | 2.x | Versionskontrolle |

### Installationsprüfung

```bash
# Java prüfen
java -version
# Sollte: openjdk version "17.x.x" oder höher zeigen

# Maven prüfen
mvn -version
# Sollte: Apache Maven 3.9.x zeigen

# Node.js prüfen
node --version
# Sollte: v20.x.x oder höher zeigen

# npm prüfen
npm --version
# Sollte: 10.x.x oder höher zeigen

# Git prüfen
git --version
# Sollte: git version 2.x.x zeigen
```

---

## 🔧 Lokale Entwicklung

### 1. Repository klonen (falls noch nicht geschehen)

```bash
git clone https://github.com/DEIN-USERNAME/m324projekt.git
cd m324projekt
```

### 2. Backend starten

```bash
# In das Backend-Verzeichnis wechseln
cd backend

# Dependencies herunterladen und Build
mvn clean install -DskipTests

# Backend starten
mvn spring-boot:run
```

**Backend läuft auf:** `http://localhost:8080`

**H2 Console:** `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:kriterien_db`
- Username: `sa`
- Password: (leer)

### 3. Frontend starten (neues Terminal)

```bash
# In das Frontend-Verzeichnis wechseln
cd frontend

# Dependencies installieren
npm install

# Frontend starten
npm start
```

**Frontend läuft auf:** `http://localhost:3000`

---

## 🧪 Tests ausführen

### Backend Tests

```bash
cd backend

# Alle Tests ausführen
mvn test

# Tests mit Coverage-Report
mvn test jacoco:report

# Coverage prüfen (80% Minimum)
mvn verify

# Nur bestimmte Test-Klasse ausführen
mvn test -Dtest=GuetestufeServiceTest
```

**Coverage-Report:** `backend/target/site/jacoco/index.html`

### Frontend Tests

```bash
cd frontend

# Tests ausführen
npm test

# Tests einmalig ausführen (ohne Watch-Mode)
npm test -- --watchAll=false

# Tests mit Coverage
npm test -- --coverage --watchAll=false
```

### E2E-Tests (Cypress)

```bash
cd frontend

# Cypress öffnen (interaktiv)
npx cypress open

# Cypress headless ausführen
npx cypress run
```

---

## 📊 Code-Qualität prüfen

### Checkstyle (Backend)

```bash
cd backend

# Checkstyle ausführen
mvn checkstyle:check

# Bei Fehlern: Report anschauen
cat target/checkstyle-result.xml
```

---

## 🔄 Änderungen committen und pushen

### Schritt-für-Schritt

```bash
# 1. Zum Projekt-Root wechseln
cd /Users/mustafa.sagaaro/Documents/Schule/m324/m324projekt

# 2. Status prüfen
git status

# 3. Alle Änderungen stagen
git add -A

# 4. Commit erstellen
git commit -m "fix: Pipeline repariert, Tests erweitert, Dokumentation aktualisiert"

# 5. Auf GitHub pushen
git push origin main
```

### Wichtige Änderungen die committed werden müssen

Die folgenden Dateien müssen committed werden:

**Gelöschte Legacy-Dateien:**
- `backend/src/main/java/ch/m324/controller/KriteriumErfüllungController.java`
- `backend/src/main/java/ch/m324/controller/NotenController.java`
- `backend/src/main/java/ch/m324/service/KriteriumErfüllungService.java`
- `backend/src/main/java/ch/m324/service/NotenBerechnungService.java`

**Neue Test-Dateien:**
- `backend/src/test/java/ch/m324/service/CriterionProgressServiceTest.java`
- `backend/src/test/java/ch/m324/service/KriterienLoaderServiceTest.java`

**Neue Frontend-Tests:**
- `frontend/src/components/PersonForm.test.js`
- `frontend/src/components/CriterionCard.test.js`
- `frontend/src/components/GradesDisplay.test.js`

**Cypress E2E-Tests:**
- `frontend/cypress/` (gesamtes Verzeichnis)
- `frontend/cypress.config.js`

**Neue Dokumentation:**
- `docs/testkonzept/TRACEABILITY_MATRIX.md`
- `docs/projekt/BRANCH_PROTECTION.md`
- `docs/BEWERTUNG_FINAL.md`
- `docs/DAILY_STANDUP.md`
- `docs/SETUP_ANLEITUNG.md`

**Aktualisierte Dateien:**
- `.github/workflows/ci.yml` (Pipeline verbessert)
- `docs/ki-nutzung/KI_NUTZUNG.md`
- `docs/testkonzept/TESTERGEBNISSE.md`

---

## 🚀 CI/CD Pipeline

### Pipeline-Ablauf

```
┌─────────────────────┐
│  Push/PR auf main   │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐    ┌─────────────────────┐    ┌─────────────────────┐
│ Backend Build &     │    │ Frontend Build &    │    │ Code Quality        │
│ Test (parallel)     │    │ Test (parallel)     │    │ (parallel)          │
│                     │    │                     │    │                     │
│ - Compile           │    │ - npm install       │    │ - Checkstyle        │
│ - Unit Tests        │    │ - npm test          │    │                     │
│ - Integration Tests │    │ - npm build         │    │                     │
│ - JaCoCo Coverage   │    │                     │    │                     │
│ - Package JAR       │    │                     │    │                     │
└──────────┬──────────┘    └──────────┬──────────┘    └──────────┬──────────┘
           │                          │                          │
           └──────────────────────────┼──────────────────────────┘
                                      │
                                      ▼
                         ┌─────────────────────┐
                         │   Pipeline Status   │
                         │   (Summary)         │
                         └─────────────────────┘
```

### Pipeline manuell triggern

1. Gehe zu GitHub → Repository → Actions
2. Wähle "CI Pipeline"
3. Klicke "Run workflow"

### Pipeline-Status prüfen

Nach dem Push:
1. Gehe zu GitHub → Repository → Actions
2. Klicke auf den laufenden Workflow
3. Prüfe alle Jobs:
   - ✅ Backend Build & Test
   - ✅ Frontend Build & Test
   - ✅ Code Quality
   - ✅ Pipeline Status

---

## 📁 Projektstruktur

```
m324projekt/
├── .github/
│   └── workflows/
│       └── ci.yml              # CI/CD Pipeline
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/ch/m324/
│   │   │   │   ├── controller/ # REST Controller
│   │   │   │   ├── dto/        # Data Transfer Objects
│   │   │   │   ├── model/      # JPA Entities
│   │   │   │   ├── repository/ # Spring Data Repositories
│   │   │   │   └── service/    # Business Logic
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       └── criteria.json
│   │   └── test/               # Backend Tests
│   ├── checkstyle.xml          # Checkstyle Config
│   └── pom.xml                 # Maven Config
├── frontend/
│   ├── cypress/                # E2E Tests
│   ├── src/
│   │   ├── components/         # React Components
│   │   └── services/           # API Services
│   ├── cypress.config.js       # Cypress Config
│   └── package.json            # npm Config
└── docs/
    ├── testkonzept/            # Testkonzept & Ergebnisse
    ├── ki-nutzung/             # KI-Nutzung Dokumentation
    └── projekt/                # Projektdokumentation
```

---

## 🔍 Troubleshooting

### Problem: Maven Build fehlgeschlagen

```bash
# Cache löschen und neu bauen
cd backend
mvn clean
rm -rf ~/.m2/repository
mvn install -DskipTests
```

### Problem: Frontend Build fehlgeschlagen

```bash
# node_modules löschen und neu installieren
cd frontend
rm -rf node_modules package-lock.json
npm install
```

### Problem: Tests schlagen fehl

```bash
# Backend: Verbose Output
cd backend
mvn test -X

# Frontend: Verbose Output
cd frontend
npm test -- --verbose
```

### Problem: Pipeline schlägt fehl

1. Prüfe den fehlgeschlagenen Job in GitHub Actions
2. Schaue in die Logs für Fehlermeldungen
3. Führe den entsprechenden Befehl lokal aus
4. Fixe den Fehler und pushe erneut

---

## 📝 Wichtige Befehle Zusammenfassung

```bash
# Backend
cd backend
mvn clean install           # Build
mvn spring-boot:run         # Starten
mvn test                    # Tests
mvn test jacoco:report      # Tests + Coverage
mvn checkstyle:check        # Linter

# Frontend
cd frontend
npm install                 # Dependencies
npm start                   # Starten
npm test                    # Tests
npm run build               # Production Build
npx cypress run             # E2E Tests

# Git
git add -A                  # Alle Änderungen stagen
git commit -m "message"     # Commit
git push origin main        # Push
git pull origin main        # Pull
```

---

## ✅ Checkliste vor dem Push

- [ ] Backend Tests laufen erfolgreich: `mvn test`
- [ ] Backend Coverage > 80%: `mvn verify`
- [ ] Checkstyle fehlerfrei: `mvn checkstyle:check`
- [ ] Frontend Build erfolgreich: `npm run build`
- [ ] Alle Änderungen committed: `git status`
- [ ] Push auf main: `git push origin main`
- [ ] Pipeline grün in GitHub Actions

---

## 🎯 Erfüllte Kriterien

| Kriterium | Status |
|-----------|--------|
| CI/CD Pipeline automatisiert Build/Test | ✅ |
| Tests automatisch ausgeführt | ✅ |
| 80%+ Code Coverage | ✅ (89%) |
| Linter konfiguriert | ✅ Checkstyle |
| Testkonzept dokumentiert | ✅ |
| Testergebnisse dokumentiert | ✅ |
| Traceability Matrix | ✅ |
| KI-Nutzung dokumentiert | ✅ |

