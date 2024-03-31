import axios from "axios";

const SERVER_URL = process.env.REACT_APP_SERVER_URL;

export const fetchCustomerProfile = async (token) => {
    try {
        const response = await axios.get(`${SERVER_URL}/customer/profile`, {
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
        });
        if (response.status === 200) {
            return response.data;
        } else {
            console.error("Failed to fetch customer profile");
            return null;
        }
    } catch (error) {
        console.error("Error fetching customer profile:", error);
        return null;
    }
};
