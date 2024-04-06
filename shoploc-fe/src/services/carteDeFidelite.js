import axios from "axios";

const SERVER_URL = process.env.REACT_APP_SERVER_URL;

export const fetchLoyaltyCard = async (token) => {
    try {
        const response = await axios.get(`${SERVER_URL}/fidelity-card/`, {
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
        });
        if (response.status === 200) {
            return response.data;
        } else {
            console.error("Failed to fetch loyalty credit");
            return null;
        }
    } catch (error) {
        console.error("Error fetching loyalty credit:", error);
        return null;
    }
};