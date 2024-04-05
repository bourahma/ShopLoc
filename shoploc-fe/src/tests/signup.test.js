import axios from "axios";
import MockAdapter from "axios-mock-adapter";
import signupService from "../services/signup";

describe("signupService", () => {
  it("returns data when signup is called", async () => {
    const mock = new MockAdapter(axios);
    const data = { response: true };
    mock
      .onPost(
        `${process.env.REACT_APP_SERVER_URL}/authentication/customer/register`
      )
      .reply(200, data);

    const credentials = { username: "testuser", password: "testpassword" };
    const response = await signupService.signup(credentials);

    expect(response).toEqual(data);
  });
});
