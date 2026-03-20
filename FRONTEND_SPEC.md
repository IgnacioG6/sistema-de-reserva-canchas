# Frontend Specification — Reserva Canchas

> **Target audience:** Frontend developers and AI agents building the client interface for this API.
> **Base URL:** `http://localhost:8080` (or your deployed host)
> **API prefix:** `/api`

---

## 1. System Overview

### What the application does

A SaaS platform for booking padel courts. Users can browse available courts, make reservations with time/duration selection, and pay online via MercadoPago. Administrators manage courts and have full visibility over all reservations.

### User Roles

| Role    | Description                                                                 |
|---------|-----------------------------------------------------------------------------|
| `CLIENT` | Registered customer. Can manage their own profile and reservations.         |
| `ADMIN`  | Platform administrator. Full access to all resources and management actions. |

### General Business Flow

1. A user registers (`POST /api/users`) and logs in (`POST /api/auth/login`).
2. The user lists available courts (`GET /api/fields`) and picks one.
3. The user creates a reservation (`POST /api/reservations`), choosing court, date, start time, and duration.
4. The API responds with the reservation details and a MercadoPago `paymentUrl`.
5. The frontend redirects the user to the `paymentUrl` to complete payment.
6. After payment, MercadoPago redirects back to the app (success/failure URLs configured on the backend).
7. The reservation transitions from `PENDING` to `CONFIRMED` once payment is verified.
8. A `CONFIRMED` reservation can be cancelled by the owner or an ADMIN.

---

## 2. Authentication

### Type

**JWT Bearer Token** — stateless, no server-side sessions.

### Login

```
POST /api/auth/login
```

**Request body:**

```json
{
  "email": "user@example.com",
  "password": "secret123"
}
```

**Successful response `200 OK`:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "user@example.com",
  "role": "CLIENT"
}
```

> Store `token` and `role` in your client state (e.g., `localStorage` or a state manager).

### Registration

```
POST /api/users
```

No token required. See [Users — Create User](#create-user).

### Including the token in every request

Add the following HTTP header to **every authenticated request**:

```
Authorization: Bearer <token>
```

**Example (fetch API):**

```js
fetch('/api/reservations', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`,
  },
  body: JSON.stringify(payload),
})
```

### Roles and access

- **Public** (no token needed): `POST /api/users`, `POST /api/auth/login`
- **Authenticated** (any valid token): most GET endpoints, create/cancel reservations, update own profile
- **ADMIN only**: `GET /api/users`, `PUT /api/users/{id}/deactivate`, `POST /api/fields`, `GET /api/reservations`, `GET /api/reservations/date/{date}`
- **Owner or ADMIN** (enforced by the backend): `GET /api/users/{id}`, `PUT /api/users/{id}`, `GET /api/reservations/{id}`, `GET /api/reservations/user/{id}`, `PUT /api/reservations/{id}/cancel`

If the token is missing or invalid, the server returns `401 Unauthorized`. If the user lacks the required role, the server returns `403 Forbidden`.

---

## 3. API Endpoints

### 3.1 Auth

---

#### Login

| | |
|---|---|
| **Method** | `POST` |
| **Path** | `/api/auth/login` |
| **Auth required** | No |

**Request body:**

| Field      | Type     | Constraints           |
|------------|----------|-----------------------|
| `email`    | `string` | Valid email, not blank |
| `password` | `string` | Not blank             |

```json
{
  "email": "admin@example.com",
  "password": "password123"
}
```

**Response `200 OK`:**

| Field   | Type     | Description                  |
|---------|----------|------------------------------|
| `token` | `string` | JWT Bearer token             |
| `email` | `string` | Authenticated user's email   |
| `role`  | `string` | `"CLIENT"` or `"ADMIN"`      |

```json
{
  "token": "eyJhbGci...",
  "email": "admin@example.com",
  "role": "ADMIN"
}
```

**HTTP status codes:**

| Code | Meaning                    |
|------|----------------------------|
| `200` | Login successful           |
| `401` | Invalid credentials        |
| `400` | Validation error (empty fields, invalid email format) |

---

### 3.2 Users

---

#### Create User

| | |
|---|---|
| **Method** | `POST` |
| **Path** | `/api/users` |
| **Auth required** | No |

New users are always created with role `CLIENT`.

**Request body:**

| Field       | Type     | Constraints |
|-------------|----------|-------------|
| `email`     | `string` | Required    |
| `password`  | `string` | Required    |
| `name`      | `string` | Required    |
| `telephone` | `string` | Required    |

```json
{
  "email": "john@example.com",
  "password": "secret123",
  "name": "John Doe",
  "telephone": "+5491123456789"
}
```

**Response `201 Created`:**

| Field       | Type     | Description       |
|-------------|----------|-------------------|
| `id`        | `number` | User ID           |
| `name`      | `string` | User's full name  |
| `telephone` | `string` | User's telephone  |

```json
{
  "id": 1,
  "name": "John Doe",
  "telephone": "+5491123456789"
}
```

**HTTP status codes:**

| Code | Meaning                          |
|------|----------------------------------|
| `201` | User created successfully        |
| `400` | Validation error                 |
| `409` | Email already registered         |

---

#### Get User by ID

| | |
|---|---|
| **Method** | `GET` |
| **Path** | `/api/users/{id}` |
| **Auth required** | Yes |
| **Role** | ADMIN or owner (CLIENT can only fetch their own) |

**Path parameters:**

| Parameter | Type     | Description |
|-----------|----------|-------------|
| `id`      | `number` | User ID     |

**Response `200 OK`:**

```json
{
  "id": 1,
  "name": "John Doe",
  "telephone": "+5491123456789"
}
```

**HTTP status codes:**

| Code | Meaning                  |
|------|--------------------------|
| `200` | User found               |
| `403` | Forbidden (not owner/admin) |
| `404` | User not found           |

---

#### List Users

| | |
|---|---|
| **Method** | `GET` |
| **Path** | `/api/users` |
| **Auth required** | Yes |
| **Role** | ADMIN only |

**Query parameters:**

| Parameter | Type      | Required | Description                                          |
|-----------|-----------|----------|------------------------------------------------------|
| `active`  | `boolean` | No       | If `true`, returns only active users. Omit for all.  |

**Examples:**
- `GET /api/users` — all users
- `GET /api/users?active=true` — only active users

**Response `200 OK`:** Array of user objects.

```json
[
  {
    "id": 1,
    "name": "John Doe",
    "telephone": "+5491123456789"
  }
]
```

**HTTP status codes:**

| Code | Meaning              |
|------|----------------------|
| `200` | List returned        |
| `403` | Not an ADMIN         |

---

#### Update User

| | |
|---|---|
| **Method** | `PUT` |
| **Path** | `/api/users/{id}` |
| **Auth required** | Yes |
| **Role** | ADMIN or owner |

**Request body:**

| Field       | Type     | Constraints |
|-------------|----------|-------------|
| `email`     | `string` | Optional    |
| `telephone` | `string` | Optional    |

```json
{
  "email": "newemail@example.com",
  "telephone": "+5491199887766"
}
```

**Response `204 No Content`** — empty body.

**HTTP status codes:**

| Code | Meaning                         |
|------|---------------------------------|
| `204` | User updated                    |
| `403` | Forbidden                       |
| `404` | User not found                  |
| `409` | Email already in use            |

---

#### Change Password

| | |
|---|---|
| **Method** | `PUT` |
| **Path** | `/api/users/{id}/change-password` |
| **Auth required** | Yes |
| **Role** | ADMIN or owner |

**Request body:**

| Field         | Type     | Constraints                        |
|---------------|----------|------------------------------------|
| `oldPassword` | `string` | Not blank                          |
| `newPassword` | `string` | Not blank, minimum 6 characters    |

```json
{
  "oldPassword": "currentPass",
  "newPassword": "newSecurePass"
}
```

**Response `204 No Content`** — empty body.

**HTTP status codes:**

| Code | Meaning                                          |
|------|--------------------------------------------------|
| `204` | Password changed                                 |
| `400` | Old password incorrect, passwords match, or validation error |
| `403` | Forbidden                                        |
| `404` | User not found                                   |

---

#### Deactivate User

| | |
|---|---|
| **Method** | `PUT` |
| **Path** | `/api/users/{id}/deactivate` |
| **Auth required** | Yes |
| **Role** | ADMIN only |

No request body.

**Response `204 No Content`** — empty body.

**HTTP status codes:**

| Code | Meaning           |
|------|-------------------|
| `204` | User deactivated  |
| `403` | Not an ADMIN      |
| `404` | User not found    |

---

### 3.3 Fields (Courts)

---

#### Create Field

| | |
|---|---|
| **Method** | `POST` |
| **Path** | `/api/fields` |
| **Auth required** | Yes |
| **Role** | ADMIN only |

**Request body:**

| Field   | Type     | Constraints                  |
|---------|----------|------------------------------|
| `name`  | `string` | Not blank                    |
| `price` | `number` | Not null, minimum value: 50  |

`price` represents the hourly rate in ARS (Argentine Pesos).

```json
{
  "name": "Cancha 1",
  "price": 3000.00
}
```

**Response `201 Created`:**

| Field   | Type     | Description          |
|---------|----------|----------------------|
| `id`    | `number` | Court ID             |
| `name`  | `string` | Court name           |
| `price` | `number` | Hourly rate (ARS)    |

```json
{
  "id": 1,
  "name": "Cancha 1",
  "price": 3000.00
}
```

**HTTP status codes:**

| Code | Meaning                  |
|------|--------------------------|
| `201` | Field created            |
| `400` | Validation error         |
| `403` | Not an ADMIN             |

---

#### List Fields

| | |
|---|---|
| **Method** | `GET` |
| **Path** | `/api/fields` |
| **Auth required** | Yes |

**Response `200 OK`:** Array of field objects.

```json
[
  {
    "id": 1,
    "name": "Cancha 1",
    "price": 3000.00
  },
  {
    "id": 2,
    "name": "Cancha 2",
    "price": 3500.00
  }
]
```

**HTTP status codes:**

| Code | Meaning           |
|------|-------------------|
| `200` | List returned     |
| `403` | Not authenticated |

---

#### Get Field by ID

| | |
|---|---|
| **Method** | `GET` |
| **Path** | `/api/fields/{id}` |
| **Auth required** | No |

**Path parameters:**

| Parameter | Type     | Description |
|-----------|----------|-------------|
| `id`      | `number` | Field ID    |

**Response `200 OK`:**

```json
{
  "id": 1,
  "name": "Cancha 1",
  "price": 3000.00
}
```

**HTTP status codes:**

| Code | Meaning          |
|------|------------------|
| `200` | Field found      |
| `404` | Field not found  |

---

### 3.4 Reservations

---

#### Create Reservation

| | |
|---|---|
| **Method** | `POST` |
| **Path** | `/api/reservations` |
| **Auth required** | Yes |
| **Role** | Authenticated. CLIENTs can only create for themselves. |

> **Note:** ADMINs can pass any `userId`. CLIENTs' `userId` is always overridden with their own authenticated ID, regardless of what is sent.

**Request body:**

| Field      | Type                 | Constraints                          |
|------------|----------------------|--------------------------------------|
| `userId`   | `number`             | Required (ignored for CLIENTs)       |
| `fieldId`  | `number`             | Required                             |
| `date`     | `string` (ISO date)  | Required, format `YYYY-MM-DD`        |
| `startTime`| `string` (ISO time)  | Required, format `HH:mm:ss`          |
| `duration` | `string` (enum)      | Required, see [ReservationDuration](#reservationduration) |

```json
{
  "userId": 1,
  "fieldId": 2,
  "date": "2024-06-15",
  "startTime": "10:00:00",
  "duration": "ONE_HOUR"
}
```

**Response `201 Created`:**

| Field        | Type                     | Description                         |
|--------------|--------------------------|-------------------------------------|
| `reservation`| `ReservationResponseDTO` | Full reservation object (see below) |
| `paymentUrl` | `string`                 | MercadoPago Checkout Pro URL        |

```json
{
  "reservation": {
    "id": 5,
    "name": "John Doe",
    "fieldName": "Cancha 1",
    "startTime": "10:00:00",
    "endTime": "11:00:00",
    "duration": "ONE_HOUR",
    "date": "2024-06-15",
    "price": 3000.00,
    "status": "PENDING"
  },
  "paymentUrl": "https://www.mercadopago.com.ar/checkout/v1/redirect?pref_id=..."
}
```

**ReservationResponseDTO fields:**

| Field       | Type                | Description                              |
|-------------|---------------------|------------------------------------------|
| `id`        | `number`            | Reservation ID                           |
| `name`      | `string`            | Name of the user who made the reservation |
| `fieldName` | `string`            | Name of the court                        |
| `startTime` | `string` (HH:mm:ss) | Start time                               |
| `endTime`   | `string` (HH:mm:ss) | Calculated end time                      |
| `duration`  | `string`            | Enum name: `"ONE_HOUR"`, `"NINETY_MINUTES"`, `"TWO_HOURS"` |
| `date`      | `string` (YYYY-MM-DD) | Reservation date                       |
| `price`     | `number`            | Total price in ARS (calculated)          |
| `status`    | `string`            | `"PENDING"`, `"CONFIRMED"`, `"CANCELLED"` |

**HTTP status codes:**

| Code | Meaning                                      |
|------|----------------------------------------------|
| `201` | Reservation created successfully             |
| `400` | Validation error                             |
| `404` | User or field not found                      |
| `409` | Time slot conflict (court already booked)    |
| `422` | Past date, or invalid reservation state      |
| `502` | MercadoPago payment link creation failed     |

---

#### List All Reservations

| | |
|---|---|
| **Method** | `GET` |
| **Path** | `/api/reservations` |
| **Auth required** | Yes |
| **Role** | ADMIN only |

**Response `200 OK`:** Array of `ReservationResponseDTO` objects.

```json
[
  {
    "id": 1,
    "name": "John Doe",
    "fieldName": "Cancha 1",
    "startTime": "10:00:00",
    "endTime": "11:00:00",
    "duration": "ONE_HOUR",
    "date": "2024-06-15",
    "price": 3000.00,
    "status": "CONFIRMED"
  }
]
```

**HTTP status codes:**

| Code | Meaning      |
|------|--------------|
| `200` | List returned |
| `403` | Not an ADMIN  |

---

#### Get Reservation by ID

| | |
|---|---|
| **Method** | `GET` |
| **Path** | `/api/reservations/{id}` |
| **Auth required** | Yes |
| **Role** | ADMIN or owner of the reservation |

**Path parameters:**

| Parameter | Type     |
|-----------|----------|
| `id`      | `number` |

**Response `200 OK`:** Single `ReservationResponseDTO`.

**HTTP status codes:**

| Code | Meaning                    |
|------|----------------------------|
| `200` | Reservation found          |
| `403` | Forbidden (not owner/admin)|
| `404` | Reservation not found      |

---

#### Get Reservations by User

| | |
|---|---|
| **Method** | `GET` |
| **Path** | `/api/reservations/user/{id}` |
| **Auth required** | Yes |
| **Role** | ADMIN or the user themselves |

**Path parameters:**

| Parameter | Type     | Description |
|-----------|----------|-------------|
| `id`      | `number` | User ID     |

**Response `200 OK`:** Array of `ReservationResponseDTO`.

**HTTP status codes:**

| Code | Meaning               |
|------|-----------------------|
| `200` | List returned         |
| `403` | Forbidden             |
| `404` | User not found        |

---

#### Get Reservations by Date

| | |
|---|---|
| **Method** | `GET` |
| **Path** | `/api/reservations/date/{date}` |
| **Auth required** | Yes |
| **Role** | ADMIN only |

**Query parameters:**

| Parameter | Type                | Description                          |
|-----------|---------------------|--------------------------------------|
| `date`    | `string` (ISO date) | Date to filter by, format `YYYY-MM-DD` |

**Example:** `GET /api/reservations/date/2024-06-15?date=2024-06-15`

**Response `200 OK`:** Array of `ReservationResponseDTO`.

**HTTP status codes:**

| Code | Meaning       |
|------|---------------|
| `200` | List returned  |
| `403` | Not an ADMIN  |

---

#### Get Reservations by Field and Date

| | |
|---|---|
| **Method** | `GET` |
| **Path** | `/api/reservations/field/{fieldId}` |
| **Auth required** | Yes |

**Path parameters:**

| Parameter | Type     | Description |
|-----------|----------|-------------|
| `fieldId` | `number` | Court ID    |

**Query parameters:**

| Parameter | Type                | Description             |
|-----------|---------------------|-------------------------|
| `date`    | `string` (ISO date) | Format `YYYY-MM-DD`     |

**Example:** `GET /api/reservations/field/2?date=2024-06-15`

**Response `200 OK`:** Array of `ReservationResponseDTO`.

> Useful to display occupied time slots before creating a new reservation.

**HTTP status codes:**

| Code | Meaning      |
|------|--------------|
| `200` | List returned |
| `403` | Not authenticated |

---

#### Cancel Reservation

| | |
|---|---|
| **Method** | `PUT` |
| **Path** | `/api/reservations/{id}/cancel` |
| **Auth required** | Yes |
| **Role** | ADMIN or owner of the reservation |

No request body.

**Response `204 No Content`** — empty body.

**HTTP status codes:**

| Code | Meaning                                          |
|------|--------------------------------------------------|
| `204` | Reservation cancelled                            |
| `403` | Forbidden (not owner/admin)                      |
| `404` | Reservation not found                            |
| `422` | Reservation is not in `CONFIRMED` status, or date is in the past |

---

## 4. Enums and Possible Values

### ReservationStatus

| Value       | Description                                          |
|-------------|------------------------------------------------------|
| `PENDING`   | Reservation created, awaiting payment confirmation   |
| `CONFIRMED` | Payment received, reservation is active              |
| `CANCELLED` | Reservation was cancelled                            |

### ReservationDuration

| Value             | Minutes | Price multiplier |
|-------------------|---------|------------------|
| `ONE_HOUR`        | 60      | 1× hourly rate   |
| `NINETY_MINUTES`  | 90      | 1.5× hourly rate |
| `TWO_HOURS`       | 120     | 2× hourly rate   |

**Price calculation:** `totalPrice = field.price × (duration.minutes / 60)`

**Examples** (field price = 3000 ARS/hour):
- `ONE_HOUR` → 3000.00 ARS
- `NINETY_MINUTES` → 4500.00 ARS
- `TWO_HOURS` → 6000.00 ARS

### Role

| Value    | Description              |
|----------|--------------------------|
| `CLIENT` | Regular end user         |
| `ADMIN`  | Platform administrator   |

---

## 5. MercadoPago Payment Flow

### Overview

When a reservation is created, the backend generates a MercadoPago Checkout Pro preference. The frontend must redirect the user to the returned `paymentUrl` to complete payment. MercadoPago handles the payment and notifies the backend via webhook.

### Step-by-step

```
1. Frontend calls POST /api/reservations with court, date, time, and duration.
2. Backend creates the reservation in PENDING status.
3. Backend calls MercadoPago to generate a payment preference.
   - Item title: "Reserva cancha - {reservationId}"
   - Quantity: 1
   - Amount: total price (ARS)
4. Backend returns { reservation, paymentUrl } to the frontend.
5. Frontend stores the reservation ID locally (for status polling or navigation).
6. Frontend redirects the user to paymentUrl (full-page redirect or new tab).
7. The user completes payment on the MercadoPago-hosted checkout page.
8. MercadoPago redirects the user back to the configured success/failure/pending URLs.
9. The backend receives a webhook notification and confirms the reservation
   (status changes from PENDING to CONFIRMED).
10. On return to the app, the frontend can call GET /api/reservations/{id}
    to display the updated status.
```

### Frontend responsibilities

| Step | Action |
|------|--------|
| After `201` response | Save `reservation.id` and redirect to `paymentUrl` |
| On payment success redirect | Navigate to a "payment success" screen |
| On payment failure redirect | Show error and offer retry or cancellation |
| On "my reservations" screen | Fetch `GET /api/reservations/user/{userId}` to show current status |

### Important notes

- `paymentUrl` is a full URL to MercadoPago's checkout page — do **not** embed it in an iframe; use a full redirect or `window.open`.
- The reservation status is updated asynchronously by the backend webhook handler. Do not expect the status to be `CONFIRMED` immediately after returning from MercadoPago.
- If the MercadoPago payment link generation fails (e.g., invalid credentials), the API returns `502 Bad Gateway` and the reservation is **not** created.

---

## 6. Error Handling

### Error response structure

All errors return a JSON object with the following shape:

```typescript
interface ApiErrorResponse {
  status: number;         // HTTP status code (e.g., 404)
  error: string;          // Short error category (e.g., "Not Found")
  message: string;        // Human-readable description of the error
  timestamp: string;      // ISO 8601 datetime (e.g., "2024-06-15T10:30:00")
}
```

**Example:**

```json
{
  "status": 409,
  "error": "Conflict",
  "message": "The court is already booked for that time slot.",
  "timestamp": "2024-06-15T10:30:00"
}
```

### HTTP status codes and their meanings

| Code | Error label                | When it occurs |
|------|----------------------------|----------------|
| `400` | `"Validation Error"` | Request body failed bean validation (missing/invalid fields) |
| `400` | `"Invalid Password"` | New password does not meet minimum requirements |
| `400` | `"Password Not Match"` | Old password does not match current password |
| `400` | `"Password Match"` | New password is the same as the current password |
| `401` | *(Spring Security)* | Missing, expired, or invalid JWT token |
| `403` | *(Spring Security)* | Authenticated but lacks required role/ownership |
| `404` | `"Not Found"` | User or field not found |
| `404` | `"Reservation Not Found"` | Reservation not found |
| `409` | `"Conflict"` | Time slot already booked for that court |
| `409` | `"Email Duplicate"` | Email already registered |
| `422` | `"Invalid State Reservation"` | Attempting to cancel a reservation not in `CONFIRMED` status |
| `422` | `"Invalid Date Reservation"` | Attempting to create or modify a reservation in the past |
| `502` | `"Payment Error"` | MercadoPago integration failed |
| `500` | `"Internal Error"` | Unexpected server-side error |

### Frontend error handling guidelines

- **400**: Display `message` inline on the form field that caused the error.
- **401**: Clear the stored token and redirect to the login screen.
- **403**: Show a "You don't have permission to do this" message.
- **404**: Show a "Resource not found" screen or navigate back.
- **409**: Display `message` to explain the conflict (e.g., "This slot is already taken").
- **422**: Display `message` — likely a date or state validation issue.
- **502**: Inform the user that payment processing is temporarily unavailable.
- **500**: Show a generic "Something went wrong, please try again later" message.

---

## 7. Business Rules Relevant to the Frontend

| Rule | Detail |
|------|--------|
| **Reservation starts as PENDING** | Every new reservation has status `PENDING` until payment is confirmed via webhook. |
| **Only CONFIRMED reservations can be cancelled** | Attempting to cancel a `PENDING` or `CANCELLED` reservation returns `422`. |
| **No past-date reservations** | The `date` + `startTime` must be in the future. The backend validates this on creation. |
| **Available durations** | Only `ONE_HOUR` (60 min), `NINETY_MINUTES` (90 min), and `TWO_HOURS` (120 min) are accepted. |
| **Price is pre-calculated** | The `price` field in `ReservationResponseDTO` is the total price already computed by the backend. Do not recalculate it. |
| **CLIENT can only access own data** | A CLIENT cannot view or modify another user's profile or reservations. The backend enforces this and returns `403`. |
| **Court price is hourly** | The `price` field in `FieldResponseDTO` is the hourly rate in ARS. The total reservation price depends on the selected duration. |
| **USER registration is always CLIENT** | There is no way to register an ADMIN via the public API. Admin accounts must be created by other means. |
| **Deactivated users cannot log in** | If a user is deactivated, authentication will fail with `401`. |
| **Time conflict detection** | If two reservations overlap for the same court, the second one returns `409 Conflict`. Always check available slots with `GET /api/reservations/field/{fieldId}?date=YYYY-MM-DD` before submitting. |

---

## 8. Date and Time Format Reference

All date/time values follow ISO 8601 and are serialized as strings in JSON:

| Java type       | JSON format           | Example               |
|-----------------|-----------------------|-----------------------|
| `LocalDate`     | `"YYYY-MM-DD"`        | `"2024-06-15"`        |
| `LocalTime`     | `"HH:mm:ss"`          | `"10:30:00"`          |
| `LocalDateTime` | `"YYYY-MM-DDTHH:mm:ss"` | `"2024-06-15T10:30:00"` |

---

## 9. Swagger / OpenAPI

The API exposes interactive documentation at:

- **Swagger UI:** `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI JSON:** `http://localhost:8080/v3/api-docs`

These endpoints are **public** and do not require authentication.
