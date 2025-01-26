# quiz_app
Created for PG3402 exam

## Run order
1. Consul --- consul agent -dev -node quizapp
2. Gateway --- mvn spring-boot:run
3. Backend-services --- mvn spring-boot:run 
4. Frontend --- npm run dev