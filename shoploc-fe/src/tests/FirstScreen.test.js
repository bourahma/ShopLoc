import "@testing-library/jest-dom";
import React from "react";
import { render, screen } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import FirstScreen from "../components/FirstScreen";

jest.mock("../components/Map", () => () => <div>Map</div>);
jest.mock("../components/NewsSlider", () => () => <div>NewsSlider</div>);

describe("FirstScreen", () => {
  test("renders FirstScreen component", () => {
    render(
      <BrowserRouter>
        <FirstScreen />
      </BrowserRouter>
    );

    expect(screen.getByText("Map")).toBeInTheDocument();
    expect(screen.getByText("NewsSlider")).toBeInTheDocument();
  });
});
