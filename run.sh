#!/bin/bash
# 1. Clean up existing volumes and containers
docker compose down -v

# 2. Rebuild and start in detached mode
docker compose up --build -d

# 3. Print access instructions
echo "--------------------------------------------------------"
echo "ANOMALOCARIS // SYSTEM IS LIVE"
echo "Access the dashboard at: http://locwalhost:8080"
echo "Monitor application logs with: docker compose logs -f app"
echo "--------------------------------------------------------"