import axios from "axios";

const SERVER_URL = process.env.REACT_APP_SERVER_URL;

const baseUrl = "product";

const getProductsCategories = async (token, commerceId) => {
  try {
    const response = await axios.get(
      `${SERVER_URL}/${baseUrl}/categories/${commerceId}`,
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

const addProductCategory = async (token, commerceId, category) => {
  try {
    const response = await axios.post(
      `${SERVER_URL}/${baseUrl}/category/${commerceId}`,
      category,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error adding product category: ", error);
    throw error;
  }
};

const getProductDetails = async (token, productId) => {
  try {
    const response = await axios.get(
      `${SERVER_URL}/${baseUrl}/detail/${productId}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching product details: ", error);
    throw error;
  }
};

const productServices = {
  getProductsCategories,
  addProductCategory,
  getProductDetails,
};

export default productServices;
