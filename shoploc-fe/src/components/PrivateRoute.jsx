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

    if (
      JSON.parse(window.localStorage.getItem("userRole")) === "ADMINISTRATOR" &&
      !window.location.pathname.includes("/admin/home")
    ) {
      return <Navigate to={"/admin/home"} />;
    }

    if (
      JSON.parse(window.localStorage.getItem("userRole")) === "MERCHANT" &&
      !window.location.pathname.includes("/merchant/home")
    ) {
      return <Navigate to={"/merchant/home"} />;
    }
  }

  return children;
};
