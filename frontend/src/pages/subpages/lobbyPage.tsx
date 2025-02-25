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
    if (session) {
      navigate("/play");
    }
  };

  return <div>{session ? <div>Yay</div> : <div>Nay</div>}</div>;
}
