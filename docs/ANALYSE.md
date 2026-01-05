# Repository-Analyse

## Aktuelle Struktur

```
m324projekt/
├── backend/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/ch/m324/
│       │   │   ├── controller/
│       │   │   │   └── KriteriumController.java
│       │   │   └── KriterienApplication.java
│       │   └── resources/
│       │       ├── application.yml
│       │       └── kriterien-beispiel.json
│       └── test/
│           └── java/ch/m324/
│               └── KriterienApplicationTests.java
├── frontend/
│   ├── Dockerfile
│   ├── nginx.conf
│   ├── package.json
│   ├── public/
│   │   └── index.html
│   └── src/
│       ├── App.css
│       ├── App.js
│       ├── App.test.js
│       ├── index.css
│       └── index.js
├── docker-compose.yml
├── docker-compose.db.yml
├── README.md
├── DOCKER_SETUP.md
├── GITHUB_ISSUES.md
├── ISSUE_SETUP_ANLEITUNG.md
└── package-lock.json (sollte nicht im Root sein)
```

## Identifizierte Probleme

### 1. Backend-Struktur
**Probleme:**
- ❌ Fehlende Package-Struktur: Keine Trennung von `model`, `service`, `repository`, `dto`, `exception`, `config`
- ❌ Controller importiert direkt Model-Klassen (sollte DTOs verwenden)
- ❌ Keine Service-Layer-Trennung
- ❌ Keine Exception-Handling-Struktur
- ❌ Keine Konfigurationsklassen

**Aktueller Zustand:**
- Nur `controller` Package vorhanden
- `KriterienApplication.java` im Root-Package
- Keine Model-Klassen sichtbar (werden aber importiert)

### 2. Frontend-Struktur
**Probleme:**
- ❌ Keine Komponenten-Organisation: Alle Dateien im `src/` Root
- ❌ Keine Trennung von `components`, `services`, `utils`, `hooks`
- ❌ Keine API-Service-Schicht
- ❌ Keine Routing-Struktur sichtbar
- ❌ Keine Assets-Organisation

**Aktueller Zustand:**
- Nur Basis-App.js vorhanden
- Keine Komponenten-Struktur
- Keine Service-Layer für API-Calls

### 3. Dokumentation
**Probleme:**
- ❌ Dokumentation verstreut im Root-Verzeichnis
- ❌ Keine klare Kategorisierung
- ❌ Fehlende `docs/` Struktur
- ❌ Keine KI-Nutzungsdokumentation vorhanden
- ❌ Kein Testkonzept-Dokument

**Aktueller Zustand:**
- README.md, DOCKER_SETUP.md, GITHUB_ISSUES.md, ISSUE_SETUP_ANLEITUNG.md im Root
- Keine organisierte Dokumentationsstruktur

### 4. Root-Verzeichnis
**Probleme:**
- ❌ `package-lock.json` sollte nicht im Root sein (gehört zu frontend)
- ❌ Zu viele Dateien im Root

## Best Practices, die fehlen

### Backend (Spring Boot)
1. **Package-Struktur:**
   ```
   ch.m324/
   ├── controller/     ✅ Vorhanden
   ├── model/          ❌ Fehlt
   ├── repository/     ❌ Fehlt
   ├── service/        ❌ Fehlt
   ├── dto/            ❌ Fehlt
   ├── exception/      ❌ Fehlt
   ├── config/         ❌ Fehlt
   └── util/           ❌ Fehlt
   ```

2. **Layered Architecture:**
   - Controller → Service → Repository → Entity
   - DTOs für API-Kommunikation
   - Exception-Handling mit @ControllerAdvice

### Frontend (React)
1. **Ordnerstruktur:**
   ```
   src/
   ├── components/     ❌ Fehlt
   ├── pages/          ❌ Fehlt
   ├── services/       ❌ Fehlt
   ├── hooks/          ❌ Fehlt
   ├── utils/          ❌ Fehlt
   ├── context/         ❌ Fehlt
   └── assets/         ❌ Fehlt
   ```

2. **Komponenten-Organisation:**
   - Feature-basierte oder Typ-basierte Struktur
   - Wiederverwendbare Komponenten
   - API-Service-Layer

### Dokumentation
1. **Struktur:**
   ```
   docs/
   ├── projekt/        ❌ Fehlt
   ├── ki-nutzung/     ❌ Fehlt
   ├── testkonzept/    ❌ Fehlt
   └── setup/          ❌ Fehlt
   ```

## Empfohlene Umstrukturierung

### Phase 1: Dokumentation organisieren
- `docs/` Ordner erstellen
- Dokumentation kategorisieren und verschieben
- Neue Dokumente erstellen (KI-Nutzung, Testkonzept)

### Phase 2: Backend-Struktur verbessern
- Package-Struktur erweitern
- Model-Klassen organisieren
- Service-Layer hinzufügen
- DTOs einführen
- Exception-Handling implementieren

### Phase 3: Frontend-Struktur verbessern
- Komponenten-Ordner erstellen
- Service-Layer für API-Calls
- Routing-Struktur
- Utilities organisieren

### Phase 4: Root-Verzeichnis aufräumen
- `package-lock.json` verschieben
- Nur essenzielle Dateien im Root behalten

## Funktionalität erhalten

**Wichtig:** Alle Umstrukturierungen müssen:
- ✅ Bestehende Funktionalität beibehalten
- ✅ Imports aktualisieren
- ✅ Docker-Konfigurationen anpassen
- ✅ CI/CD-Pipelines aktualisieren
- ✅ Tests weiterhin funktionieren lassen

