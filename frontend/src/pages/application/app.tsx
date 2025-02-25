import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import HomePage from "../subpages/homePage";
import "../application/app.css";
import CreatePage from "../subpages/createPage";
import LobbyPage from "../subpages/lobbyPage";
import PlayPage from "../subpages/playPage";

export default function App() {
  return (
    <div className="main_container">
      <div className="main_block">
        <Router>
          <Routes>
            <Route path={"/"} element={<HomePage />} />
            <Route path={"/create"} element={<CreatePage />} />
            <Route path={"/lobby"} element={<LobbyPage />} />
            <Route path={"/play"} element={<PlayPage />} />
          </Routes>
        </Router>
      </div>
    </div>
  );
}
