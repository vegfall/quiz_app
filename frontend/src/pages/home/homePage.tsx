import React from "react";

export default function HomePage() {
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
      <h2>Available Sessions</h2>
    </div>
  );
}
