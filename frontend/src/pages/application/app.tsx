import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import HomePage from "../home/homePage";

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path={"/"} element={<HomePage />} />
      </Routes>
    </Router>
  );
}
