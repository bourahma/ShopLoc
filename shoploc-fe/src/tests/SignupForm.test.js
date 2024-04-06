import "@testing-library/jest-dom";
import React from "react";
import { fireEvent, getByText, render, waitFor } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import SignupForm from "../components/SignupForm";
import signupService from "../services/signup";

jest.mock("../services/signup");

describe("SignupForm", () => {
  test("renders SignupForm component", () => {
    const { getByText, getByPlaceholderText } = render(
      <BrowserRouter>
        <SignupForm />
      </BrowserRouter>
    );
    expect(getByText("Nom d'utilisateur")).toBeInTheDocument();
    expect(getByText("Nom de famille")).toBeInTheDocument();
    expect(getByText("Prénom")).toBeInTheDocument();
    expect(getByText("Mot de passe")).toBeInTheDocument();
    expect(getByText("Confirmer le mot de passe")).toBeInTheDocument();
    expect(getByText("Adresse e-mail")).toBeInTheDocument();
    expect(getByText("Numéro de téléphone")).toBeInTheDocument();
    expect(getByText("S'inscrire")).toBeInTheDocument();
  });

  test("checks form submission", async () => {
    signupService.signup.mockResolvedValue({});

    const { getByText, getByPlaceholderText } = render(
      <BrowserRouter>
        <SignupForm />
      </BrowserRouter>
    );

    fireEvent.change(getByPlaceholderText("Votre nom d'utilisateur"), {
      target: { value: "testuser" },
    });
    fireEvent.change(getByPlaceholderText("Votre nom de famille"), {
      target: { value: "testlastname" },
    });
    fireEvent.change(getByPlaceholderText("Votre prénom"), {
      target: { value: "testfirstname" },
    });
    fireEvent.change(getByPlaceholderText("Votre mot de passe"), {
      target: { value: "testpassword" },
    });
    fireEvent.change(getByPlaceholderText("Confirmer votre mot de passe"), {
      target: { value: "testpassword" },
    });
    fireEvent.change(getByPlaceholderText("addresse@gmail.com"), {
      target: { value: "test@gmail.com" },
    });
    fireEvent.change(getByPlaceholderText("Votre numéro de téléphone"), {
      target: { value: "1234567890" },
    });
    fireEvent.click(getByText("S'inscrire"));

    await waitFor(() => expect(signupService.signup).toHaveBeenCalledTimes(1));
    expect(signupService.signup).toHaveBeenCalledWith({
      username: "testuser",
      lastname: "testlastname",
      firstname: "testfirstname",
      password: "testpassword",
      confirmedPassword: "testpassword",
      email: "test@gmail.com",
      phoneNumber: "1234567890",
    });
  });
});
