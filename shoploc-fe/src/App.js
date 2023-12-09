import React from "react";
import { BrowserRouter, Outlet, Route, Routes } from "react-router-dom";
import HomeComponent from "./components/HomeComponent";
import Header from "./components/Header";
import Products from "./components/Products";
import LegacyInfosComponent from "./components/Footer";
import SignupForm from "./components/SignupForm";
import LoginForm from "./components/LoginForm";
import { AuthenticatedOnly } from "./components/AuthenticatedOnly";
import { AnonymousOnly } from "./components/AnonymousOnly";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={
            <div className="flex flex-col min-h-screen">
              <div className="flex-1">
                <div className="px-20 py-8">
                  <Outlet />
                </div>
              </div>
              <LegacyInfosComponent className="bg-gray-300 py-4 text-center" />
            </div>
          }
        >
          <Route
            index
            element={
              <AuthenticatedOnly>
                <Header />
                <HomeComponent />
              </AuthenticatedOnly>
            }
          />
          <Route
            path="/signup"
            element={
              <AnonymousOnly>
                <Header />
                <SignupForm />
              </AnonymousOnly>
            }
          />
          <Route
            path="/login"
            element={
              <AnonymousOnly>
                <Header />
                <LoginForm />
              </AnonymousOnly>
            }
          />
          <Route
            path="/commercant/:commercantId"
            element={
              <AuthenticatedOnly>
                <Header />
                <Products />
              </AuthenticatedOnly>
            }
          />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
