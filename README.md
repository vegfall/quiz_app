# quiz_app
Created for PG3402 exam

## ToDo
- [ ] Setup Frontend
- [ ] Setup Gateway API key to block unauthorized communication
- [ ] Setup Async Comm (RabbitMQ)
- [ ] Setup Docker
- [ ] Setup AI Communication

## Run order
1. Consul --- consul agent -dev -node quizapp
2. Gateway --- mvn spring-boot:run
3. Backend-services --- mvn spring-boot:run 
4. Frontend --- npm run dev