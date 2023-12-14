import "@testing-library/jest-dom";
import React from "react";
import { render, screen } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import { PublicRoute } from "../components/PublicRoute";

describe("PublicRoute", () => {
  test("redirects to home when user is logged in", () => {
    Storage.prototype.getItem = jest.fn(() => "userToken");
    render(
      <Router>
        <PublicRoute>Test</PublicRoute>
      </Router>
    );
    expect(screen.queryByText("Test")).not.toBeInTheDocument();
  });

  test("renders children when user is not logged in", () => {
    Storage.prototype.getItem = jest.fn(() => null);
    render(
      <Router>
        <PublicRoute>Test</PublicRoute>
      </Router>
    );
    expect(screen.getByText("Test")).toBeInTheDocument();
  });
});
