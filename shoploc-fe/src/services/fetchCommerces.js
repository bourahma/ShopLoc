import axios from "axios";

const apiUrl = process.env.REACT_APP_SERVER_URL;

export const fetchCommerces = async (token) => {
    try {
        const response = await axios.get(`${apiUrl}/commerce/`, {
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

export default fetchCommerces;

export const fetchCommerce = async (token, commerceId) => {
    try {
        const response = await axios.get(`${apiUrl}/commerce/${commerceId}`, {
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


