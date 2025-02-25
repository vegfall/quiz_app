import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import { CreateSessionRequest } from "../../types/request/createSessionRequest";
import { quizApi } from "../config/axiosApi";
import { Session } from "../../types/session";

//https://www.npmjs.com/package/js-cookie
export default function CreatePage() {
  const [theme, setTheme] = useState<string>("");
  const [numberOfAlternatives, setNumberOfAlternatives] = useState<number>(4);
  const navigate = useNavigate();

  const createSession = () => {
    const username = Cookies.get("username");

    if (!username) {
      alert("Missing username. Returning to start...");
      navigate("/");
      return;
    }

    if (!theme) {
      alert("Please provide a theme...");
      return;
    }

    const request: CreateSessionRequest = {
      theme,
      numberOfAlternatives,
      username,
    };

    quizApi
      .post("session/create", request)
      .then((response) => {
        const session: Session = response.data;

        Cookies.set("sessionKey", session.sessionKey);

        navigate("/lobby", { state: session });
      })
      .catch((error) => {
        console.error("Error creating session:", error);
      });
  };

  return (
    <div>
      <h1 className="title">QuizApp</h1>
      <hr />
      <h3>Please choose settings for your quiz...</h3>
      <hr />
      <h3>Theme:</h3>
      <input
        type="text"
        value={theme}
        onChange={(e) => setTheme(e.target.value)}
        placeholder="Quiz Theme."
      />
      <hr />
      <h3>Number of alternatives:</h3>
      <input
        type="number"
        value={numberOfAlternatives}
        onChange={(e) => setNumberOfAlternatives(parseInt(e.target.value))}
        placeholder="4"
        min={2}
        max={6}
      />
      <hr />
      <button onClick={createSession}>Create Quiz</button>
    </div>
  );
}
