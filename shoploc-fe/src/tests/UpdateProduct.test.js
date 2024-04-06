import { render, screen } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import UpdateProduct from "../components/UpdateProduct";
import useProducts from "../hooks/useProducts";
import usePromotions from "../hooks/usePromotions";
import "@testing-library/jest-dom";

// Mock the hooks
jest.mock("../hooks/useProducts", () => ({
  useProductDetails: jest.fn(),
  useProductsCategories: jest.fn(),
}));

jest.mock("../hooks/usePromotions", () => ({
  useCommercePromotions: jest.fn(),
}));

describe("UpdateProduct", () => {
  beforeEach(() => {
    // Provide a mock implementation for the hooks
    useProducts.useProductDetails.mockImplementation(() => ({
      data: {},
      isLoading: false,
      isError: false,
      Error: null,
    }));

    useProducts.useProductsCategories.mockImplementation(() => ({
      data: [],
      isLoading: false,
      isError: false,
      Error: null,
    }));

    usePromotions.useCommercePromotions.mockImplementation(() => ({
      data: [],
      isLoading: false,
      isError: false,
      Error: null,
    }));
  });

  it("renders without crashing", () => {
    render(
      <Router>
        <UpdateProduct />
      </Router>
    );
    expect(screen.getByPlaceholderText("Nom du produit")).toBeInTheDocument();
  });
});
