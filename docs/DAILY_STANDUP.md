# 📅 Daily Stand-up Meeting

**Datum:** Montag, 12. Januar 2026, 09:00 Uhr  
**Teilnehmer:** Mustafa, Ali, Sarah  
**Sprint:** Sprint 3 - Testing & CI/CD  
**Moderation:** Mustafa

---

## 🎬 Meeting-Protokoll

### Mustafa (Scrum Master / Backend)

**Was habe ich seit letzter Woche gemacht?**
> "Guten Morgen zusammen! Also, ich habe letzte Woche einiges geschafft:
> - Die **Backend-Tests** erweitert - wir haben jetzt 62 Tests insgesamt
> - **CriterionProgressServiceTest** mit 12 neuen Tests implementiert
> - **KriterienLoaderServiceTest** mit 5 Tests hinzugefügt
> - Coverage ist jetzt bei **89%**, also deutlich über unserem 80%-Ziel
> - Die **Legacy-Code Probleme** behoben - alte Services entfernt die nicht mehr gebraucht wurden
> - Die **Testergebnisse-Dokumentation** komplett aktualisiert"

**Was mache ich heute?**
> "Heute werde ich:
> - Die **Traceability Matrix** nochmal durchgehen und mit dem Team besprechen
> - Die **Branch Protection Rules** in GitHub aktivieren - das fehlt uns noch für die volle Punktzahl
> - Mit Sarah die **E2E-Tests** anschauen"

**Gibt es Blocker?**
> "Ja, ein kleines Problem: Die **Mockito-Kompatibilität mit Java 23** hat uns Kopfschmerzen bereitet. Musste einige Tests als Integration-Tests umschreiben statt Unit-Tests. Hat funktioniert, aber war mehr Aufwand als geplant."

---

### Ali (Frontend / Testing)

**Was habe ich seit letzter Woche gemacht?**
> "Hey Leute! Bei mir:
> - **Frontend Unit-Tests** geschrieben für PersonForm, CriterionCard und GradesDisplay
> - Das sind etwa **33 neue Frontend-Tests**
> - Die **Cypress E2E-Tests** aufgesetzt und erste Tests für den Person-Flow implementiert
> - Hab auch die **cypress.config.js** konfiguriert und die Custom Commands erstellt
> - Die Tests laufen lokal alle durch"

**Was mache ich heute?**
> "Heute hab ich vor:
> - Mit Mustafa die **E2E-Tests in die Pipeline** zu integrieren
> - Die Frontend-Tests mal lokal mit Coverage laufen lassen
> - Die **GradesDisplay Komponente** hat noch ein paar Edge Cases die ich testen will"

**Gibt es Blocker?**
> "Jein... die E2E-Tests in die Pipeline zu bringen ist nicht trivial. Wir brauchen einen laufenden Backend-Server und Frontend gleichzeitig. Hab schon recherchiert, aber das braucht noch etwas Zeit."

---

### Sarah (DevOps / Dokumentation)

**Was habe ich seit letzter Woche gemacht?**
> "Morgen! Also ich war hauptsächlich bei der Doku und DevOps:
> - Die **KI-Nutzung Dokumentation** komplett überarbeitet - ist jetzt viel detaillierter
> - Die **Branch Protection Anleitung** geschrieben - Step-by-Step Guide
> - Die **Traceability Matrix** erstellt - zeigt jetzt welche Anforderung von welchem Test abgedeckt wird
> - Die **CI/CD Pipeline Dokumentation** in PROJEKT_ABLAUF.md erweitert
> - Alle YAML-Schritte sind jetzt kommentiert"

**Was mache ich heute?**
> "Heute:
> - Die **Branch Protection Rules** tatsächlich in GitHub aktivieren
> - Die **finale Bewertungsdoku** nochmal durchgehen
> - Screenshots von der Branch Protection für den Nachweis machen
> - Vielleicht noch ein **ER-Diagramm** für die Datenbank erstellen"

**Gibt es Blocker?**
> "Eigentlich nur eine Frage: Wollen wir das **Staging-Deployment** noch machen? Das steht noch offen in der Anforderungsliste. Meiner Meinung nach ist das viel Aufwand für wenig Mehrwert, aber wir sollten das im Team entscheiden."

---

## 📊 Sprint Status

### Abgeschlossen ✅
- [x] Backend Tests erweitern (89% Coverage)
- [x] Testkonzept dokumentieren
- [x] Testergebnisse dokumentieren
- [x] Traceability Matrix erstellen
- [x] CI/CD Pipeline vollständig dokumentieren
- [x] KI-Nutzung dokumentieren
- [x] Frontend Tests implementieren
- [x] E2E-Tests mit Cypress erstellen

### In Arbeit 🔄
- [ ] Branch Protection Rules aktivieren
- [ ] E2E-Tests in Pipeline integrieren

### Offen ❌
- [ ] Staging-Deployment konfigurieren (bewusst offengelassen)

---

## 🗣️ Diskussion

**Mustafa:** "Zum Staging-Deployment - ich würde sagen wir lassen das erstmal. Wir haben 89% Coverage, alle Tests laufen, die Doku ist komplett. Das ist schon sehr gut."

**Ali:** "Stimme zu. Der Aufwand für ein komplettes Staging-Setup ist ziemlich hoch. Müssten Docker Compose in der Pipeline haben, einen Server irgendwo hosten..."

**Sarah:** "Okay, dann dokumentieren wir das als 'bewusst offen gelassen' in der Bewertungsdoku. Ist ja auch realistisch - in echten Projekten macht man auch nicht immer alles."

**Mustafa:** "Genau. Dann lass uns die Branch Protection noch heute aktivieren, das ist der wichtigste offene Punkt. Wer macht das?"

**Sarah:** "Ich kann das übernehmen, hab ja die Anleitung geschrieben."

**Ali:** "Cool, und ich schau mir nochmal die E2E-Pipeline-Integration an. Wenn wir das noch hinkriegen, wäre das nice-to-have."

---

## 📋 Action Items

| Task | Owner | Deadline |
|------|-------|----------|
| Branch Protection aktivieren | Sarah | Heute EOD |
| Screenshots für Nachweis | Sarah | Heute EOD |
| E2E-Pipeline Integration recherchieren | Ali | Morgen |
| Code Review der neuen Tests | Mustafa | Heute 14:00 |
| Finale Bewertung checken | Alle | Heute 16:00 |

---

## 🎯 Sprint-Ziel

> **"Alle Testing- und DevOps-Anforderungen erfüllen mit mindestens 80% Coverage und vollständiger Dokumentation"**

**Status:** ✅ **ERREICHT** (89% Coverage, 62 Tests, vollständige Doku)

---

## 📝 Notizen

- **Nächstes Daily:** Dienstag, 13. Januar 2026, 09:00 Uhr
- **Sprint Review:** Freitag, 16. Januar 2026
- **Retrospektive:** Freitag, 16. Januar 2026, nach Sprint Review

---

*Meeting beendet um 09:18 Uhr*



