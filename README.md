# E-Commerce Application

Full-stack e-commerce platform built with **Spring Boot** (backend) and **Next.js** (frontend).

**Tech Stack:**
- Backend: Spring Boot 3.5.6, PostgreSQL, Spring Security
- Frontend: Next.js 15, React 19, Tailwind CSS

**Features:** Product catalog, shopping cart, checkout, order management, admin dashboard.

## Getting Started

### Prerequisites
- Java 21
- Node.js (for Next.js)
- PostgreSQL database running on `localhost:5432`

### Running the Application

#### 1. Start PostgreSQL Database
Ensure PostgreSQL is running on `localhost:5432` with your configured database.

#### 2. Start the Backend (Spring Boot)
```bash
cd backend
./gradlew bootRun
```
Backend runs on `http://localhost:8080`

#### 3. Start Stripe CLI (for webhook testing)
Open a new terminal:
```bash
stripe listen --forward-to localhost:8080/api/checkout/webhook
```
Copy the webhook signing secret and update `application.properties`:
```
stripe.webhook.secret=whsec_your_secret_here
```

#### 4. Start the Frontend (Next.js)
Open a new terminal:
```bash
cd frontend
npm install  # First time only
npm run dev
```
Frontend runs on `http://localhost:3000`

#### 5. Access the Application
Open your browser: **http://localhost:3000**