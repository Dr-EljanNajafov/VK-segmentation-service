# User Segmentation Service

## Description
This project was developed as part of a VK assignment. VK is a data-driven company that relies on evidence-based approaches and methods. This service allows managing user segments and distributing them randomly based on specified percentages.

## Features
- Create, delete, and edit segments
- Add and remove users from segments
- Randomly assign segments to a percentage of users
- API for retrieving a user's segment list
- Role-based access control (RBAC) for security

## Security & Roles
The service implements role-based access control to ensure only authorized users can perform specific actions:
- **ADMIN**: Full access to manage segments and user assignments.
- **MANAGER**: Can manage user-segment information.

## API Endpoints

### 1. Segment Management
- **POST /api/segments** — Create a segment *(ADMIN)*
- **GET /api/segments** — Retrieve a list of segments
- **GET /api/segements/{id}** — Retriving a segment by id
- **PUT /api/segments/{id}** — Update a segment *(ADMIN)*
- **DELETE /api/segments/{id}** — Delete a segment *(ADMIN)*

### 2. User Management in Segments
- **POST /api/userSegment/assign** — Add a user to a segment *(ADMIN, MANAGER)*
- **POST /api/userSegment/assign_random** — Add random number of users to a segment by percentage *(ADMIN, MANAGER)*
- **GET /api/userSegment/user/{userId}** — Retrieve a list of segments related to user

### 3. User Management
- **POST /api/users** — Create a user *(ADMIN)*
- **GET /api/users** — Retrieve a list of users
- **GET /api/users/{id}** — Retriving a user by id
- **PUT /api/users/{id}** — Update a user *(ADMIN)*
- **DELETE /api/users/{id}** — Delete a user *(ADMIN)*

## Technologies
- **Spring Boot** — Core framework
- **Spring Security & JWT** — Authentication and authorization
- **PostgreSQL** — Database
- **JUnit** — Unit testing framework
  
## Installation & Running
```sh
git clone https://github.com/your-repo/user-segmentation-service.git
cd user-segmentation-service
./mvnw spring-boot:run
```
