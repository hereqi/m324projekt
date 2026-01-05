# Docker Datenbank Setup

## Option 1: Mit Docker Compose (Empfohlen - Einfachste Methode)

### Schritt 1: Docker Desktop öffnen
- Öffne Docker Desktop auf deinem Mac
- Stelle sicher, dass Docker läuft (Icon in der Menüleiste sollte grün sein)

### Schritt 2: Datenbank starten
```bash
cd /Users/eqi/Desktop/m324-projekt
docker-compose -f docker-compose.db.yml up -d
```

### Schritt 3: Prüfen ob es läuft
```bash
docker ps
```
Du solltest `m324-postgres` sehen.

### Schritt 4: Datenbank stoppen (wenn nötig)
```bash
docker-compose -f docker-compose.db.yml down
```

---

## Option 2: Manuell in Docker Desktop

### Schritt 1: Docker Desktop öffnen
- Öffne Docker Desktop

### Schritt 2: PostgreSQL Container erstellen
1. Gehe zu "Containers" im Docker Desktop
2. Klicke auf "Add" oder "Create"
3. Suche nach: `postgres:15-alpine`
4. Klicke auf "Run"

### Schritt 3: Container konfigurieren
Im Dialog "Run a container":

**Container Name:** `m324-postgres`

**Ports:**
- Host: `5432`
- Container: `5432`

**Environment Variables:**
- `POSTGRES_DB` = `kriterien_db`
- `POSTGRES_USER` = `kriterien_user`
- `POSTGRES_PASSWORD` = `kriterien_password`

**Volumes (optional, für Datenpersistenz):**
- Host: Wähle einen lokalen Ordner (z.B. `~/docker/postgres-data`)
- Container: `/var/lib/postgresql/data`

### Schritt 4: Container starten
- Klicke auf "Run"
- Der Container sollte jetzt in der Liste erscheinen und laufen

---

## Option 3: Mit Terminal-Befehl (Schnellste Methode)

```bash
docker run -d \
  --name m324-postgres \
  -e POSTGRES_DB=kriterien_db \
  -e POSTGRES_USER=kriterien_user \
  -e POSTGRES_PASSWORD=kriterien_password \
  -p 5432:5432 \
  -v postgres_data:/var/lib/postgresql/data \
  postgres:15-alpine
```

---

## Datenbank-Verbindung testen

### Mit psql (falls installiert):
```bash
psql -h localhost -U kriterien_user -d kriterien_db
# Passwort: kriterien_password
```

### Oder mit Docker:
```bash
docker exec -it m324-postgres psql -U kriterien_user -d kriterien_db
```

---

## Wichtige Informationen

**Datenbank-Details:**
- **Host:** `localhost`
- **Port:** `5432`
- **Database:** `kriterien_db`
- **User:** `kriterien_user`
- **Password:** `kriterien_password`

**Connection String für Backend:**
```
jdbc:postgresql://localhost:5432/kriterien_db
```

---

## Troubleshooting

**Port bereits belegt?**
- Prüfe ob PostgreSQL bereits lokal läuft: `lsof -i :5432`
- Oder ändere den Port in `docker-compose.db.yml` zu `5433:5432`

**Container startet nicht?**
- Prüfe Logs: `docker logs m324-postgres`
- Stelle sicher, dass Docker Desktop läuft

**Daten löschen?**
```bash
docker-compose -f docker-compose.db.yml down -v
```

