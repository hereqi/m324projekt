# Umstrukturierungsplan

## Übersicht

Dieses Dokument fasst die empfohlene Umstrukturierung des M324-Projekts zusammen. Ziel ist es, eine klare, wartbare und skalierbare Projektstruktur zu schaffen, ohne bestehende Funktionalität zu verlieren.

---

## Ziele

1. ✅ **Klare Ordnerstruktur** für Backend, Frontend und Dokumentation
2. ✅ **Best Practices** für Spring Boot und React umsetzen
3. ✅ **Wartbarkeit** durch klare Trennung der Verantwortlichkeiten
4. ✅ **Skalierbarkeit** für zukünftige Erweiterungen
5. ✅ **Funktionalität erhalten** - keine Breaking Changes

---

## Neue Projektstruktur

```
m324projekt/
├── backend/                              # Spring Boot Backend
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/ch/m324/
│       │   │   ├── KriterienApplication.java
│       │   │   ├── controller/           # REST-Controller
│       │   │   ├── service/              # Business-Logik
│       │   │   ├── repository/           # Datenbank-Zugriff
│       │   │   ├── model/                # JPA-Entitäten
│       │   │   ├── dto/                  # Data Transfer Objects
│       │   │   │   ├── request/
│       │   │   │   └── response/
│       │   │   ├── exception/            # Exception-Handling
│       │   │   ├── config/               # Konfiguration
│       │   │   └── util/                 # Utilities
│       │   └── resources/
│       │       ├── application.yml
│       │       └── kriterien-beispiel.json
│       └── test/
│           └── java/ch/m324/
│               ├── controller/
│               ├── service/
│               └── repository/
│
├── frontend/                              # React Frontend
│   ├── Dockerfile
│   ├── nginx.conf
│   ├── package.json
│   ├── public/
│   │   └── index.html
│   └── src/
│       ├── index.js
│       ├── index.css
│       ├── App.js
│       ├── App.css
│       ├── components/                    # Wiederverwendbare Komponenten
│       │   ├── common/
│       │   ├── person/
│       │   └── kriterium/
│       ├── pages/                         # Page-Komponenten
│       ├── services/                      # API-Services
│       ├── hooks/                         # Custom Hooks
│       ├── utils/                         # Utility-Funktionen
│       ├── context/                       # React Context (optional)
│       └── assets/                        # Statische Assets
│
├── docs/                                  # Dokumentation
│   ├── ANALYSE.md                         # Repository-Analyse
│   ├── UMSTRUKTURIERUNGSPLAN.md           # Dieser Plan
│   ├── projekt/                           # Projekt-Dokumentation
│   │   ├── GITHUB_ISSUES.md
│   │   ├── ISSUE_SETUP_ANLEITUNG.md
│   │   ├── UMSTRUKTURIERUNG_BACKEND.md
│   │   └── UMSTRUKTURIERUNG_FRONTEND.md
│   ├── ki-nutzung/                        # KI-Nutzung Dokumentation
│   │   └── KI_NUTZUNG.md
│   ├── testkonzept/                       # Testkonzept
│   │   └── TESTKONZEPT.md
│   └── setup/                              # Setup-Anleitungen
│       └── DOCKER_SETUP.md
│
├── docker-compose.yml
├── docker-compose.db.yml
└── README.md
```

---

## Migrationsplan

### Phase 1: Dokumentation organisieren ✅ (Abgeschlossen)

**Status:** ✅ Abgeschlossen

**Durchgeführte Schritte:**
- ✅ `docs/` Ordnerstruktur erstellt
- ✅ Dokumentationsdateien verschoben und organisiert
- ✅ KI-Nutzungsdokumentation erstellt
- ✅ Testkonzept erstellt
- ✅ Analyse-Dokument erstellt

**Ergebnis:**
- Klare Dokumentationsstruktur
- Alle Dokumente kategorisiert
- Neue Dokumente erstellt

---

### Phase 2: Backend-Struktur verbessern

**Status:** ⏳ Ausstehend

**Schritte:**

1. **Package-Struktur erstellen**
   ```bash
   mkdir -p backend/src/main/java/ch/m324/{model,repository,service,dto/request,dto/response,exception,config,util}
   ```

2. **Model-Klassen erstellen**
   - `Person.java`
   - `Kriterium.java`
   - `KriteriumErfuellung.java`

3. **Repository-Interfaces erstellen**
   - `PersonRepository.java`
   - `KriteriumRepository.java`
   - `KriteriumErfuellungRepository.java`

4. **Service-Klassen erstellen**
   - `PersonService.java`
   - `KriteriumService.java`
   - `NotenBerechnungService.java`
   - `KriterienLoaderService.java`

5. **DTOs erstellen**
   - Request-DTOs für alle Create/Update-Operationen
   - Response-DTOs für alle GET-Operationen

6. **Controller refactoren**
   - Controller verwenden Services statt Repositories direkt
   - Controller verwenden DTOs statt Entities
   - Bestehenden `KriteriumController` anpassen

7. **Exception-Handling hinzufügen**
   - Custom Exceptions erstellen
   - `GlobalExceptionHandler` implementieren

8. **Config-Klassen hinzufügen**
   - `CorsConfig.java` (falls nötig)

9. **Tests aktualisieren**
   - Alle Tests an neue Struktur anpassen
   - Mocking für Services statt Repositories

**Detaillierte Anleitung:** Siehe `docs/projekt/UMSTRUKTURIERUNG_BACKEND.md`

---

### Phase 3: Frontend-Struktur verbessern

**Status:** ⏳ Ausstehend

**Schritte:**

1. **Ordnerstruktur erstellen**
   ```bash
   mkdir -p frontend/src/{components/{common,person,kriterium},pages,services,hooks,utils,context,assets/{images,fonts}}
   ```

2. **Services erstellen**
   - `api.js` - Axios-Instanz
   - `personService.js` - Person-API
   - `kriteriumService.js` - Kriterium-API
   - `notenService.js` - Noten-API

3. **Komponenten organisieren**
   - Bestehende Komponenten in entsprechende Ordner verschieben
   - Neue Komponenten-Struktur erstellen

4. **Pages erstellen**
   - `HomePage.js`
   - `PersonDetailPage.js`
   - Routing in `App.js` einrichten

5. **Custom Hooks erstellen**
   - `usePersonen.js`
   - `usePerson.js`
   - `useKriterien.js`

6. **Utils erstellen**
   - `dateUtils.js`
   - `validation.js`
   - `constants.js`

7. **Imports aktualisieren**
   - Alle Imports an neue Struktur anpassen
   - Relative Pfade verwenden

8. **Tests aktualisieren**
   - Test-Imports anpassen
   - Neue Test-Struktur

**Detaillierte Anleitung:** Siehe `docs/projekt/UMSTRUKTURIERUNG_FRONTEND.md`

---

### Phase 4: Root-Verzeichnis aufräumen

**Status:** ⏳ Ausstehend

**Schritte:**

1. **package-lock.json verschieben**
   ```bash
   # Falls package-lock.json im Root existiert, sollte es zu frontend/ verschoben werden
   # Oder gelöscht werden, wenn es nicht benötigt wird
   ```

2. **README.md aktualisieren**
   - Links zu neuer Dokumentationsstruktur hinzufügen
   - Projektstruktur aktualisieren

3. **.gitignore prüfen**
   - Sicherstellen, dass alle Build-Artefakte ignoriert werden

---

## Wichtige Hinweise

### ⚠️ Funktionalität erhalten

Bei allen Umstrukturierungen muss sichergestellt werden:

- ✅ **Imports aktualisieren:** Alle Imports müssen an die neue Struktur angepasst werden
- ✅ **Docker-Konfigurationen:** Docker-Compose und Dockerfiles müssen angepasst werden (falls nötig)
- ✅ **CI/CD-Pipelines:** GitHub Actions müssen aktualisiert werden (falls nötig)
- ✅ **Tests:** Alle Tests müssen weiterhin funktionieren
- ✅ **API-Kompatibilität:** API-Endpoints müssen weiterhin funktionieren

### ⚠️ Schrittweise Migration

**Nicht alles auf einmal ändern!**

- Schritt für Schritt migrieren
- Nach jedem Schritt testen
- Commits für jeden Schritt
- Pull Requests für Reviews

### ⚠️ Testing

Nach jeder Phase:

1. **Lokale Tests ausführen**
   ```bash
   # Backend
   cd backend && ./mvnw test
   
   # Frontend
   cd frontend && npm test
   ```

2. **Manuelle Tests**
   - API-Endpoints testen
   - Frontend-Funktionalität testen
   - Docker-Container starten und testen

3. **CI/CD Pipeline testen**
   - Pull Request erstellen
   - Pipeline-Lauf überprüfen

---

## Priorisierung

### Hoch (Sprint 1)
1. ✅ Dokumentation organisieren
2. ⏳ Backend Package-Struktur erstellen
3. ⏳ Backend Model-Klassen erstellen
4. ⏳ Backend Repository-Interfaces erstellen

### Mittel (Sprint 1-2)
5. ⏳ Backend Service-Layer implementieren
6. ⏳ Backend DTOs erstellen
7. ⏳ Backend Controller refactoren
8. ⏳ Frontend Services erstellen
9. ⏳ Frontend Komponenten-Struktur erstellen

### Niedrig (Sprint 2-3)
10. ⏳ Frontend Pages erstellen
11. ⏳ Frontend Custom Hooks erstellen
12. ⏳ Exception-Handling implementieren
13. ⏳ Root-Verzeichnis aufräumen

---

## Erfolgskriterien

Die Umstrukturierung ist erfolgreich, wenn:

- ✅ Alle Tests bestehen
- ✅ Docker-Container starten ohne Fehler
- ✅ API-Endpoints funktionieren
- ✅ Frontend funktioniert
- ✅ CI/CD Pipeline läuft erfolgreich
- ✅ Code-Coverage bleibt bei >80%
- ✅ Keine Breaking Changes für bestehende Funktionalität

---

## Nächste Schritte

1. **Review:** Diesen Plan mit dem Team besprechen
2. **Priorisierung:** Phasen nach Team-Kapazität priorisieren
3. **Umsetzung:** Schritt für Schritt umsetzen
4. **Dokumentation:** Während der Umsetzung dokumentieren
5. **Testing:** Nach jeder Phase testen

---

## Referenzen

- **Backend-Umstrukturierung:** `docs/projekt/UMSTRUKTURIERUNG_BACKEND.md`
- **Frontend-Umstrukturierung:** `docs/projekt/UMSTRUKTURIERUNG_FRONTEND.md`
- **Repository-Analyse:** `docs/ANALYSE.md`
- **Testkonzept:** `docs/testkonzept/TESTKONZEPT.md`
- **KI-Nutzung:** `docs/ki-nutzung/KI_NUTZUNG.md`

---

## Fragen oder Probleme?

Bei Fragen oder Problemen während der Umstrukturierung:
1. Dokumentation in `docs/` konsultieren
2. Team konsultieren
3. GitHub Issues erstellen

