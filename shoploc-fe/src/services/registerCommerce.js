import axios from "axios";

const SERVER_URL = process.env.REACT_APP_SERVER_URL;

const baseUrl = "commerce/";

const registerCommerce = async (credentials, token) => {
  const response = await axios.post(`${SERVER_URL}/${baseUrl}`, credentials, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};

const registerCommerceService = { registerCommerce };

export default registerCommerceService;
