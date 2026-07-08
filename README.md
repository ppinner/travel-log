# Travel Log

A review hub for backpackers and budget travellers: a world map dashboard where zooming in reveals
places rated on Cost, Authenticity, Enjoyment, and Fitness Required, with local tour guide contacts
attached to posts and a personal trip planner for saving places you want to visit or have visited.

## Stack

- **Frontend**: React + Vite (JavaScript), `react-leaflet` for the map, plain CSS (mobile-first)
- **Backend**: Java 21, Spring Boot, Spring Security (JWT), Spring Data MongoDB
- **Database**: MongoDB (Atlas free tier for local dev — see setup below)

## Prerequisites

- JDK 21 (this project was set up using [Eclipse Temurin 21](https://adoptium.net/))
- Node.js 16.3+ and npm (Vite 4 is pinned for compatibility with older Node)
- A MongoDB Atlas free-tier cluster (see below)

## MongoDB Atlas setup (one-time)

1. Create a free account at https://www.mongodb.com/cloud/atlas/register
2. Create a free (M0) cluster.
3. Under **Database Access**, create a database user with a username/password.
4. Under **Network Access**, add your current IP (or `0.0.0.0/0` for local dev only).
5. Click **Connect** on your cluster → **Drivers** → copy the connection string, e.g.:
   `mongodb+srv://<username>:<password>@cluster0.xxxxx.mongodb.net/travellog?retryWrites=true&w=majority`
6. Export it as an environment variable before starting the backend:
   ```
   export MONGODB_URI="mongodb+srv://<username>:<password>@cluster0.xxxxx.mongodb.net/travellog?retryWrites=true&w=majority"
   ```

`docker-compose.yml` is included as an alternative if you later set up Docker locally — it runs a local
`mongo:7` instance and requires no `MONGODB_URI` override (the app defaults to
`mongodb://localhost:27017/travellog`).

## Running locally

Backend (from `backend/`):
```
JAVA_HOME=<path-to-jdk-21> ./mvnw spring-boot:run
```

Frontend (from `frontend/`):
```
npm install
npm run dev
```

The Vite dev server proxies `/api/*` requests to `http://localhost:8080`, so the frontend can call
relative paths like `/api/health` without any CORS configuration.

## Environment variables (backend)

| Variable          | Default                                     | Purpose                        |
|--------------------|----------------------------------------------|---------------------------------|
| `MONGODB_URI`      | `mongodb://localhost:27017/travellog`        | MongoDB connection string       |
| `JWT_SECRET`       | dev-only placeholder, override in production | Signing key for JWTs            |
| `JWT_EXPIRATION_MS`| `86400000` (24h)                             | JWT lifetime                    |
| `SERVER_PORT`      | `8080`                                       | Backend port                    |
