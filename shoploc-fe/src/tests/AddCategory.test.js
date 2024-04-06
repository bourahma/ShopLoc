import { render, screen } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import AddCategory from "../components/AddCategory";
import useProducts from "../hooks/useProducts";
import "@testing-library/jest-dom";

jest.mock("../hooks/useProducts");

describe("AddCategory", () => {
  it("renders form fields", () => {
    useProducts.useCreateProductCategory.mockReturnValue({
      mutate: jest.fn(),
    });

    render(
      <Router>
        <AddCategory />
      </Router>
    );

    expect(screen.getByLabelText("Nom de la catégorie")).toBeInTheDocument();
    expect(screen.getByLabelText("Description")).toBeInTheDocument();
    expect(screen.getByText("Ajouter Catégorie")).toBeInTheDocument();
  });
});
