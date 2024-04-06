import { render, screen } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import LaunchPromo from "../components/LaunchPromo";
import usePromotions from "../hooks/usePromotions";
import "@testing-library/jest-dom";

// Mock the usePromotions hook
jest.mock("../hooks/usePromotions", () => ({
  useAllPromotions: jest.fn(),
}));

describe("LaunchPromo", () => {
  beforeEach(() => {
    // Provide a mock implementation for the hook
    usePromotions.useAllPromotions.mockImplementation(() => ({
      data: [],
      isLoading: false,
      isError: false,
      Error: null,
    }));
  });

  it("renders without crashing", () => {
    render(
      <Router>
        <LaunchPromo />
      </Router>
    );
    expect(
      screen.getByPlaceholderText("Rechercher un produit ...")
    ).toBeInTheDocument();
  });
});
