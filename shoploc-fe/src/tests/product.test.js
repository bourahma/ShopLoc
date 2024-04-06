import axios from "axios";
import MockAdapter from "axios-mock-adapter";
import productServices from "../services/product";

const mock = new MockAdapter(axios);
const token = "testToken";
const commerceId = 1;
const productId = 1;
const category = { name: "testCategory" };
const product = { name: "testProduct" };
const SERVER_URL = process.env.REACT_APP_SERVER_URL;
const baseUrl = "product";

beforeEach(() => {
  mock.reset();
});

describe("productServices", () => {
  it("fetches product categories", async () => {
    const data = [{ id: 1, name: "testCategory" }];
    mock
      .onGet(`${SERVER_URL}/${baseUrl}/categories/${commerceId}`)
      .reply(200, data);

    const result = await productServices.getProductsCategories(
      token,
      commerceId
    );
    expect(result).toEqual(data);
  });

  it("adds a product", async () => {
    const data = { id: 1, name: "testProduct" };
    mock
      .onPost(`${SERVER_URL}/${baseUrl}/commerce/${commerceId}`)
      .reply(200, data);

    const result = await productServices.addProduct(product, token, commerceId);
    expect(result).toEqual(data);
  });

  it("adds a product category", async () => {
    const data = { id: 1, name: "testCategory" };
    mock
      .onPost(`${SERVER_URL}/${baseUrl}/category/${commerceId}`)
      .reply(200, data);

    const result = await productServices.addProductCategory(
      token,
      commerceId,
      category
    );
    expect(result).toEqual(data);
  });

  it("fetches product details", async () => {
    const data = { id: 1, name: "testProduct" };
    mock.onGet(`${SERVER_URL}/${baseUrl}/detail/${productId}`).reply(200, data);

    const result = await productServices.getProductDetails(token, productId);
    expect(result).toEqual(data);
  });

  it("updates a product", async () => {
    const data = { id: 1, name: "updatedProduct" };
    mock.onPut(`${SERVER_URL}/${baseUrl}/`).reply(200, data);

    const result = await productServices.updateProduct(product, token);
    expect(result).toEqual(data);
  });
});
