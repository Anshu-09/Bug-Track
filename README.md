# Bug Track 🐛

A collaborative bug tracking web application for software teams to manage, assign, and resolve project issues efficiently.

---

## What is Bug Track?

Bug Track is a team-based issue tracking platform where developers create shared project workspaces, report bugs, assign them to teammates, and mark them resolved with a GitHub branch link. Teams join projects using a unique **6-character invite code** — no admin approval needed.

---

## Tech Stack

| Layer      | Technology                                      |
|------------|-------------------------------------------------|
| Backend    | Java 17, Spring Boot 3, Spring Security, Hibernate |
| Database   | PostgreSQL                                      |
| Auth       | JWT (JSON Web Tokens), BCrypt                   |
| Build Tool | Maven                                           |
| Frontend   | React + TypeScript                              |
| API Testing| Postman                                         |

---

## Core Features

- **Auth** — Register and login with JWT-based authentication
- **Projects** — Create projects, get a unique 6-char invite code, share with team
- **Join via Invite** — Teammates join a project using the invite code
- **Issue Tracking** — Post bugs with title, description, severity (LOW / MEDIUM / HIGH), and an optional GitHub code reference URL
- **Assign Issues** — Members assign issues to themselves → status moves to `IN_PROGRESS`
- **Resolve Issues** — Paste a GitHub branch link and mark the issue `RESOLVED`
- **Activity Feed** — Every action is logged; team sees the 20 most recent activities per project

---

## Database Schema

- `users` — stores registered users
- `projects` — stores project info and invite code
- `project_members` — maps users to projects
- `issues` — stores all bugs with severity, status, assignee, and branch link
- `activity_log` — logs all team actions per project

---

## Issue Workflow

1. User registers and logs in → receives JWT token
2. Creates a project → gets a 6-char invite code
3. Shares invite code with teammates
4. Teammates join via invite code
5. Any member posts an issue with title, description, severity, and optional GitHub code reference
6. Any member assigns the issue to themselves → status: `IN_PROGRESS`
7. Member fixes the bug and pushes to a GitHub branch
8. Member pastes the GitHub branch link and marks the issue `RESOLVED`
9. Activity feed logs all actions for the entire team

---

## Core Java Algorithms Used

| Class                  | Algorithm / Data Structure         | Purpose                                      |
|------------------------|------------------------------------|----------------------------------------------|
| `InviteCodeGenerator`  | Base36 encoding on UUID hash       | Generate unique 6-char project invite codes  |
| `IssueSorter`          | Comparator + Priority Queue        | Sort issues by severity then date            |
| `IssueSearchEngine`    | KMP string matching                | Search issues by keyword in title/description|
| `StatusStateMachine`   | HashMap of allowed transitions     | Validate issue status changes                |
| `RoundRobinAssigner`   | Circular Queue (LinkedList)        | Auto-assign issues to members in round robin |
| `ActivityStack`        | Deque (ArrayDeque)                 | Maintain per-project recent activity feed    |

---

## API Overview

| Method | Endpoint                          | Description                        |
|--------|-----------------------------------|------------------------------------|
| POST   | `/auth/register`                  | Register a new user                |
| POST   | `/auth/login`                     | Login and receive JWT token        |
| POST   | `/projects`                       | Create a new project               |
| GET    | `/projects/:id`                   | Get project details + members      |
| POST   | `/projects/join/:code`            | Join project via invite code       |
| POST   | `/projects/:id/issues`            | Post a new issue                   |
| GET    | `/projects/:id/issues`            | Get all issues (sorted)            |
| GET    | `/projects/:id/issues?q=`         | Search issues by keyword           |
| PATCH  | `/issues/:id/assign`              | Assign issue to self               |
| PATCH  | `/issues/:id/status`              | Update issue status                |
| PATCH  | `/issues/:id/resolve`             | Add branch link + mark resolved    |
| DELETE | `/issues/:id`                     | Delete an issue                    |
| GET    | `/projects/:id/activity`          | Get recent activity feed (top 20)  |

---

## Frontend Pages

- `LoginPage` / `RegisterPage`
- `Dashboard` — all projects the user belongs to
- `ProjectPage` — all issues for a project with filters
- `IssueForm` — modal to post a new issue

---

## Development Roadmap

- [x] README
- [ ] Spring Boot setup + PostgreSQL config
- [ ] User entity + Auth module (JWT + BCrypt)
- [ ] Project entity + InviteCodeGenerator
- [ ] ProjectMember entity + join via invite code
- [ ] Issue entity + CRUD + all algorithm classes
- [ ] Activity log module + ActivityStack
- [ ] React frontend (auth → dashboard → project → issue form)
- [ ] Connect React to Spring Boot APIs
- [ ] Full flow testing with Postman

---

## Project Structure (Backend)

```
src/main/java/com/bugtrack/
├── auth/
├── project/
├── issue/
├── member/
├── activity/
├── user/
└── config/
```

---

## Getting Started (coming soon)

Setup instructions will be added as the project is built.
