import axios from "axios";
import MockAdapter from "axios-mock-adapter";
import { fetchCustomerProfile } from "../services/customer";

describe("fetchCustomerProfile", () => {
  it("fetches successfully data from an API", async () => {
    const mock = new MockAdapter(axios);
    const data = { profile: "John Doe" };
    const token = "testToken";

    mock
      .onGet(`${process.env.REACT_APP_SERVER_URL}/customer/profile`)
      .reply(200, data);

    const fetchedData = await fetchCustomerProfile(token);
    expect(fetchedData).toEqual(data);
  });
});
