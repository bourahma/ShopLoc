import axios from "axios";

const SERVER_URL = process.env.REACT_APP_SERVER_URL;

const baseUrl = "promotion";

const addDiscount = async (token, discount, commerceId) => {
  const response = await axios.post(
    `${SERVER_URL}/${baseUrl}/discount/${commerceId}`,
    discount,
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }
  );
  return response.data;
};

const addOffer = async (token, offer, commerceId) => {
  const response = await axios.post(
    `${SERVER_URL}/${baseUrl}/offer/${commerceId}`,
    offer,
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }
  );
  return response.data;
};

const getCommercePromotions = async (token, commerceId) => {
  try {
    const response = await axios.get(
      `${SERVER_URL}/${baseUrl}/commerce/${commerceId}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching commerce promotions: ", error);
    throw error;
  }
};

const promotionService = {
  addDiscount,
  addOffer,
  getCommercePromotions,
};

export default promotionService;
