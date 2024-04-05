import "@testing-library/jest-dom";
import React from "react";
import { render, screen } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import { PrivateRoute } from "../components/PrivateRoute";

describe("PrivateRoute", () => {
  test("renders children when user is logged in", () => {
    const mockToken =
      "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    Storage.prototype.getItem = jest.fn(() => JSON.stringify(mockToken));
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
