import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import HomePage from "../home/homePage";
import "../application/app.css";

export default function App() {
  return (
    <div className="main_container">
      <div className="main_block">
        <Router>
          <Routes>
            <Route path={"/"} element={<HomePage />} />
          </Routes>
        </Router>
      </div>
    </div>
  );
}
