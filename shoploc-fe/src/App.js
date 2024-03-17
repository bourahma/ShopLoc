import React from "react";
import "matchmedia-polyfill";
import "matchmedia-polyfill/matchMedia.addListener";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/Header";
import Products from "./components/Products";
import SignupForm from "./components/SignupForm";
import LoginForm from "./components/LoginForm";
import { PrivateRoute } from "./components/PrivateRoute";
import { PublicRoute } from "./components/PublicRoute";
import HomeComponent from "./components/HomeComponent";
import MerchantHome from "./components/MerchantHome";
import Template from "./components/Template";
import FirstScreen from "./components/FirstScreen";
import Cart from "./components/Cart";
import AdminHomeComponent from "./components/AdminHomeComponent";

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
            path="/admin/home"
            element={
              <PrivateRoute>
                <Header />
                <AdminHomeComponent />
              </PrivateRoute>
            }
          />
          <Route
            path="/merchant/home"
            element={
              <PrivateRoute>
                <Header />
                <MerchantHome />
              </PrivateRoute>
            }
          />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
