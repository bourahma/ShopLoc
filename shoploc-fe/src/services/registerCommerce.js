import axios from "axios";

const SERVER_URL = process.env.REACT_APP_SERVER_URL;

const baseUrl = "commerce/register";

const registerCommerce = async (credentials) => {
  const response = await axios.post(`${SERVER_URL}/${baseUrl}`, credentials);
  return response.data;
};

const registerCommerceService = { registerCommerce };

export default registerCommerceService;
