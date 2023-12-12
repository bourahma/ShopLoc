import { Navigate } from "react-router-dom";
import React from "react";

export const AuthenticatedOnly = ({ children }) => {
  const loggedUser = window.localStorage.getItem("userToken");

  if (!loggedUser) {
    return <Navigate to={"/login"} />;
  }

  return children;
};
