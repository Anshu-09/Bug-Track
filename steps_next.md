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

### Step 6 - Auth Module
- Created `src/main/java/com/bugtrack/auth/JwtUtil.java`
  - generateToken(email) → creates JWT token
  - extractEmail(token) → reads email from token
  - isTokenValid(token) → checks token expiry and signature
- Created `src/main/java/com/bugtrack/auth/AuthRequest.java`
  - DTO for incoming request body: name, email, password
- Created `src/main/java/com/bugtrack/auth/AuthResponse.java`
  - DTO for outgoing response body: token
- Created `src/main/java/com/bugtrack/auth/AuthService.java`
  - register() → BCrypt hash password, save user, return JWT
  - login() → verify password with BCrypt, return JWT
- Created `src/main/java/com/bugtrack/auth/AuthController.java`
  - POST /auth/register
  - POST /auth/login
- Pushed to GitHub

---

## Next Step → Step 7: SecurityConfig

Create two files inside `src/main/java/com/bugtrack/config/`:

1. `JwtFilter.java`
   - Extends OncePerRequestFilter
   - Reads Authorization header from every request
   - Extracts and validates JWT token
   - Sets authenticated user in Spring Security context

2. `SecurityConfig.java`
   - Disable CSRF (not needed for REST APIs)
   - Allow /auth/register and /auth/login without token
   - Protect all other endpoints with JWT
   - Register JwtFilter in the security filter chain

Before creating files, run:
```
mkdir src/main/java/com/bugtrack/config
```

---

## Full Roadmap

- [x] README
- [x] Spring Boot setup + PostgreSQL config
- [x] User entity + UserRepository
- [x] Auth module (JwtUtil, AuthRequest, AuthResponse, AuthService, AuthController)
- [ ] SecurityConfig + JwtFilter ← NEXT
- [ ] Project entity + InviteCodeGenerator
- [ ] ProjectMember entity + join via invite code
- [ ] Issue entity + CRUD + algorithm classes (IssueSorter, IssueSearchEngine, StatusStateMachine, RoundRobinAssigner)
- [ ] Activity log module + ActivityStack
- [ ] React frontend (auth → dashboard → project → issue form)
- [ ] Connect React to Spring Boot APIs
- [ ] Full flow testing with Postman
