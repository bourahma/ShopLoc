import { Navigate } from "react-router-dom";
import React from "react";

export const PrivateRoute = ({ children }) => {
  const loggedUser = window.localStorage.getItem("userToken");

  if (!loggedUser) {
    return <Navigate to={"/"} />;
  }

  return children;
};
