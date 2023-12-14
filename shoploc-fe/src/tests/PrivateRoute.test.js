import "@testing-library/jest-dom";
import React from "react";
import { render, screen } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import { PrivateRoute } from "../components/PrivateRoute";

describe("PrivateRoute", () => {
  test("renders children when user is logged in", () => {
    Storage.prototype.getItem = jest.fn(() => "userToken");
    render(
      <Router>
        <PrivateRoute>Test</PrivateRoute>
      </Router>
    );
    expect(screen.getByText("Test")).toBeInTheDocument();
  });

  test("redirects to login when user is not logged in", () => {
    Storage.prototype.getItem = jest.fn(() => null);
    const { container } = render(
      <Router>
        <PrivateRoute>Test</PrivateRoute>
      </Router>
    );
    expect(container.innerHTML).toBe("");
  });
});
