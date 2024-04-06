import axios from "axios";
import MockAdapter from "axios-mock-adapter";
import registerMerchantService from "../services/registerMerchant";

const mock = new MockAdapter(axios);
const token = "testToken";
const credentials = { email: "test@test.com", password: "testPassword" };

describe("registerMerchantService", () => {
  afterEach(() => {
    mock.reset();
  });

  test("registerMerchant", async () => {
    const data = { id: "1", email: "test@test.com" };
    mock
      .onPost(
        `${process.env.REACT_APP_SERVER_URL}/authentication/merchant/register`
      )
      .reply(200, data);

    const response = await registerMerchantService.registerMerchant(
      credentials,
      token
    );
    expect(response).toEqual(data);
  });
});
