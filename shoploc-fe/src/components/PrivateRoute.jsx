import { Navigate } from "react-router-dom";
import React from "react";

export const PrivateRoute = ({ children }) => {
  const loggedUser = window.localStorage.getItem("userToken");

  if (!loggedUser) {
    return <Navigate to={"/"} />;
  }

  return children;
};

export const AdminPrivateRoute = ({ children }) => {
  const loggedUser = window.localStorage.getItem("userToken");
  const userRole = window.localStorage.getItem("userRole");

  if (!loggedUser || userRole !== "ADMINISTRATOR") {
    return <Navigate to={"/"} />;
  }

  return children;
};

export const MerchantPrivateRoute = ({ children }) => {
  const loggedUser = window.localStorage.getItem("userToken");
  const userRole = window.localStorage.getItem("userRole");

  if (!loggedUser || userRole !== "MERCHANT") {
    return <Navigate to={"/"} />;
  }

  return children;
};
