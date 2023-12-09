import React from "react";
import { Navigate } from "react-router-dom";

export const AnonymousOnly = ({ children }) => {
  const loggedUser = window.localStorage.getItem("userToken");

  if (loggedUser) {
    return <Navigate to={"/"} />;
  }

  return children;
};
