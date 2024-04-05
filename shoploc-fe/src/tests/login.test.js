import axios from "axios";
import MockAdapter from "axios-mock-adapter";
import loginService from "../services/login";

describe("loginService", () => {
  it("returns data when login is called", async () => {
    const mock = new MockAdapter(axios);
    const data = { response: true };
    mock
      .onPost(
        `${process.env.REACT_APP_SERVER_URL}/authentication/customer/login`
      )
      .reply(200, data);

    const credentials = { username: "testuser", password: "testpassword" };
    const response = await loginService.customerLogin(credentials);

    expect(response).toEqual(data);
  });
});
