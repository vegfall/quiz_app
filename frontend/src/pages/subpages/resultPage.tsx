import React, { useEffect, useState } from "react";
import { RevealScore } from "../../types/conclusion/revealScore";
import { SessionScore } from "../../types/sessionScore";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import { quizApi } from "../config/axiosApi";
import { RevealAlternative } from "../../types/conclusion/revealAlternative";
import { RevealQuestion } from "../../types/conclusion/revealQuestion";

export default function ResultPage() {
  const [score, setScore] = useState<RevealScore | null>(null);
  const [leaderboard, setLeaderboard] = useState<SessionScore[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    const sessionKey = Cookies.get("sessionKey");
    const username = Cookies.get("username");

    if (!sessionKey || !username) {
      alert("Missing session or username. Returning to start...");
      navigate("/");
      return;
    }

    fetchScore(sessionKey, username);
    fetchLeaderboard(sessionKey);
  }, [navigate]);

  const fetchScore = (sessionKey: string, username: string) => {
    quizApi
      .get<RevealScore>(`session/${sessionKey}/${username}/score`)
      .then((response) => {
        setScore(response.data);
      })
      .catch((error) => {
        console.error("Failed to fetch score:", error);
        alert("Filed to get score. Returning to start...");
        navigate("/");
      });
  };

  const fetchLeaderboard = (sessionKey: string) => {
    quizApi
      .get<SessionScore[]>(`session/${sessionKey}/scores`)
      .then((response) => {
        setLeaderboard(response.data);
      })
      .catch((error) => {
        console.error("Failed for fetch leaderboard:", error);
      });
  };

  const getAlternativeStyle = (
    alternative: RevealAlternative,
    selectedKey: number,
  ) => {
    if (alternative.correct)
      return { backgroundColor: "green", color: "white" };
    if (alternative.alternativeKey === selectedKey)
      return { backgroundColor: "red", color: "white" };
    return {};
  };

  const navigateHome = () => {
    Cookies.remove("sessionKey");
    Cookies.remove("username");
    navigate("/");
  };

  return (
    <div>
      {score ? (
        <div>
          <h1 className="title">Quiz Result</h1>
          <hr />
          <h2>Overall Score: {score.score}</h2>
          <hr />
          <div>
            {score.questions.map((question: RevealQuestion, index) => (
              //PUT IN CSS?
              <div key={index} style={{ marginBottom: "20px" }}>
                <h3>
                  {index + 1}. {question.questionText}
                </h3>
                <ul>
                  {question.alternatives.map((alternative) => (
                    <li key={alternative.alternativeKey}>
                      <button
                        style={getAlternativeStyle(
                          alternative,
                          question.chosenAlternativeKey,
                        )}
                        disabled
                      >
                        {alternative.alternativeText}
                      </button>
                    </li>
                  ))}
                </ul>
              </div>
            ))}
          </div>
          <hr />
          <button onClick={navigateHome}>Home</button>
        </div>
      ) : (
        <h2>Loading result...</h2>
      )}
    </div>
  );
}
