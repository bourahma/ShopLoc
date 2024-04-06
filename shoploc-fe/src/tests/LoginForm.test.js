import "@testing-library/jest-dom";
import React from "react";
import { fireEvent, render, waitFor } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import LoginForm from "../components/LoginForm";
import loginService from "../services/login";

jest.mock("../services/login");

describe("LoginForm", () => {
  test("renders LoginForm component", () => {
    render(
      <BrowserRouter>
        <LoginForm />
      </BrowserRouter>
    );
  });

  test("checks if all the elements are present", () => {
    const { getByText, getByPlaceholderText } = render(
      <BrowserRouter>
        <LoginForm />
      </BrowserRouter>
    );

    expect(getByText("Nom d'utilisateur")).toBeInTheDocument();
    expect(getByText("Mot de passe")).toBeInTheDocument();
    expect(getByText("Connexion")).toBeInTheDocument();
    expect(getByPlaceholderText("Votre nom d'utilisateur")).toBeInTheDocument();
  });

  test("checks form submission", async () => {
    loginService.login.mockResolvedValue({});

    const { getByText, getByPlaceholderText } = render(
      <BrowserRouter>
        <LoginForm />
      </BrowserRouter>
    );

    fireEvent.change(getByPlaceholderText("Votre nom d'utilisateur"), {
      target: { value: "testuser" },
    });
    fireEvent.change(getByPlaceholderText("Votre mot de passe"), {
      target: { value: "testpassword" },
    });
    fireEvent.click(getByText("Connexion"));

    await waitFor(() => expect(loginService.login).toHaveBeenCalledTimes(1));
    expect(loginService.login).toHaveBeenCalledWith({
      username: "testuser",
      password: "testpassword",
    });
  });
});
