import axios from "axios";

const API_URL = process.env.REACT_APP_SERVER_URL;

const baseUrl = "commerce/types";

const fetchCommerceTypes = async (token) => {
  try {
    const response = await axios.get(`${API_URL}/${baseUrl}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response.data;
  } catch (error) {
    console.error("Error fetching commerce types:", error);
    throw error;
  }
};

export default fetchCommerceTypes;
