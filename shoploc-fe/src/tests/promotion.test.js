import axios from "axios";
import MockAdapter from "axios-mock-adapter";
import promotionService from "../services/promotion";

const mock = new MockAdapter(axios);
const token = "testToken";
const commerceId = "testCommerceId";
const promotionId = "testPromotionId";
const discount = { percent: 10 };
const offer = { product: "testProduct", price: 100 };

describe("promotionService", () => {
  afterEach(() => {
    mock.reset();
  });

  test("addDiscount", async () => {
    const data = { id: "1", percent: 10 };
    mock
      .onPost(
        `${process.env.REACT_APP_SERVER_URL}/promotion/discount/${commerceId}`
      )
      .reply(200, data);

    const response = await promotionService.addDiscount(
      token,
      discount,
      commerceId
    );
    expect(response).toEqual(data);
  });

  test("addOffer", async () => {
    const data = { id: "1", product: "testProduct", price: 100 };
    mock
      .onPost(
        `${process.env.REACT_APP_SERVER_URL}/promotion/offer/${commerceId}`
      )
      .reply(200, data);

    const response = await promotionService.addOffer(token, offer, commerceId);
    expect(response).toEqual(data);
  });

  test("getCommercePromotions", async () => {
    const data = [{ id: "1", percent: 10 }];
    mock
      .onGet(
        `${process.env.REACT_APP_SERVER_URL}/promotion/commerce/${commerceId}`
      )
      .reply(200, data);

    const response = await promotionService.getCommercePromotions(
      token,
      commerceId
    );
    expect(response).toEqual(data);
  });

  test("getAllPromotions", async () => {
    const data = [{ id: "1", percent: 10 }];
    mock
      .onGet(`${process.env.REACT_APP_SERVER_URL}/promotion/`)
      .reply(200, data);

    const response = await promotionService.getAllPromotions(token);
    expect(response).toEqual(data);
  });

  test("getPromotionDetails", async () => {
    const data = { id: "1", percent: 10 };
    mock
      .onGet(`${process.env.REACT_APP_SERVER_URL}/promotion/${promotionId}`)
      .reply(200, data);

    const response = await promotionService.getPromotionDetails(
      token,
      promotionId
    );
    expect(response).toEqual(data);
  });

  test("launchPromotion", async () => {
    const data = { id: "1", percent: 10 };
    mock
      .onGet(
        `${process.env.REACT_APP_SERVER_URL}/promotion/${promotionId}/launch`
      )
      .reply(200, data);

    const response = await promotionService.launchPromotion(token, promotionId);
    expect(response).toEqual(data);
  });
});
