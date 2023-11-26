import React from "react";
import { BrowserRouter, Outlet, Route, Routes } from "react-router-dom";
import HomeComponent from "./components/HomeComponent";
import Header from "./components/Header";
import LegacyInfosComponent from "./components/Footer";
import SignupForm from "./components/SignupForm";
import LoginForm from "./components/LoginForm";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={
            <div className="flex flex-col min-h-screen">
              <div className="flex-1">
                <Header />
                <Outlet />
              </div>
              <LegacyInfosComponent className="bg-gray-300 py-4 text-center" />
            </div>
          }
        >
          <Route index element={<HomeComponent />} />
          <Route path="login" element={<LoginForm />} />
          <Route path="signup" element={<SignupForm />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
