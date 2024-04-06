import axios from "axios";
import MockAdapter from "axios-mock-adapter";
import { fetchLoyaltyCard } from "../services/carteDeFidelite";

describe("fetchLoyaltyCard", () => {
  it("fetches successfully data from an API", async () => {
    const mock = new MockAdapter(axios);
    const data = { card: "1234567890" };
    const token = "testToken";

    mock
      .onGet(`${process.env.REACT_APP_SERVER_URL}/fidelity-card/`)
      .reply(200, data);

    const fetchedData = await fetchLoyaltyCard(token);
    expect(fetchedData).toEqual(data);
  });
});
