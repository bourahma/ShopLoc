import axios from "axios";

const SERVER_URL = process.env.REACT_APP_SERVER_URL;

const baseUrl = "authentication/merchant/register";

const registerMerchant = async (credentials, token) => {
  const response = await axios.post(`${SERVER_URL}/${baseUrl}`, credentials, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};

const registerMerchantService = { registerMerchant };

export default registerMerchantService;
