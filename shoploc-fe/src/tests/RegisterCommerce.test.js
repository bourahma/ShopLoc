import { render, fireEvent, waitFor } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import CommerceRegistrationForm from "../components/RegisterCommerce";
import commerceService from "../services/commerce";
import "@testing-library/jest-dom";

jest.mock("../services/commerce");

describe("CommerceRegistrationForm", () => {
  it("renders correctly", () => {
    const { getByLabelText } = render(
      <Router>
        <CommerceRegistrationForm />
      </Router>
    );

    expect(getByLabelText(/Nom du commerce/i)).toBeInTheDocument();
    expect(getByLabelText(/Heure d'ouverture/i)).toBeInTheDocument();
    expect(getByLabelText(/Heure de fermeture/i)).toBeInTheDocument();
  });
});
