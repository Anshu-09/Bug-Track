# Bug Track - Progress Tracker

This file tracks every completed step and the next step to continue from.
Feed this file to the AI assistant to resume from where we left off.

---

## Project Info
- Project Name: Bug Track
- Repo: Bug-Track (GitHub)
- Backend folder: `bugtrack-backend/`
- Backend runs on: `http://localhost:8081`
- Database: PostgreSQL, database name: `bugtrack`
- Java: 17, Maven: 3.9.6, Spring Boot: 3.2.5

---

## Completed Steps

### Step 1 - README
- Created `README.md` in root of `Bug-Track/`
- Pushed to GitHub

### Step 2 - Spring Boot Project Setup
- Generated Maven project using `mvn archetype:generate`
- Folder: `bugtrack-backend/`
- Replaced `pom.xml` with Spring Boot 3.2.5 dependencies:
  - spring-boot-starter-web
  - spring-boot-starter-data-jpa
  - spring-boot-starter-security
  - postgresql
  - jjwt-api, jjwt-impl, jjwt-jackson (version 0.11.5)
  - spring-boot-starter-test
- Pushed to GitHub

### Step 3 - application.properties
- Created `src/main/resources/application.properties`
- Configured PostgreSQL connection (database: bugtrack, port: 5432)
- Configured Hibernate (ddl-auto: update, show-sql: true)
- Configured JWT secret and expiration (86400000ms = 24 hours)
- Server port set to 8081 (8080 was occupied by Oracle TNS Listener)
- Pushed to GitHub

### Step 4 - Main Entry Point
- Replaced `App.java` with `BugTrackApplication.java`
- Added `@SpringBootApplication` annotation
- Replaced `AppTest.java` with `BugTrackApplicationTests.java` (JUnit 5)
- Verified Spring Boot starts successfully and connects to PostgreSQL
- Pushed to GitHub

### Step 5 - User Entity + UserRepository
- Created `src/main/java/com/bugtrack/user/User.java`
  - Fields: id (UUID), name, email (unique), password, createdAt
  - Annotations: @Entity, @Table, @Id, @GeneratedValue, @PrePersist
- Created `src/main/java/com/bugtrack/user/UserRepository.java`
  - Extends JpaRepository<User, UUID>
  - Custom method: findByEmail(String email)
- Pushed to GitHub

---

## Next Step → Step 6: Auth Module

Build the full Auth module inside `src/main/java/com/bugtrack/auth/`:

Files to create in order:
1. `JwtUtil.java` → generates and validates JWT tokens using the secret from application.properties
2. `AuthRequest.java` → DTO (Data Transfer Object) for login/register request body { name, email, password }
3. `AuthResponse.java` → DTO for response body { token }
4. `AuthService.java` → handles register (BCrypt hash password, save user) and login (validate password, return JWT)
5. `AuthController.java` → REST endpoints: POST /auth/register and POST /auth/login

After Auth module → Step 7: SecurityConfig.java
- Configure Spring Security to allow /auth/** without JWT
- Protect all other endpoints with JWT filter

---

## Full Roadmap

- [x] README
- [x] Spring Boot setup + PostgreSQL config
- [x] User entity + UserRepository
- [ ] Auth module (JwtUtil, AuthRequest, AuthResponse, AuthService, AuthController) ← NEXT
- [ ] SecurityConfig.java
- [ ] Project entity + InviteCodeGenerator
- [ ] ProjectMember entity + join via invite code
- [ ] Issue entity + CRUD + algorithm classes (IssueSorter, IssueSearchEngine, StatusStateMachine, RoundRobinAssigner)
- [ ] Activity log module + ActivityStack
- [ ] React frontend (auth → dashboard → project → issue form)
- [ ] Connect React to Spring Boot APIs
- [ ] Full flow testing with Postman
