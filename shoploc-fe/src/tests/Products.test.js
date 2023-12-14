import "@testing-library/jest-dom";
import React from "react";
import { fireEvent, render } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { createMemoryHistory } from "history";
import Product from "../components/Products";

jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"),
  useParams: () => ({ commercantId: "1" }),
  useNavigate: () => jest.fn(),
}));

describe("Products", () => {
  test("checks back button click", () => {
    const history = createMemoryHistory();
    const { getByText } = render(
      <BrowserRouter history={history}>
        <Product />
      </BrowserRouter>
    );

    fireEvent.click(getByText("Back"));
    expect(history.location.pathname).toBe("/");
  });
});
