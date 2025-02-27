# quiz_app
Created for PG3402 exam

## Setup and Play
Although possible to run without docker with some tweaks, the variables has been set up to work docker, 
thus it is a requirement.
### Requirements
- [Git](https://git-scm.com/downloads)
- [Docker](https://docs.docker.com/get-started/get-docker/)
- MySQL user

### Steps
1. Clone repository: https://github.com/vegfall/quiz_app/
2. Navigate to **docker** folder.
3. Rename **.env.example** to **.env**
4. Create an OpenAI API Key: https://platform.openai.com/api-keys
5. Replace the following in **.env**:
    - **OPENAI_API_KEY** replace with API key created in **4.**
    - **DB_USER:** replace with your MySQL username. (Default is **root**).
    - **DB_PASSWORD:** replace with your MySQL password.
6. Start *Docker* on your computer.
7. Run *docker-compose up --build* while in the **docker** folder.
8. Open Consul (http://127.0.0.1:8500/ui/dc1/services).
9. Wait while the game is built. This might take up to 5 minutes. 
There will be some pauses during docker compose for each service to wait for the other to start. Please be patient.
10. Once all six services has a green checkmark go to: http://localhost:3000/

### Warnings & Info (Please Read)
- When joining an existing quiz with a username already in that quiz, it will override the previous user score entry.
- AI is not 100% reliable and might cause some bugs such as:
  - Number of alternatives, might not match what you set at all questions, however it will still work.
  - The return question/alternative might not always follow the expected json structure. If this happens unfortunately 
  the database might have to be reset. This has only happened once and the fix was unfortunately to reset the database.
  I will work on a fix, but for now, if it happens, please reset with the **Cleanup** steps, and set up the game again.
- If AI is not responding, a short mock quiz called **MOCKQUIZ** can be used to test the game.
- Very rarely the services do not wait for MySQL to respond, even with health check. If this happens,
  run *docker-compose down* and try to run it again with *docker-compose up --build*

### Game Instructions
This is a Quiz game with questions dynamically created with OpenAI. 
When starting the game you have two options, either to play an existing game or start a new.
- **Start New Quiz**: 
  - To start a new quiz, type in your username.
  - Click on **Start New Quiz**. 
  - Select a *theme* for the quiz, which can be anything the AI might know about (ex. *Hundreds Year War*). 
  - Select *number of alternatives* for the quiz, where minimum is **2** and maximum is **6**
  - Click on **Create Quiz** and wait a few moments while AI is generating questions before taken to the lobby.
  - Click on **Start Quiz** to start the quiz.
- **Join Existing Quiz**
  - To join an existing quiz, type in your username and either type in or select an available session from the list.
  - Click on **Join Existing Quiz** to be taken to the lobby.
  - Click on **Start Quiz** to start the quiz.

You will now be on the question page. The quiz will go endlessly until either you select **Quit** after a question,
or AI is no longer responding. Once in the question page you will see the following:
- **Score** showing your current score, with **CurrentScore** / **NumberOfQuestions**
- **Question** showing your current question and its **Alternatives**
  - Select the alternative you think is right, and wait a few moments for the result to appear. 
  **Green** for **correct** and **Red** for **Wrong**.
- **Explanation** showing a short explanation of the alternative you selected.
- **Next Question** brings you to the next question.
- **Quit** ends the quiz and brings you to the result page.

Once you end the quiz you will be brought to the result page. Here you will see:
- **Overall Score** your end score, with **TotalScore** / **NumberOfQuestions**
- **Response List** showing you all questions, with chosen and correct answers.
- **Home** which will bring you back to the frontpage.
- **Scoreboard** showing you all other players that had played the current game with 
**TotalScore** / **NumberOfQuestions**

### Cleanup
Following these instructions will remove all Quiz app data from your computer, including scores. 
- In your project folder go to the **docker** folder
- Run the following commands to remove project data:
```shell
docker-compose down -v --rmi local
```
- Some data might still be left behind, so the command below can be executed. 
**Warning! this will also remove data not related to this project, if there are any.**
```shell
docker volume prune
```
- The project folder can now be safely deleted, if it is not wanted.