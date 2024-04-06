import axios from "axios";

const apiKey = process.env.REACT_APP_NEWS_API_KEY;

const getNews = async () => {
  const response = await axios.get(
    `https://newsapi.org/v2/everything?domains=actu.fr&apiKey=${apiKey}`
  );
  return response.data;
};

const newsService = { getNews };

export default newsService;
