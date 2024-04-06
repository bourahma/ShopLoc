import axios from "axios";

const SERVER_URL = process.env.REACT_APP_SERVER_URL;

const baseUrl = "authentication/customer/register";

const signup = async (credentials) => {
  const response = await axios.post(`${SERVER_URL}/${baseUrl}`, credentials);
  return response.data;
};

const signupService = { signup };

export default signupService;
