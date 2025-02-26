import React, { useEffect, useState } from "react";
import Question from "../../types/question";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import { SessionStatus } from "../../types/enum/sessionStatus";
import { quizApi } from "../config/axiosApi";
import { Alternative } from "../../types/alternative";
import { PostAnswerRequest } from "../../types/request/postAnswerRequest";
import Result from "../../types/result";

export default function PlayPage() {
  const [question, setQuestion] = useState<Question | null>(null);
  const [selectedAlternative, setSelectedAlternative] = useState<number | null>(
    null,
  );
  const [correctAlternative, setCorrectAlternative] = useState<number | null>(
    null,
  );
  const [explanation, setExplanation] = useState<string | null>(null);
  const [score, setScore] = useState<number | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const sessionKey = Cookies.get("sessionKey");

    if (!sessionKey) {
      alert("Missing session key. Returning to start...");
      navigate("/");
      return;
    }

    getCurrentQuestion(sessionKey);
  }, [navigate]);

  const checkSessionStatus = async (
    sessionKey: string,
  ): Promise<SessionStatus> => {
    try {
      const response = await quizApi.get<SessionStatus>(
        `session/${sessionKey}/status`,
      );
      return response.data;
    } catch (error) {
      console.error("Failed to get session status:", error);
      alert("An error occurred while checking session status.");
      return SessionStatus.COMPLETED;
    }
  };

  const getCurrentQuestion = (sessionKey: string) => {
    quizApi
      .get<Question>(`session/${sessionKey}/current-question`)
      .then((response) => {
        setQuestion(response.data);
        setSelectedAlternative(null);
        setCorrectAlternative(null);
        setExplanation(null);
      })
      .catch((error) => {
        console.error("Failed to get question:", error);
        alert("Failed to get question. Returning to start...");
        navigate("/");
      });
  };

  const putNextQuestion = async () => {
    const sessionKey = Cookies.get("sessionKey");

    if (!sessionKey) {
      alert("Missing session key. Returning to start...");
      navigate("/");
      return;
    }

    const sessionStatus = await checkSessionStatus(sessionKey);

    if (sessionStatus === SessionStatus.COMPLETED) {
      navigate("/result");
      return;
    }

    if (sessionStatus === SessionStatus.ONGOING) {
      quizApi
        .put(`session/${sessionKey}/next-question`)
        .then(() => {
          getCurrentQuestion(sessionKey);
        })
        .catch((error) => {
          console.error("Failed to change question:", error);
          alert("Failed to change question. Returning to start...");
          navigate("/");
        });
    }
  };

  const handleAlternativeClick = (alternative: Alternative) => {
    const sessionKey = Cookies.get("sessionKey");
    const username = Cookies.get("username") || "guest";

    if (!sessionKey) {
      alert("Missing session key. Returning to start...");
      navigate("/");
      return;
    }

    setSelectedAlternative(alternative.alternativeKey);

    const request: PostAnswerRequest = {
      username: username,
      alternativeKey: alternative.alternativeKey,
    };

    quizApi
      .post(`session/${sessionKey}/post-answer`, request)
      .then((response) => {
        const result: Result = response.data;

        setCorrectAlternative(result.correctAlternative);
        setExplanation(result.explanation);
        setScore(result.score);
      })
      .catch((error) => {
        console.error("Failed to submit answer:", error);
        alert("Failed to submit answer. Please try again.");
      });
  };

  const endSession = () => {
    navigate("/result");
  };

  return (
    <div>
      {question ? (
        <div>
          <h1 className="title">Quiz :)</h1>
          <hr />
          <h4>
            Score: {score ? score : 0} / {question.questionKey - 1}
          </h4>
          <hr />
          <h2>{question.questionText}</h2>
          <div>
            {question.alternatives.map((alternative) => (
              <button
                key={alternative.alternativeKey}
                onClick={() => handleAlternativeClick(alternative)}
                //MOVE TO CSS?
                style={{
                  backgroundColor:
                    selectedAlternative === alternative.alternativeKey
                      ? correctAlternative === alternative.alternativeKey
                        ? "green"
                        : "red"
                      : correctAlternative === alternative.alternativeKey
                        ? "green"
                        : "",
                }}
                disabled={correctAlternative != null}
              >
                {alternative.alternativeText}
              </button>
            ))}
          </div>
          <hr />
          {explanation && (
            <div>
              <h3>{explanation}</h3>
              <hr />
            </div>
          )}
          {selectedAlternative !== null && correctAlternative != null && (
            <div>
              <button onClick={putNextQuestion}>Next Question</button>
              <button onClick={endSession}>Quit</button>
            </div>
          )}
        </div>
      ) : (
        <div>
          <h2>Loading question...</h2>
        </div>
      )}
    </div>
  );
}
