import React from "react";
import { render, fireEvent, waitFor } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import AddProduct from "../components/AddProduct";
import productServices from "../services/product";
import useProducts from "../hooks/useProducts";

jest.mock("../services/product");
jest.mock("../hooks/useProducts");

describe("AddProduct", () => {
  beforeEach(() => {
    // Mock the hooks and services used by the component
    useProducts.useProductsCategories.mockReturnValue({
      data: [
        { productCategoryId: "1", label: "Category 1" },
        { productCategoryId: "2", label: "Category 2" },
      ],
    });
    productServices.addProduct.mockResolvedValue({});
  });

  it("renders without crashing", () => {
    render(
      <Router>
        <AddProduct />
      </Router>
    );
  });

  it("submits the form", async () => {
    const { getByLabelText, getByText } = render(
      <Router>
        <AddProduct />
      </Router>
    );

    // Simulate user input
    fireEvent.change(getByLabelText(/Nom du produit/i), {
      target: { value: "Test Product" },
    });
    fireEvent.change(getByLabelText(/Description/i), {
      target: { value: "Test Description" },
    });
    fireEvent.change(getByLabelText(/Prix/i), {
      target: { value: "10" },
    });
    fireEvent.change(getByLabelText(/Quantité/i), {
      target: { value: "5" },
    });
    fireEvent.change(getByLabelText(/Points de récompense/i), {
      target: { value: "100" },
    });
    fireEvent.change(getByLabelText(/Cadeau/i), {
      target: { value: "true" },
    });
    fireEvent.change(getByLabelText(/Catégorie/i), {
      target: { value: "1" },
    });

    // Simulate form submission
    fireEvent.click(getByText(/Ajouter/i));

    // Wait for the promise to resolve
    await waitFor(() => expect(productServices.addProduct).toHaveBeenCalled());
  });
});
