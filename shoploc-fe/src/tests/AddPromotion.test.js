import { render, screen } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import { createMemoryHistory } from "history";
import AddPromotion from "../components/AddPromotion";
import usePromotions from "../hooks/usePromotions";
import "@testing-library/jest-dom";

// Mock the hooks
jest.mock("../hooks/usePromotions", () => ({
  useCreateDiscount: jest.fn(),
  useCreateOffer: jest.fn(),
}));

describe("AddPromotion", () => {
  beforeEach(() => {
    // Provide a mock implementation for the hooks
    usePromotions.useCreateDiscount.mockImplementation(() => ({
      mutate: jest.fn(),
    }));

    usePromotions.useCreateOffer.mockImplementation(() => ({
      mutate: jest.fn(),
    }));

    // Mock localStorage
    Storage.prototype.getItem = jest.fn(() => JSON.stringify("mockedToken"));
  });

  it("renders without crashing", () => {
    const history = createMemoryHistory();
    render(
      <Router history={history}>
        <AddPromotion />
      </Router>
    );
    expect(
      screen.getByPlaceholderText("Entrez le libellé de la promotion")
    ).toBeInTheDocument();
    expect(
      screen.getByPlaceholderText("Entrez la description de la promotion")
    ).toBeInTheDocument();
    expect(screen.getByText("Ajouter la promotion")).toBeInTheDocument();
  });
});
