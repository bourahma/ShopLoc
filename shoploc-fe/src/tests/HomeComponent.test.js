import "@testing-library/jest-dom";
import React from "react";
import { render } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { createMemoryHistory } from "history";
import HomeComponent from "../components/HomeComponent";

jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"),
  useNavigate: () => jest.fn(),
}));

describe("HomeComponent", () => {
  test("renders HomeComponent", () => {
    const history = createMemoryHistory();
    const { getByText, getByPlaceholderText } = render(
      <BrowserRouter>
        <HomeComponent />
      </BrowserRouter>
    );

    expect(getByText("Auchan")).toBeInTheDocument();
  });
  /*
      test("checks card click", () => {
        const history = createMemoryHistory();
        const { getByText } = render(
          <BrowserRouter history={history}>
            <HomeComponent />
          </BrowserRouter>
        );
    
        fireEvent.click(getByText(""));
    
        expect(history.location.pathname).toBe("/commercant/1");
      });
    
     */
});
