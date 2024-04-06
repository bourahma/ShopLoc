import { render, screen } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import ProductDetails from "../components/ProductDetails";
import useProducts from "../hooks/useProducts";
import "@testing-library/jest-dom";

jest.mock("../hooks/useProducts");

describe("ProductDetails", () => {
  it("displays loading state", () => {
    useProducts.useProductDetails.mockReturnValue({
      isLoading: true,
    });

    render(
      <Router>
        <ProductDetails />
      </Router>
    );

    expect(screen.getByText("Loading...")).toBeInTheDocument();
  });
});
