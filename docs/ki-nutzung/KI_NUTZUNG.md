# KI-Nutzung Dokumentation

## Übersicht

Dieses Dokument dokumentiert alle Stellen im Projekt, an denen KI-Tools verwendet wurden.

**Wichtig:** Es werden nur die **Ergebnisse** dokumentiert, nicht die Prompts oder der Prozess.

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

## Hinweise

- Diese Liste wird während der Entwicklung aktualisiert
- Neue KI-Nutzungen sollten hier dokumentiert werden
- Code-Kommentare mit `// KI-generiert` oder `/* KI-generiert */` sind optional

---

## Template für neue Einträge

```markdown
### [Feature/Modul Name]

#### [Spezifische Komponente]
- **Was:** [Beschreibung]
- **Wo:** `[Dateipfad]`
- **Notiz:** [Kurze Beschreibung]
```

