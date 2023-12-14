import "@testing-library/jest-dom";
import React from "react";
import { render } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import Header from "../components/Header";

jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"),
  useNavigate: () => jest.fn(),
}));

describe("Header", () => {
  test("renders Header component", () => {
    render(
      <BrowserRouter>
        <Header />
      </BrowserRouter>
    );
  });

  test("checks if all the elements are present", () => {
    const { getByText } = render(
      <BrowserRouter>
        <Header />
      </BrowserRouter>
    );

    expect(getByText("SHOPLOC")).toBeInTheDocument();
    expect(getByText("Trouver mon commerçant")).toBeInTheDocument();
    expect(getByText("Se connecter")).toBeInTheDocument();
    expect(getByText("S'inscrire")).toBeInTheDocument();
  });
});
