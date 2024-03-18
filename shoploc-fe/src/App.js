import React from "react";
import "matchmedia-polyfill";
import "matchmedia-polyfill/matchMedia.addListener";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/Header";
import Products from "./pages/Products";
import SignupForm from "./components/SignupForm";
import LoginForm from "./components/LoginForm";
import { PrivateRoute } from "./components/PrivateRoute";
import { PublicRoute } from "./components/PublicRoute";
import HomeComponent from "./pages/HomeComponent";
import Template from "./components/Template";
import FirstScreen from "./pages/FirstScreen";
import Cart from "./pages/Cart";
import AdminHomeComponent from "./components/AdminHomeComponent";
import Profile from "./pages/Profile";
import Checkout from "./pages/Checkout";

function App() {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Template />}>
                  <Route
                      index
                      element={
                          <PublicRoute>
                              <Header />
                              <FirstScreen />
                          </PublicRoute>
                      }
                  />
                  <Route
                      path="/home"
                      element={
                          <PrivateRoute>
                              <Header />
                              <HomeComponent />
                          </PrivateRoute>
                      }
                  />
                  <Route
                      path="/profile"
                      element={
                          <PrivateRoute>
                              <Header />
                              <Profile />
                          </PrivateRoute>
                      }
                  />
                  <Route
                      path="/signup"
                      element={
                          <PublicRoute>
                              <Header />
                              <SignupForm />
                          </PublicRoute>
                      }
                  />
                  <Route
                      path="/login"
                      element={
                          <PublicRoute>
                              <Header />
                              <LoginForm />
                          </PublicRoute>
                      }
                  />
                  <Route
                      path="/commercant/:commercantId"
                      element={
                          <PrivateRoute>
                              <Header />
                              <Products />
                          </PrivateRoute>
                      }
                  />
                  <Route
                      path="/cart"
                      element={
                          <PrivateRoute>
                              <Header />
                              <Cart />
                          </PrivateRoute>
                      }
                  />
                  <Route
                      path="/checkout"
                      element={
                          <PrivateRoute>
                              <Header />
                              <Checkout />
                          </PrivateRoute>
                      }
                  />
                  <Route
                      path="/admin/home"
                      element={
                          <PrivateRoute>
                              <Header />
                              <AdminHomeComponent />
                          </PrivateRoute>
                      }
                  />
              </Route>
          </Routes>
      </BrowserRouter>
  );
}

export default App;
