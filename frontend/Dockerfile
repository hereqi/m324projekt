# Multi-stage build für React Frontend

# Stage 1: Build
FROM node:18-alpine AS build

WORKDIR /app

# Dependencies kopieren und installieren
COPY package*.json ./
RUN npm ci --only=production

# Source Code kopieren
COPY . .

# Build durchführen
RUN npm run build

# Stage 2: Production
FROM nginx:alpine

# Build-Ergebnisse kopieren
COPY --from=build /app/build /usr/share/nginx/html

# Nginx Konfiguration
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]

