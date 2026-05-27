# 📚 EDUCENTER – Sistema de Gestión Educativa

---


## 📌 Descripción General

Sistema de gestión educativa basado en arquitectura de microservicios...


---


## 🏗️ Arquitectura

Microservices

```
                ┌──────────────────────┐
                │     Angular App      │
                │      Frontend        │
                └──────────┬───────────┘
                           │
                           ▼
                ┌──────────────────────┐
                │    API Gateway       │
                │   Spring Cloud GW    │
                └──────────┬───────────┘
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
        ▼                  ▼                  ▼
┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│ auth-service │  │ user-service │  │ grade-service│
│ JWT Security │  │ Users CRUD   │  │ Grades CRUD  │
└──────┬───────┘  └──────┬───────┘  └──────┬───────┘
       │                 │                 │
       └─────────────────┼─────────────────┘
                         ▼
              ┌──────────────────┐
              │   Eureka Server  │
              │ Service Discovery│
              └──────────────────┘

```

## 🚀 Tecnologías Utilizadas

- Java
- Spring boot
- Spring Security
- JWT
- Eureka Server
- Api Gateway
- MySql
- Angular
- Docker
- Swagger
- Postman

```

## 🧪 Testing y Pruebas

Las pruebas de integración y validación de endpoints
se realizan principalmente mediante Postman y Swagger UI.
El frontend Angular se encuentra en desarrollo y se utiliza
para pruebas funcionales básicas.

```
## 👨‍💻 Autor

**Aldair Broncano** - Microservices . Java Backend . · JWT Security · API Gateway

