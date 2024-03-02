import axios from "axios";

const SERVER_URL = process.env.REACT_APP_SERVER_URL;

const baseUrl = "merchant/register";

const registerMerchant = async (credentials) => {
  const response = await axios.post(`${SERVER_URL}/${baseUrl}`, credentials);
  return response.data;
};

const registerMerchantService = { registerMerchant };

export default registerMerchantService;
