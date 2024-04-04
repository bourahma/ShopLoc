import React from "react";
import { Navigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

export const PublicRoute = ({ children }) => {
  const loggedUserToken = JSON.parse(window.localStorage.getItem("userToken"));

  if (loggedUserToken) {
    let decoded = jwtDecode(loggedUserToken);
    if (decoded.exp > Date.now() / 1000) {
      if (
        JSON.parse(window.localStorage.getItem("userRole")) === "ADMINISTRATOR"
      ) {
        return <Navigate to={"/admin/home"} />;
      }
      if (JSON.parse(window.localStorage.getItem("userRole")) === "MERCHANT") {
        return <Navigate to={"/merchant/home"} />;
      }
      if (JSON.parse(window.localStorage.getItem("userRole")) === "CUSTOMER") {
        return <Navigate to={"/home"} />;
      }
    }
  }
  return children;
};
