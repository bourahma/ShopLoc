import { render, screen } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import CommerceCard from "../components/CommerceCard";
import "@testing-library/jest-dom";

describe("CommerceCard", () => {
  const commercant = {
    commerceId: "1",
    imageUrl: "test.jpg",
    commerceName: "Test Commerce",
    openingHour: "08:00",
    closingHour: "18:00",
  };

  test("renders commerce card with correct data", () => {
    render(
      <BrowserRouter>
        <CommerceCard commercant={commercant} />
      </BrowserRouter>
    );

    expect(screen.getByText("Test Commerce")).toBeInTheDocument();
    expect(screen.getByText("Ouverture: 08:00")).toBeInTheDocument();
    expect(screen.getByText("Fermeture: 18:00")).toBeInTheDocument();
    expect(screen.getByAltText("test.jpg")).toBeInTheDocument();
  });

  test("renders correct link to commerce", () => {
    render(
      <BrowserRouter>
        <CommerceCard commercant={commercant} />
      </BrowserRouter>
    );

    expect(screen.getByRole("link")).toHaveAttribute("href", "/commercant/1");
  });
});
