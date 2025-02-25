import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import HomePage from "../subpages/homePage";
import "../application/app.css";
import CreatePage from "../subpages/createPage";

export default function App() {
  return (
    <div className="main_container">
      <div className="main_block">
        <Router>
          <Routes>
            <Route path={"/"} element={<HomePage />} />
            <Route path={"/create"} element={<CreatePage />} />
          </Routes>
        </Router>
      </div>
    </div>
  );
}
