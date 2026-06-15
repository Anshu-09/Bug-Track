# Bug Track 🐛

A collaborative bug tracking web application for software teams to manage, assign, and resolve project issues efficiently.

---

## What is Bug Track?

Bug Track is a team-based issue tracking platform where developers create shared project workspaces, report bugs, assign them to teammates, and mark them resolved with a GitHub branch link. Teams join projects using a unique **6-character invite code** — no admin approval needed.

---

## Tech Stack

| Layer       | Technology                                         |
|-------------|----------------------------------------------------|
| Backend     | Java 17, Spring Boot 3, Spring Security, Hibernate |
| Database    | PostgreSQL                                         |
| Auth        | JWT (JSON Web Tokens), BCrypt                      |
| Build Tool  | Maven                                              |
| Frontend    | React + TypeScript                                 |
| API Testing | Postman                                            |

---

## Prerequisites

Make sure you have the following installed before setting up the project:

| Tool | Version | Check |
|---|---|---|
| Java | 17 | `java -version` |
| Maven | 3.9+ | `mvn -version` |
| PostgreSQL | 16+ | `psql --version` |
| Node.js | 18+ | `node -v` |
| npm | 9+ | `npm -v` |

---

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/<your-username>/Bug-Track.git
cd Bug-Track
```

---

### 2. Set Up the Database

Open a terminal and connect to PostgreSQL:

```bash
psql -U postgres
```

Create the database:

```sql
CREATE DATABASE bugtrack;
\q
```

---

### 3. Configure the Backend

Open the file:
```
bugtrack-backend/src/main/resources/application.properties
```

Update the following values to match your local setup:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bugtrack
spring.datasource.username=postgres
spring.datasource.password=your_postgres_password
server.port=8081
jwt.secret=bugtrack_super_secret_key_change_this_in_production_32chars_minimum
jwt.expiration=86400000
```

> **Note:** If port `8081` is also occupied, change it to any free port and update the frontend API base URL accordingly.

---

### 4. Run the Backend

```bash
cd bugtrack-backend
mvn spring-boot:run
```

You should see:
```
Started BugTrackApplication in X.XXX seconds
```

Hibernate will automatically create all database tables on first run:
- `users`
- `projects`
- `project_members`
- `issues`
- `activity_log`

---

### 5. Run the Frontend

Open a new terminal:

```bash
cd bugtrack-frontend
npm install
npm run dev
```

Open your browser at:
```
http://localhost:5173
```

---

### 6. Using the App

1. Go to `http://localhost:5173`
2. Click **Register** and create an account
3. After login, you land on the **Dashboard**
4. Click **Create Project** → give it a name → you get a 6-character invite code
5. Share the invite code with teammates → they click **Join Project** and enter the code
6. Inside the project, click **+ New Issue** to report a bug
7. Click **Assign to me** to take ownership → status moves to `IN_PROGRESS`
8. Fix the bug, push to a GitHub branch, paste the branch link and click **Resolve**
9. The **Activity Feed** at the bottom logs every team action

---

## Project Structure

```
Bug-Track/
├── README.md
├── bugtrack-backend/          # Spring Boot backend
│   ├── pom.xml
│   └── src/main/java/com/bugtrack/
│       ├── auth/              # JWT auth module
│       ├── config/            # Security + JWT filter
│       ├── user/              # User entity + repository
│       ├── project/           # Project entity + invite code
│       ├── member/            # Project membership
│       ├── issue/             # Issues + all algorithm classes
│       └── activity/          # Activity log module
└── bugtrack-frontend/         # React + TypeScript frontend
    └── src/
        ├── api/               # Axios API calls
        ├── pages/             # Login, Register, Dashboard, Project
        ├── components/        # Reusable components
        └── types/             # TypeScript interfaces
```

---

## API Overview

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Login and receive JWT token |
| GET | `/projects` | Get all projects for current user |
| POST | `/projects` | Create a new project |
| GET | `/projects/:id` | Get project details |
| POST | `/projects/join/:code` | Join project via invite code |
| POST | `/projects/:id/issues` | Post a new issue |
| GET | `/projects/:id/issues` | Get all issues (sorted) |
| GET | `/projects/:id/issues?q=` | Search issues by keyword |
| PATCH | `/issues/:id/assign` | Assign issue to self |
| PATCH | `/issues/:id/status` | Update issue status |
| PATCH | `/issues/:id/resolve` | Add branch link + mark resolved |
| DELETE | `/issues/:id` | Delete an issue |
| GET | `/projects/:id/activity` | Get recent activity feed |

---

## Core Java Algorithms

| Class | Algorithm | Purpose |
|---|---|---|
| `InviteCodeGenerator` | Base36 encoding on UUID hash | Generate unique 6-char invite codes |
| `IssueSorter` | Comparator + Priority Queue | Sort issues by severity then date |
| `IssueSearchEngine` | KMP string matching | Search issues by keyword |
| `StatusStateMachine` | HashMap of allowed transitions | Validate issue status changes |
| `RoundRobinAssigner` | Circular Queue (LinkedList) | Auto-assign issues to members |
| `ActivityStack` | Deque (ArrayDeque) | Maintain per-project activity feed |

---

## Issue Workflow

```
OPEN → IN_PROGRESS → RESOLVED
```

- `OPEN` → issue reported, unassigned
- `IN_PROGRESS` → member assigned to themselves
- `RESOLVED` → member added GitHub branch link and marked resolved
- `IN_PROGRESS` → `OPEN` is also allowed (reopen)
- `RESOLVED` is a terminal state — cannot transition further
