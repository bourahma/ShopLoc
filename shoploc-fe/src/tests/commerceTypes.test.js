import axios from "axios";
import MockAdapter from "axios-mock-adapter";
import fetchCommerceTypes from "../services/commerceTypesService";

describe("fetchCommerceTypes", () => {
  const mock = new MockAdapter(axios);
  const token = "testToken";
  const API_URL = process.env.REACT_APP_SERVER_URL;
  const baseUrl = "commerce/types";

  beforeEach(() => {
    jest.spyOn(console, "error").mockImplementation(() => {});
  });

  afterEach(() => {
    console.error.mockRestore();
  });

  afterEach(() => {
    mock.reset();
  });

  it("returns data when request is successful", async () => {
    const data = [
      {
        commerceTypeId: 0,
        label: "string",
        description: "string",
      },
    ];
    mock.onGet(`${API_URL}/${baseUrl}`).reply(200, data);

    const result = await fetchCommerceTypes(token);
    expect(result).toEqual(data);
  });

  it("throws error when request fails", async () => {
    mock.onGet(`${API_URL}/${baseUrl}`).reply(500);

    await expect(fetchCommerceTypes(token)).rejects.toThrow();
  });
});
