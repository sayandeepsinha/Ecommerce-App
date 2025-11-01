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

#### 1. Start the Backend (Spring Boot)
```bash
cd backend
./gradlew bootRun
```
The backend will start on `http://localhost:8080` (default Spring Boot port).

#### 2. Start the Frontend (Next.js)
Open a new terminal and run:
```bash
cd frontend
npm install  # Only needed first time or after dependency changes
npm run dev
```
The frontend will start on `http://localhost:3000` (default Next.js port).

#### 3. Open in Browser
Once both servers are running:
- Open your browser and navigate to: **http://localhost:3000**
- The Next.js development server will automatically open in your default browser
- You can also manually open any browser and go to `http://localhost:3000`

The frontend will proxy API requests to the backend server running on port 8080.