import "@testing-library/jest-dom";
import React from "react";
import { render, screen } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import Template from "../components/Template";

jest.mock("../components/Footer", () => () => <div>Footer</div>);

describe("Template", () => {
  test("renders Template component", () => {
    render(
      <Router>
        <Template />
      </Router>
    );

    expect(screen.getByText("Footer")).toBeInTheDocument();
  });
});
