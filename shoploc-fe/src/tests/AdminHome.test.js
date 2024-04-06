import { render, screen } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import AdminHome from "../pages/AdminHome";
import "@testing-library/jest-dom";

describe("AdminHome", () => {
  it("renders sidebar items", () => {
    render(
      <Router>
        <AdminHome />
      </Router>
    );

    expect(screen.getByText("Créer un commerce")).toBeInTheDocument();
    expect(screen.getByText("Créer un commerçant")).toBeInTheDocument();
    expect(screen.getByText("Lancer une promotion")).toBeInTheDocument();
  });
});
