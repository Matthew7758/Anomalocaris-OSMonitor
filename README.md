1. Prerequisites
   Ensure you have the following installed on your machine:

Docker Desktop (Windows) or Docker Engine (Linux).

NVIDIA Container Toolkit (Required for GPU monitoring).

Java 21+ (For local development/testing).

Host Setup for NVIDIA GPU
You must register the NVIDIA runtime with Docker to avoid driver mismatch errors.

Linux (CachyOS/Arch):

Bash
sudo pacman -S --needed nvidia-container-toolkit
sudo nvidia-ctk runtime configure --runtime=docker
sudo systemctl restart docker
Windows (PowerShell as Admin):
Ensure you have the NVIDIA drivers installed, then enable the Docker engine integration in Docker Desktop settings under Resources > WSL Integration.

2. Project Structure
   Ensure your files are arranged as follows:

/Anomalocaris-OSMonitor/
├── backend/
│ ├── src/main/java/com/anomalocaris/...
│ ├── src/test/java/com/anomalocaris/...
│ ├── Dockerfile
│ └── pom.xml
├── docker-compose.yml
└── README.md

3. Deployment Instructions
   Build and Run
   Use the following commands to initialize the database and the monitoring application.

Linux (Bash):

Bash
docker-compose up --build -d

PowerShell
docker-compose up --build -d

4. Running Tests
   To verify the system integration, run the test suite provided in your backend/ directory.

Linux/Bash:

Bash
cd backend
./mvnw clean test
Windows/PowerShell:

PowerShell
cd backend
.\mvnw.cmd clean test

5. Troubleshooting
   If you encounter ERR_CONNECTION_REFUSED:

Check Container Status:

Bash
docker-compose ps
If the app status is Exit, check logs: docker-compose logs app.

Verify Database:
Ensure SPRING_R2DBC_URL in docker-compose.yml uses the service name db, not localhost.

Driver Mismatch:
If you see Driver/library version mismatch, reboot your host machine to sync the NVIDIA kernel modules with your user-space drivers.

6. Accessing the Dashboard
   Once the containers are healthy, access your monitoring dashboard via your web browser:
   http://localhost:8080/api/recent
