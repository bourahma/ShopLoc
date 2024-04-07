import { render } from "@testing-library/react";
import RatingStars from "../components/RatingStars";
import "@testing-library/jest-dom";

jest.mock("react-icons/fa", () => ({
  FaStar: () => <div data-testid="star" />,
  FaStarHalf: () => <div data-testid="star-half" />,
}));

describe("RatingStars", () => {
  it("renders correctly", () => {
    const { getByTestId, getAllByTestId } = render(<RatingStars />);
    expect(getAllByTestId("star").length).toBe(4);
    expect(getByTestId("star-half")).toBeInTheDocument();
  });
});
