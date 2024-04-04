import { Navigate } from "react-router-dom";
import React from "react";
import { jwtDecode } from "jwt-decode";

export const PrivateRoute = ({ children }) => {
  const loggedUserToken = JSON.parse(window.localStorage.getItem("userToken"));

  if (!loggedUserToken) {
    return <Navigate to={"/"} />;
  }

  if (loggedUserToken) {
    let decoded = jwtDecode(loggedUserToken);
    let expirationDate = decoded.exp;

    if (expirationDate < Date.now() / 1000) {
      window.localStorage.clear();
      return <Navigate to={"/"} />;
    }
  }

  return children;
};
