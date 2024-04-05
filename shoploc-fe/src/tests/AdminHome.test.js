import React from "react";
import { render, fireEvent, waitFor } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import AdminHome from "../pages/AdminHome";
import "@testing-library/jest-dom";

describe("AdminHome", () => {
  it("renders without crashing", () => {
    const { getByText } = render(
      <Router>
        <AdminHome />
      </Router>
    );
    expect(getByText("Créer un commerce")).toBeInTheDocument();
    expect(getByText("Créer un commerçant")).toBeInTheDocument();
    expect(getByText("Lancer une promotion")).toBeInTheDocument();
  });

  it("changes task state when sidebar items are clicked", async () => {
    const { getByText } = render(
      <Router>
        <AdminHome />
      </Router>
    );
    fireEvent.click(getByText("Créer un commerçant"));
  });
});
