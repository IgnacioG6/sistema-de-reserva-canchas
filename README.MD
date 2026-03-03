# 🏓 Sistema de Reserva de Canchas

Backend REST API para la gestión de reservas de canchas de pádel desarrollado para **Sombra Padel**.

---

## 🛠️ Stack Tecnológico

| Tecnología | Versión |
|---|---|
| Java | 21 |
| Spring Boot | 3.5.x |
| Spring Security | 6 |
| PostgreSQL | 16 |
| Docker | - |
| JWT (jjwt) | 0.12.6 |
| SpringDoc OpenAPI | 2.8.5 |
| Lombok | - |

---

## 📦 Endpoints

### Autenticación
| Método | Endpoint | Descripción | Acceso |
|---|---|---|---|
| POST | /api/auth/login | Iniciar sesión | Público |

### Usuarios
| Método | Endpoint | Descripción | Acceso |
|---|---|---|---|
| POST | /api/users | Registrar usuario | Público |
| GET | /api/users | Listar usuarios | ADMIN |
| GET | /api/users/{id} | Obtener usuario | ADMIN / propio |
| PUT | /api/users/{id} | Actualizar usuario | ADMIN / propio |
| PUT | /api/users/{id}/change-password | Cambiar contraseña | ADMIN / propio |
| PUT | /api/users/{id}/deactivate | Desactivar usuario | ADMIN |

### Canchas
| Método | Endpoint | Descripción | Acceso |
|---|---|---|---|
| POST | /api/fields | Crear cancha | ADMIN |
| GET | /api/fields | Listar canchas | Autenticado |
| GET | /api/fields/{id} | Obtener cancha | Autenticado |
| PUT | /api/fields/{id}/activate | Activar cancha | ADMIN |
| PUT | /api/fields/{id}/desactivate | Desactivar cancha | ADMIN |

### Reservas
| Método | Endpoint | Descripción | Acceso |
|---|---|---|---|
| POST | /api/reservations | Crear reserva | Autenticado |
| GET | /api/reservations | Listar reservas | ADMIN |
| GET | /api/reservations/{id} | Obtener reserva | ADMIN / propia |
| GET | /api/reservations/user/{id} | Reservas por usuario | ADMIN / propio |
| GET | /api/reservations/date/{date} | Reservas por fecha | ADMIN |
| GET | /api/reservations/field/{id} | Reservas por cancha | Autenticado |
| PUT | /api/reservations/{id}/cancel | Cancelar reserva | ADMIN / propia |

---
