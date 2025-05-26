# 🛠️ LicitariFix - Backend API

Aplicație Spring Boot pentru gestionarea lucrărilor și ofertelor între clienți și meseriași. Include autentificare cu Keycloak, notificări în timp real prin WebSocket și stocare fișiere.

---

## ⚙️ Tehnologii utilizate

- Java 17
- Spring Boot 3+
- Spring Security (OAuth2 + Keycloak)
- Spring WebSocket (STOMP + SockJS)
- PostgreSQL + Liquibase (SQL format)
- Docker (pentru baze de date și Keycloak)
- ModelMapper
- Lombok
- WebSocket (SimpMessagingTemplate)
- Multipart file upload

---

## 🧩 Funcționalități principale

### 🔐 Autentificare
- Integrare completă cu Keycloak
- Roluri suportate: `CLIENT`, `MESERIAS`, `ADMIN`
- Creare useri și login direct din aplicație (Keycloak Admin API)

### 📋 Lucrări (Work Requests)
- Creare lucrare cu poze (upload fișiere)
- Listare lucrări aprobate sau proprii
- Aprobare / respingere de către ADMIN
- Notificări la aprobare/respingere

### 💬 Oferte
- Meseriașii pot trimite oferte către lucrări
- Unicitate pe (meseriaș + lucrare)
- Notificare instant către client

### 🔔 Notificări
- Stocate în DB
- Returnate prin endpointuri REST
- Transmise automat în timp real via WebSocket

---

## 🔗 Endpointuri REST principale

### 🔐 Autentificare
> Gestionată de Keycloak

### 📂 Work Requests

| Metodă | Endpoint                             | Descriere                             |
|--------|--------------------------------------|----------------------------------------|
| GET    | `/api/v1/work-requests/approved`     | Listare lucrări aprobate              |
| GET    | `/api/v1/work-requests/me`           | Lucrările proprii                     |
| GET    | `/api/v1/work-requests/pending`      | Lucrări în așteptare (ADMIN)          |
| POST   | `/api/v1/work-requests`              | Postează o lucrare (multipart)        |
| POST   | `/api/v1/work-requests/{id}/approve` | Aprobare lucrare (ADMIN)             |
| POST   | `/api/v1/work-requests/{id}/reject`  | Respingere lucrare (ADMIN)           |

### 💬 Oferte

| Metodă | Endpoint                        | Descriere                         |
|--------|---------------------------------|------------------------------------|
| GET    | `/api/v1/offers/me`            | Ofertele trimise de utilizator     |
| GET    | `/api/v1/offers/work/{id}`     | Ofertele pentru o anumită lucrare |
| POST   | `/api/v1/offers`               | Trimite ofertă nouă               |

### 🔔 Notificări

| Metodă | Endpoint                              | Descriere                            |
|--------|---------------------------------------|---------------------------------------|
| GET    | `/api/v1/notifications`               | Listare notificări utilizator        |
| GET    | `/api/v1/notifications/unread-count` | Număr notificări necitite            |

---

## 📡 WebSocket Config

- Endpoint STOMP: `/ws`
- Subscriere notificări:  
  `/topic/notifications/{userId}`
- Mesaj primit:
  ```json
  {
    "content": "Ai primit o ofertă nouă pentru lucrarea X."
  }
