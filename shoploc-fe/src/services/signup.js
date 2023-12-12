import axios from "axios";

const SERVER_URL = "http://172.28.101.175";

const baseUrl = "authentication/register";

const signup = async (credentials) => {
  const response = await axios.post(`${SERVER_URL}/${baseUrl}`, credentials);
  return response.data;
};

const signupService = { signup };

export default signupService;
