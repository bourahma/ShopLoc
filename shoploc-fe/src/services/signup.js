import axios from "axios";

const SERVER_URL = "http://localhost:8080";

const baseUrl = "authentication/register";

const signup = async (credentials) => {
  const response = await axios.post(`${SERVER_URL}/${baseUrl}`, credentials);
  return response.data;
};

const signupService = { signup };

export default signupService;
