import axios from "axios";

const SERVER_URL = process.env.REACT_APP_SERVER_URL;

const baseUrl = "commerce";

const registerCommerce = async (credentials, token) => {
  const response = await axios.post(`${SERVER_URL}/${baseUrl}/`, credentials, {
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "multipart/form-data",
    },
  });
  return response.data;
};

const addProduct = async (product, token, commerceId) => {
  const response = await axios.post(
    `${SERVER_URL}/${baseUrl}/${commerceId}`,
    product,
    {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "multipart/form-data",
      },
    }
  );
  return response.data;
};

const fetchProducts = async (token, commerceId) => {
  try {
    const response = await axios.get(
      `${SERVER_URL}/${baseUrl}/${commerceId}/products`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching products: ", error);
    throw error;
  }
};

const fetchMerchantProducts = async (token, merchantId) => {
  try {
    const response = await axios.get(
      `${SERVER_URL}/${baseUrl}/merchant/${merchantId}/products`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching merchant products: ", error);
    throw error;
  }
};

const commerceService = {
  registerCommerce,
  addProduct,
  fetchProducts,
  fetchMerchantProducts,
};

export default commerceService;
