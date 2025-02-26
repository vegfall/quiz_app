import React, { useEffect, useState } from "react";
import { Session } from "../../types/session";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import { quizApi } from "../config/axiosApi";

export default function LobbyPage() {
  const [session, setSession] = useState<Session | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const sessionKey = Cookies.get("sessionKey");

    if (!sessionKey) {
      alert("Missing session key. Returning to start...");
      navigate("/");
      return;
    }

    quizApi
      .get<Session>(`session/${sessionKey}/info`)
      .then((response) => {
        setSession(response.data);
      })
      .catch((error) => {
        console.error("Failed to get session details:", error);
        alert("Failed to get session details. Returning to start...");
        navigate("/");
      });
  }, [navigate]);

  const startQuiz = () => {
    const sessionKey = Cookies.get("sessionKey");
    const username = Cookies.get("username");

    if (session) {
      quizApi.put(`session/${sessionKey}/${username}/start`).catch((error) => {
        console.error("Failed to start quiz:", error);
        alert("Failed to get start quiz. Returning to start...");
        navigate("/");
      });

      navigate("/play");
    }
  };

  return (
    <div>
      {session ? (
        <div>
          <h1 className="title">
            Quiz Lobby for Session ({session?.sessionKey})
          </h1>
          <hr />
          <h3>Theme: {session.theme}</h3>
          <hr />
          <h3>Number of alternatives: {session.numberOfAlternatives}</h3>
          <hr />
          <button onClick={startQuiz}>Start Quiz</button>
        </div>
      ) : (
        <div>
          <h2>Loading session...</h2>
        </div>
      )}
    </div>
  );
}
