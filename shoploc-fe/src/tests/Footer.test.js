import "@testing-library/jest-dom";
import React from "react";
import { render, screen } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import FooterComponent from "../components/Footer";

describe("FooterComponent", () => {
  test("renders FooterComponent", () => {
    render(
      <BrowserRouter>
        <FooterComponent />
      </BrowserRouter>
    );

    expect(screen.getByText("A propos")).toBeInTheDocument();
    expect(screen.getByText("FAQ")).toBeInTheDocument();
    expect(
      screen.getByText("Politique de confidentialité")
    ).toBeInTheDocument();
    expect(
      screen.getByText("Conditions générales d'utilisation")
    ).toBeInTheDocument();
  });
});
