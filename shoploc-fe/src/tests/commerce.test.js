import axios from "axios";
import MockAdapter from "axios-mock-adapter";
import commerceService from "../services/commerce";

const mock = new MockAdapter(axios);
const token = "testToken";
const commerceId = 1;
const merchantId = 1;
const credentials = { username: "test", password: "test" };

beforeEach(() => {
  mock.reset();
});

describe("commerceService", () => {
  it("registers a commerce", async () => {
    const data = { id: 1, name: "testCommerce" };
    mock
      .onPost(`${process.env.REACT_APP_SERVER_URL}/commerce/`)
      .reply(200, data);

    const result = await commerceService.registerCommerce(credentials, token);
    expect(result).toEqual(data);
  });

  it("fetches products", async () => {
    const data = [{ id: 1, name: "testProduct" }];
    mock
      .onGet(
        `${process.env.REACT_APP_SERVER_URL}/commerce/${commerceId}/products`
      )
      .reply(200, data);

    const result = await commerceService.fetchProducts(token, commerceId);
    expect(result).toEqual(data);
  });

  it("fetches product categories", async () => {
    const data = [{ id: 1, name: "testCategory" }];
    mock
      .onGet(
        `${process.env.REACT_APP_SERVER_URL}/product/categories/${commerceId}`
      )
      .reply(200, data);

    const result = await commerceService.fetchProductsCategories(
      token,
      commerceId
    );
    expect(result).toEqual(data);
  });

  it("fetches merchant products", async () => {
    const data = [{ id: 1, name: "testProduct" }];
    mock
      .onGet(
        `${process.env.REACT_APP_SERVER_URL}/commerce/merchant/${merchantId}/products`
      )
      .reply(200, data);

    const result = await commerceService.fetchMerchantProducts(
      token,
      merchantId
    );
    expect(result).toEqual(data);
  });

  it("fetches commerce", async () => {
    const data = { id: 1, name: "testCommerce" };
    mock
      .onGet(`${process.env.REACT_APP_SERVER_URL}/commerce/${commerceId}`)
      .reply(200, data);

    const result = await commerceService.fetchCommerce(token, commerceId);
    expect(result).toEqual(data);
  });

  it("gets commerce id", async () => {
    const data = { id: 1 };
    mock
      .onGet(
        `${process.env.REACT_APP_SERVER_URL}/commerce/merchant/${merchantId}`
      )
      .reply(200, data);

    const result = await commerceService.getCommerceId(token, merchantId);
    expect(result).toEqual(data);
  });

  it("fetches commerces", async () => {
    const data = [{ id: 1, name: "testCommerce" }];
    mock
      .onGet(`${process.env.REACT_APP_SERVER_URL}/commerce/`)
      .reply(200, data);

    const result = await commerceService.fetchCommerces(token);
    expect(result).toEqual(data);
  });
});
