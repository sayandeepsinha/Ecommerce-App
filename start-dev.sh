#!/bin/bash

# Start all development servers
echo "Starting backend..."
cd backend && ./gradlew bootRun &

echo "Starting frontend..."
cd frontend && npm run dev &

echo "Starting Stripe webhooks..."
stripe listen --forward-to localhost:8080/api/checkout/webhook