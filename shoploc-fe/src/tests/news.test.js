import axios from "axios";
import MockAdapter from "axios-mock-adapter";
import newsService from "../services/news";

describe("newsService", () => {
  it("returns data when getNews is called", async () => {
    const mock = new MockAdapter(axios);
    const data = { response: true };
    mock
      .onGet(
        `https://newsapi.org/v2/everything?domains=actu.fr&apiKey=${process.env.REACT_APP_NEWS_API_KEY}`
      )
      .reply(200, data);

    const response = await newsService.getNews();

    expect(response).toEqual(data);
  });
});
