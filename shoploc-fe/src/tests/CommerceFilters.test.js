import { render, fireEvent } from "@testing-library/react";
import CommerceFilters from "../components/CommerceFilters";
import "@testing-library/jest-dom";
import userEvent from "@testing-library/user-event";

describe("CommerceFilters", () => {
  it("renders correctly", () => {
    const { getByText } = render(
      <CommerceFilters
        onDistanceChange={() => {}}
        onOpenNowChange={() => {}}
        distance={50}
      />
    );
    expect(getByText("Ouvert maintenant")).toBeInTheDocument();
    expect(getByText("Distance: 50 km")).toBeInTheDocument();
  });

  it("calls onOpenNowChange when checkbox is clicked", () => {
    const onOpenNowChange = jest.fn();
    const { getByText } = render(
      <CommerceFilters
        onDistanceChange={() => {}}
        onOpenNowChange={onOpenNowChange}
        distance={50}
      />
    );
    fireEvent.click(getByText("Ouvert maintenant"));
    expect(onOpenNowChange).toHaveBeenCalledWith(true);
  });
});
