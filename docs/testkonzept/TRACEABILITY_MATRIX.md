# Traceability Matrix

## Übersicht

Diese Matrix dokumentiert die Nachvollziehbarkeit zwischen Anforderungen und deren Tests.
Sie zeigt, welche Tests welche Anforderungen abdecken.

**Letzte Aktualisierung:** 2026-01-12

---

## Anforderungen → Tests Mapping

### Funktionale Anforderungen

| Req-ID | Anforderung | Test-Klasse | Test-Methode | Status |
|--------|-------------|-------------|--------------|--------|
| **F-001** | Personendaten erfassen (Name, Vorname, Thema, Datum) | `PersonControllerIntegrationTest` | `testCreatePerson()` | ✅ |
| **F-002** | Personendaten aktualisieren | `PersonControllerIntegrationTest` | `testUpdatePerson()` | ✅ |
| **F-003** | Personendaten löschen | `PersonControllerIntegrationTest` | `testDeletePerson()` | ✅ |
| **F-004** | Personendaten anzeigen | `PersonControllerIntegrationTest` | `testGetAllPersonen_WithData()`, `testGetPersonById()` | ✅ |
| **F-005** | Kriterien aus JSON laden | `KriterienLoaderServiceTest` | `testLoadCriteriaFromJson_WhenRepositoryEmpty()` | ✅ |
| **F-006** | Kriterien neu laden | `KriterienLoaderServiceTest` | `testReloadCriteria()` | ✅ |
| **F-007** | Kriterien anzeigen | `CriterionControllerIntegrationTest` | `testGetAllCriteria()`, `testGetCriterionById()` | ✅ |
| **F-008** | Kriterien nach Teil filtern | `CriterionControllerIntegrationTest` | `testGetCriteriaByTeil()` | ✅ |
| **F-009** | Anforderungen abhaken | `CriterionProgressControllerIntegrationTest` | `testPostProgress_CreateNew()` | ✅ |
| **F-010** | Fortschritt aktualisieren | `CriterionProgressControllerIntegrationTest` | `testPostProgress_UpdateExisting()` | ✅ |
| **F-011** | Fortschritt pro Person abrufen | `CriterionProgressControllerIntegrationTest` | `testGetProgressByPersonId()` | ✅ |
| **F-012** | Notizen zu Kriterium hinzufügen | `CriterionProgressControllerIntegrationTest` | `testPostProgress_CreateNew()` (mit Notizen) | ✅ |
| **F-013** | Gütestufe automatisch berechnen | `GuetestufeServiceTest` | Alle 19 Testfälle | ✅ |
| **F-014** | Mutmassliche Note berechnen | `GuetestufeServiceTest` | Gütestufen-Berechnungstests | ✅ |

---

### Gütestufen-Berechnung

| Req-ID | Szenario | Test-Methode | Erwartetes Ergebnis | Status |
|--------|----------|--------------|---------------------|--------|
| **G-001** | 0 Anforderungen erfüllt (0%) | `testBerechneGuetestufe_0Anforderungen_Erfuellt()` | Gütestufe 0 | ✅ |
| **G-002** | 1 Anforderung erfüllt (16.7%) | `testBerechneGuetestufe_1Anforderung_Erfuellt()` | Gütestufe 1 | ✅ |
| **G-003** | 2 Anforderungen erfüllt (33.3%) | `testBerechneGuetestufe_2Anforderungen_Erfuellt()` | Gütestufe 2 | ✅ |
| **G-004** | 3 Anforderungen erfüllt (50%) | `testBerechneGuetestufe_3Anforderungen_Erfuellt()` | Gütestufe 2 | ✅ |
| **G-005** | 4 Anforderungen erfüllt (66.7%) | `testBerechneGuetestufe_4Anforderungen_Erfuellt()` | Gütestufe 3 | ✅ |
| **G-006** | 5 Anforderungen erfüllt (83.3%) | `testBerechneGuetestufe_5Anforderungen_Erfuellt()` | Gütestufe 3 | ✅ |
| **G-007** | 6 Anforderungen erfüllt (100%) | `testBerechneGuetestufe_6Anforderungen_Erfuellt()` | Gütestufe 3 | ✅ |
| **G-008** | Null-Handling | `testBerechneGuetestufe_NullProgress()` | Gütestufe 0 | ✅ |
| **G-009** | Kriterium nicht gefunden | `testBerechneGuetestufe_CriterionNichtGefunden()` | Gütestufe 0 | ✅ |
| **G-010** | Grenzfall 33% | `testBerechneGuetestufe_Grenzfall_33Prozent()` | Gütestufe 2 | ✅ |
| **G-011** | Grenzfall 66% | `testBerechneGuetestufe_Grenzfall_66Prozent()` | Gütestufe 3 | ✅ |

---

### REST-API Endpoints

| Endpoint | HTTP | Test-Klasse | Test-Methode | Status |
|----------|------|-------------|--------------|--------|
| `/api/personen` | GET | `PersonControllerIntegrationTest` | `testGetAllPersonen_Empty()`, `testGetAllPersonen_WithData()` | ✅ |
| `/api/personen/{id}` | GET | `PersonControllerIntegrationTest` | `testGetPersonById()`, `testGetPersonById_NotFound()` | ✅ |
| `/api/personen` | POST | `PersonControllerIntegrationTest` | `testCreatePerson()` | ✅ |
| `/api/personen/{id}` | PUT | `PersonControllerIntegrationTest` | `testUpdatePerson()`, `testUpdatePerson_NotFound()` | ✅ |
| `/api/personen/{id}` | DELETE | `PersonControllerIntegrationTest` | `testDeletePerson()`, `testDeletePerson_NotFound()` | ✅ |
| `/api/criteria` | GET | `CriterionControllerIntegrationTest` | `testGetAllCriteria()` | ✅ |
| `/api/criteria/{id}` | GET | `CriterionControllerIntegrationTest` | `testGetCriterionById()`, `testGetCriterionById_NotFound()` | ✅ |
| `/api/criteria/teil/{teil}` | GET | `CriterionControllerIntegrationTest` | `testGetCriteriaByTeil()` | ✅ |
| `/api/criteria/reload` | POST | `CriterionControllerIntegrationTest` | `testReloadCriteria()` | ✅ |
| `/api/criterion-progress` | POST | `CriterionProgressControllerIntegrationTest` | `testPostProgress_CreateNew()`, `testPostProgress_UpdateExisting()` | ✅ |
| `/api/criterion-progress/person/{id}` | GET | `CriterionProgressControllerIntegrationTest` | `testGetProgressByPersonId()` | ✅ |
| `/api/criterion-progress/person/{id}/criterion/{id}` | GET | `CriterionProgressControllerIntegrationTest` | `testGetProgressByPersonAndCriterion()` | ✅ |

---

### Service Layer Tests

| Service | Test-Klasse | Anzahl Tests | Coverage |
|---------|-------------|--------------|----------|
| `GuetestufeService` | `GuetestufeServiceTest` | 19 | 91% |
| `CriterionProgressService` | `CriterionProgressServiceTest` | 12 | 85%+ |
| `KriterienLoaderService` | `KriterienLoaderServiceTest` | 4 | 80%+ |

---

### Fehlerszenarien

| Szenario | Test-Methode | HTTP Status | Status |
|----------|--------------|-------------|--------|
| Person nicht gefunden (GET) | `testGetPersonById_NotFound()` | 404 | ✅ |
| Person nicht gefunden (PUT) | `testUpdatePerson_NotFound()` | 404 | ✅ |
| Person nicht gefunden (DELETE) | `testDeletePerson_NotFound()` | 404 | ✅ |
| Ungültige Person-ID bei Progress | `testPostProgress_InvalidPersonId()` | 400 | ✅ |
| Kriterium nicht gefunden | `testGetCriterionById_NotFound()` | 404 | ✅ |
| Progress nicht gefunden | `testGetProgressByPersonAndCriterion_NotFound()` | 404 | ✅ |

---

## Test-Abdeckung nach User Stories

### US-001: Als Benutzer möchte ich meine Personendaten erfassen

| Akzeptanzkriterium | Test | Status |
|-------------------|------|--------|
| Name eingeben | `testCreatePerson()` | ✅ |
| Vorname eingeben | `testCreatePerson()` | ✅ |
| Thema eingeben | `testCreatePerson()` | ✅ |
| Abgabedatum eingeben | `testCreatePerson()` | ✅ |
| Daten werden gespeichert | `testGetPersonById()` | ✅ |

### US-002: Als Benutzer möchte ich Anforderungen abhaken

| Akzeptanzkriterium | Test | Status |
|-------------------|------|--------|
| Anforderung abhaken | `testPostProgress_CreateNew()` | ✅ |
| Mehrere Anforderungen abhaken | `testPostProgress_AllAnforderungenErfuellt()` | ✅ |
| Abhaken rückgängig machen | `testPostProgress_UpdateExisting()` | ✅ |
| Gütestufe wird automatisch berechnet | Alle `GuetestufeServiceTest` Tests | ✅ |

### US-003: Als Benutzer möchte ich die mutmassliche Note sehen

| Akzeptanzkriterium | Test | Status |
|-------------------|------|--------|
| Gütestufe pro Kriterium anzeigen | `testGetProgressByPersonId()` | ✅ |
| Note für Teil 1 berechnen | `GuetestufeServiceTest` | ✅ |
| Note für Teil 2 berechnen | `GuetestufeServiceTest` | ✅ |

### US-004: Als Benutzer möchte ich Notizen hinterlegen

| Akzeptanzkriterium | Test | Status |
|-------------------|------|--------|
| Notiz hinzufügen | `testPostProgress_CreateNew()` | ✅ |
| Notiz aktualisieren | `testPostProgress_UpdateExisting()` | ✅ |
| Notiz anzeigen | `testGetProgressByPersonAndCriterion()` | ✅ |

---

## Zusammenfassung

| Kategorie | Gesamt | Getestet | Abdeckung |
|-----------|--------|----------|-----------|
| Funktionale Anforderungen | 14 | 14 | **100%** |
| Gütestufen-Szenarien | 11 | 11 | **100%** |
| REST-API Endpoints | 12 | 12 | **100%** |
| Fehlerszenarien | 6 | 6 | **100%** |
| User Stories | 4 | 4 | **100%** |

**Gesamt-Testabdeckung User-Story-Anforderungen: 100%** ✅

---

## Legende

- ✅ Test implementiert und erfolgreich
- ⚠️ Test implementiert, aber fehlgeschlagen
- ❌ Test noch nicht implementiert
- 🔄 Test in Bearbeitung



