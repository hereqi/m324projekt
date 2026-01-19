# 📅 Daily Stand-up – Tag 3

**Datum:** Mittwoch, 14. Januar 2026, 09:00 Uhr  
**Teilnehmer:** Mustafa, Lion, Cedric  
**Sprint:** Sprint 3 - Testing & CI/CD  

---

## Mustafa

**Was habe ich seit gestern gemacht?**
- Alle Backend-Tests nochmals lokal und in CI ausgeführt (60 Tests grün)
- Legacy-Services entfernt und Jacoco-Konfiguration vereinfacht
- Pipeline-Status im `ci.yml` überprüft und in der Dokumentation referenziert

**Was mache ich heute?**
- Letzten Feinschliff an FINALE_BEWERTUNG.md
- Prüfen, ob alle Anforderungen aus M324/M450 in der Traceability Matrix abgedeckt sind

**Blocker?**
- Keine.

---

## Lion

**Was habe ich seit gestern gemacht?**
- ESLint-Warnungen im Frontend beseitigt
- Frontend-Build erfolgreich auf `main` getestet
- Fehler in den Tests (act-Warnungen, Selektoren) behoben

**Was mache ich heute?**
- Cypress-Tests nochmals durchlaufen lassen
- In SETUP_ANLEITUNG.md den Abschnitt „Frontend lokal starten & testen“ verifizieren

**Blocker?**
- Keine, alle Tools laufen stabil.

---

## Cedric

**Was habe ich seit gestern gemacht?**
- SETUP_ANLEITUNG.md auf aktuellen Pipeline-Stand gebracht
- TESTERGEBNISSE.md mit neuem Pipeline-Run (#22) aktualisiert
- ARBEITSPAKETE.md geschrieben (Abbildung von User Stories → Arbeitspakete → Branches)

**Was mache ich heute?**
- Review von PROJEKTDOKUMENTATION.md (Vorgehen, Architektur, Testkonzept)
- Letzte Rechtschreib- und Formulierungschecks

**Blocker?**
- Keine.

---

## Sprint-Status (Tag 3)

- ✅ CI/CD Pipeline stabil (Backend, Frontend, Code Quality)
- ✅ Arbeitspakete, Testkonzept, Testergebnisse dokumentiert
- ✅ Branch Protection dokumentiert und im Repo konfiguriert
- ⚠️ Frontend-Coverage < 80% (bewusst als Optimierungspotenzial dokumentiert)


