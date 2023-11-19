import React from "react";
import { BrowserRouter, Outlet, Route, Routes } from "react-router-dom";
import HomeComponent from "./components/HomeComponent";
import AuthComponent from "./components/AuthComponent";
import Header from "./components/Header";
import Products from "./components/Products";
import LegacyInfosComponent from "./components/Footer";

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
                  <Route path="login" element={<AuthComponent />} />
                  <Route path="/commercant/:id" element={<Products />} />
              </Route>
          </Routes>
      </BrowserRouter>
  );
}

export default App;
