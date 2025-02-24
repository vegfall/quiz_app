import React, { useEffect, useState } from 'react';
import { quizApi } from '../config/axiosApi';

export default function HomePage() {
  const [sessionKeys, setSessionKeys] = useState<string[]>([]);

  useEffect(() => {
    quizApi
      .get('/session/get-sessions') //FIX LATER
      .then((response) => {
        setSessionKeys(response.data);
      })
      .catch((error) => {
        console.error('Error fetching sessions:', error);
        alert('Failed to load sessions. Please try again later.');
      });
  }, []);

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
      <input type="text" />
      <button>Start New Quiz</button>
      <hr />
      <h3>Session Key:</h3>
      <input type="text" />
      <button>Join Existing Quiz</button>
      <hr />
      <h2>Available Sessions</h2>
      <ul>
        {sessionKeys.map((session) => (
          <li key={session}>{session}</li>
        ))}
      </ul>
    </div>
  );
}
