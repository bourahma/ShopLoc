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


const fetchProductsCategories = async (token, commerceId) => {
    try {
        const response = await axios.get(
            `${SERVER_URL}/product/categories/${commerceId}`,
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            }
        );
        return response.data;
    } catch (error) {
        console.error("Error fetching products categories: ", error);
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

const fetchCommerce = async (token, commerceId) => {
  try {
    const response = await axios.get(`${SERVER_URL}/${baseUrl}/${commerceId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching products: ", error);
    throw error;
  }
};

const getCommerceId = async (token, merchantId) => {
  try {
    const response = await axios.get(
      `${SERVER_URL}/${baseUrl}/merchant/${merchantId}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching commerce: ", error);
    throw error;
  }
};

const fetchCommerces = async (token) => {
  try {
    const response = await axios.get(`${SERVER_URL}/${baseUrl}/`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching commercants:", error);
    throw error;
  }
};

const commerceService = {
  fetchProductsCategories,
  registerCommerce,
  addProduct,
  fetchProducts,
  fetchMerchantProducts,
  fetchCommerce,
  fetchCommerces,
  getCommerceId,
};

export default commerceService;
