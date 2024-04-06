import { render, screen } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import { createMemoryHistory } from "history";
import PromoDetails from "../components/PromoDetails";
import usePromotions from "../hooks/usePromotions";
import useCommerces from "../hooks/useCommerces";
import "@testing-library/jest-dom";

// Mock the hooks
jest.mock("../hooks/usePromotions", () => ({
  usePromotionDetails: jest.fn(),
}));

jest.mock("../hooks/useCommerces", () => ({
  useCommerceDetails: jest.fn(),
}));

describe("PromoDetails", () => {
  beforeEach(() => {
    // Provide a mock implementation for the hooks
    usePromotions.usePromotionDetails.mockImplementation(() => ({
      data: {},
      isLoading: false,
      isError: false,
      error: null,
      isSuccess: true,
    }));

    useCommerces.useCommerceDetails.mockImplementation(() => ({
      data: {},
      isLoading: false,
      isError: false,
      error: null,
    }));

    // Mock localStorage
    Storage.prototype.getItem = jest.fn(() => JSON.stringify("mockedToken"));
  });

  it("renders without crashing", () => {
    const history = createMemoryHistory();
    render(
      <Router history={history}>
        <PromoDetails />
      </Router>
    );
    expect(screen.getByText("Retour")).toBeInTheDocument();
    expect(screen.getByText("Envoyer la promotion")).toBeInTheDocument();
  });
});
