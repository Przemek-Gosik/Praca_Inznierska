# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Brain UTrain - A web application for learning fast reading, writing, and memorization. Full-stack monorepo with Angular frontend and Spring Boot backend.

## Commands

### Frontend (from `frontend/` directory)
```bash
npm install          # Install dependencies
ng serve             # Dev server at http://localhost:4200
ng build             # Production build
ng test              # Unit tests (Karma/Jasmine)
npx playwright test  # Run Playwright E2E tests
npx playwright test tests/e2e/login.spec.ts  # Run single E2E test
npm run cy:open      # Open Cypress E2E UI
```

### Backend (from `backend/` directory)
```bash
./mvnw spring-boot:run   # Run Spring Boot at http://localhost:8080
./mvnw test              # Run unit tests
```

### Database
- Start XAMPP (Apache + MySQL)
- Create database `brainutrain` via PHPMyAdmin
- Flyway handles migrations automatically on startup

## Architecture

### Frontend (Angular 14)
```
frontend/src/app/
├── pages/           # Feature modules: Reading, Writing, Memorizing, Account, Admin
├── services/        # 17 services for API communication and state
├── models/          # TypeScript interfaces
├── helpers/         # Guards (auth.guard.ts), utilities
├── consts/          # Constants
├── directives/      # Custom directives
└── app-routing.module.ts  # Route definitions
```

### Backend (Spring Boot 2.7)
```
backend/src/main/java/com/example/brainutrain/
├── controller/      # REST controllers (7)
├── service/         # Business logic
├── model/           # JPA entities (14)
├── repository/      # Data access
├── dto/             # Request/Response objects
├── config/          # Security, CORS, Mail, Swagger
├── mapper/          # MapStruct entity-DTO mappers
├── constants/       # Enums (Theme, Level, TypeMemory)
└── exception/       # Global exception handling
```

### API Structure
- Auth: `/api/auth/*`
- Courses: `/api/reading/*`, `/api/writing/*`, `/api/memorizing/*`
- Admin: Role-protected endpoints
- Swagger docs: `/docs`

### Key Patterns
- JWT authentication (token in localStorage)
- Role-based access: User, Admin
- Layered backend: Controller → Service → Repository → Entity
- MapStruct for DTO conversions
- Flyway for DB migrations (`backend/src/main/resources/db/migration/`)

## Tech Stack

| Layer | Technology |
|-------|------------|
| Frontend | Angular 14, TypeScript, Material Design, RxJS |
| Backend | Spring Boot 2.7, Java 17, Spring Security, Spring Data JPA |
| Database | MySQL 5.7+ |
| Testing | Karma/Jasmine (unit), Playwright & Cypress (E2E) |
| API Docs | Swagger/SpringFox 3.0 |

## E2E Testing Notes

Playwright tests are in `frontend/tests/e2e/`. Global setup (`frontend/globalSetup.ts`) resets the database before test runs via `/api/reset-database` endpoint.

Run with backend and frontend servers running:
```bash
cd frontend
npx playwright test
npx playwright test --ui  # Interactive mode
```
