import axios from "axios";

const SERVER_URL = process.env.REACT_APP_SERVER_URL;

const customerLoginUrl = "authentication/customer/login";

const merchantLoginUrl = "authentication/merchant/login";

const adminLoginUrl = "authentication/administrator/login";

const login = async (credentials, url) => {
  const response = await axios.post(`${SERVER_URL}/${url}`, credentials);
  return response.data;
};

const customerLogin = (credentials) => login(credentials, customerLoginUrl);

const merchantLogin = (credentials) => login(credentials, merchantLoginUrl);

const adminLogin = (credentials) => login(credentials, adminLoginUrl);

const loginService = { customerLogin, merchantLogin, adminLogin };

export default loginService;
