import React, { useEffect, useState } from "react";
import { quizApi } from "../config/axiosApi";
import { Session } from "../../types/session";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import { LoadSessionRequest } from "../../types/request/loadSessionRequest";

export default function HomePage() {
  const [username, setUsername] = useState<string>("");
  const [sessionKey, setSessionKey] = useState<string>("");
  const [sessions, setSessions] = useState<Session[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    quizApi
      .get("/session/get-sessions") //FIX LATER
      .then((response) => {
        setSessions(response.data);
      })
      .catch((error) => {
        console.error("Error fetching sessions:", error);
        alert("Failed to load sessions. Please try again later.");
      });
  }, []);

  const handleNewQuiz = () => {
    if (!username) {
      alert("Please provide a username...");
      return;
    }

    Cookies.set("username", username);
    navigate("/create");
  };

  const handleExistingQuiz = () => {
    const request: LoadSessionRequest = { username };

    if (!username || !sessionKey) {
      alert("Please provide a username and session key...");
      return;
    }

    Cookies.set("username", username);

    quizApi
      .put(`/session/${sessionKey}/load`, request)
      .then((response) => {
        const session = response.data;

        Cookies.set("sessionKey", sessionKey);
        navigate("/lobby", { state: session });
      })
      .catch((error) => {
        console.error("Error loading existing session:", error);
        alert("Failed to load session. Please double-check session key...");
      });
  };

  const handleSelectSession = (key: string) => {
    setSessionKey(key);
  };

  return (
    <div>
      <h1 className="title">Welcome to my QuizApp</h1>
      <hr />
      <h3>
        Please select if you wish to start a new Quiz, or if you want to play an
        existing one shown below...
      </h3>
      <hr />
      <h3>Username:</h3>
      <input
        type="text"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        placeholder="Enter your username."
      />
      <button onClick={handleNewQuiz}>Start New Quiz</button>
      <hr />
      <h3>Session Key:</h3>
      <input
        type="text"
        value={sessionKey}
        onChange={(e) => setSessionKey(e.target.value)}
        placeholder="Enter an existing session key."
      />
      <button onClick={handleExistingQuiz}>Join Existing Quiz</button>
      <hr />
      <h2>Available Sessions:</h2>
      <ul>
        {sessions.map((session) => (
          <li key={session.sessionKey}>
            <hr />
            <div className="sessions">
              <h4>
                Session Key: {session.sessionKey} | Theme: {session.theme}
              </h4>
              <button onClick={() => handleSelectSession(session.sessionKey)}>
                Select
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}
