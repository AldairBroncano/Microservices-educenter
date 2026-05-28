# 📚 EDUCENTER – Sistema de Gestión Educativa

Sistema backend desarrollado en Java + Spring Boot, orientado a la gestión académica mediante una arquitectura basada en microservicios.

La plataforma permite administrar usuarios, cursos y calificaciones, incorporando autenticación segura mediante JWT y control de acceso según el rol del usuario.

Actualmente el sistema utiliza Eureka Server para descubrimiento de servicios y API Gateway como punto de entrada centralizado.


---


## 📌 Descripción General

EDUCENTER permite gestionar diferentes funcionalidades académicas según el tipo de usuario:

ADMIN → administración general del sistema

TEACHER → gestión de cursos y calificaciones

STUDENT → visualización de cursos y notas

El sistema está dividido en microservicios independientes para autenticación, usuarios, cursos y notas, permitiendo una arquitectura más modular y escalable.


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

**Aldair Broncano** - Microservices · Java Backend · JWT Security · API Gateway

