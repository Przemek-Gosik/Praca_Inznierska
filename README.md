# Brain UTrain

Aplikacja webowa do nauki szybkiego czytania, pisania i zapamiętywania.

## Backend

```bash
cd backend/src/main/resources
cp application-local.properties.example application-local.properties
# Uzupełnij hasła w application-local.properties
```

Wybierz bazę w `application.properties`:
```properties
spring.profiles.active=mysql,local    # lub postgres,local
```

Utwórz bazę `brainutrain`, następnie:
```bash
cd backend
./mvnw spring-boot:run
```

Serwer: http://localhost:8080

## Frontend

```bash
cd frontend
npm install
ng serve
```

Aplikacja: http://localhost:4200
