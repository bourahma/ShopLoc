import axios from "axios";

const apiUrl = process.env.REACT_APP_SERVER_URL;

const fetchProducts = async (token, commerceId) => {
    try {
        const response = await axios.get(`${apiUrl}/commerce/${commerceId}/products`, {
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
export default fetchProducts;
