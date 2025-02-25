# quiz_app
Created for PG3402 exam

## ToDo
- [x] Setup Frontend
- [x] Setup Async Comm (RabbitMQ)
- [x] Setup database (MySQL)
- [x] Setup Docker
- [x] Setup Gateway API key to block unauthorized communication
- [ ] Setup AI Communication

## Run order
1. Consul --- consul agent -dev -node quizapp
2. Gateway --- mvn spring-boot:run
3. Backend-services --- mvn spring-boot:run 
4. Frontend --- npm run dev

---
# Setup
### Requirements
- [Git](https://git-scm.com/downloads)
- [Docker](https://docs.docker.com/get-started/get-docker/)
- MySQL user

## Steps
1. Clone repository: https://github.com/vegfall/quiz_app/
2. Navigate to **docker** folder.
3. Rename **.env.example** to **.env**
4. Replace the following in **.env**:
    - **DB_USER:** replace with your MySQL username. (Default is **root**).
    - **DB_PASSWORD:** replace with your MySQL password.
5. Start *Docker* on your computer.
6. Run *docker-compose up --build* while in the **docker** folder.
7. Open Consul (http://127.0.0.1:8500/ui/dc1/services).
8. Once all six services has a green checkmark navigate to: http://localhost:3000/