import React, { useState, useEffect } from "react";
import { useCart } from "../services/CartContext"; 
import { FaUser, FaStar } from "react-icons/fa";
import QRCode from "react-qr-code";

const ProfilePage = () => {
    const { itemCount } = useCart(); 
    const [loyaltyPoints, setLoyaltyPoints] = useState(0);
    const [loggedUser, setLoggedUser] = useState(null);
    const [showQRCode, setShowQRCode] = useState(false);
    const [timer, setTimer] = useState(0);

    useEffect(() => {
        const loggedUser = window.localStorage.getItem("loggedUser");
        if (loggedUser) {
            setLoggedUser(JSON.parse(loggedUser));
            // Logic for fetching rest of infosss
        }
    }, []);

    // Mock function to fetch loyalty points from backend
    const fetchLoyaltyPoints = async () => {
        // try {
        //     // Replace this with your actual API endpoint to fetch loyalty points
        //     const response = await fetch("/api/loyalty-points", {
        //         method: "GET",
        //         headers: {
        //             Authorization: `Bearer ${loggedUser.token}`,
        //         },
        //     });
        //     const data = await response.json();
        //     setLoyaltyPoints(data.points);
        // } catch (error) {
        //     console.error("Error fetching loyalty points:", error);
        // }
    };

    useEffect(() => {
        if (loggedUser) {
            fetchLoyaltyPoints();
        }
    }, [loggedUser]);

    const handleGenerateQRCode = () => {
        setShowQRCode(true);
        setTimer(30);
        const interval = setInterval(() => {
            setTimer((prevTimer) => prevTimer - 1);
        }, 1000);
        setTimeout(() => {
            setShowQRCode(false);
            clearInterval(interval);
        }, 30000); // Hide QR code after 30 seconds
    };

    return (
        <div className="container mx-auto py-8">
            <div className="max-w-md mx-auto bg-white rounded-lg overflow-hidden shadow-lg">
                <div className="p-4">
                    <div className="flex items-center justify-center">
                        <FaUser className="text-4xl text-gray-600 mr-2" />
                        <h2 className="text-2xl font-bold">
                            Profile Information
                        </h2>
                    </div>
                    {loggedUser && (
                        <div className="mt-4">
                            <p className="text-gray-800">
                                <span className="font-semibold">Name:</span>{" "}
                                {loggedUser}
                            </p>
                            <p className="text-gray-800">
                                <span className="font-semibold">Email:</span>{" "}
                                {loggedUser.email}
                            </p>
                            {/* Add more user information fields as needed */}
                        </div>
                    )}
                </div>
            </div>
            <div className="max-w-md mx-auto mt-8 bg-white rounded-lg overflow-hidden shadow-lg">
                <div className="p-4">
                    <div className="flex items-center justify-center">
                        <FaStar className="text-yellow-500 text-4xl mr-2" />
                        <h2 className="text-2xl font-bold">Loyalty Card</h2>
                    </div>
                    <div className="mt-4">
                        <p className="text-gray-800">
                            <span className="font-semibold">
                                Loyalty Points:
                            </span>{" "}
                            {loyaltyPoints}
                        </p>
                        {/* Add more loyalty card information as needed */}
                    </div>
                </div>
            </div>
            <div className="max-w-md mx-auto mt-8 bg-white rounded-lg overflow-hidden shadow-lg">
                <div className="p-4">
                    <div className="flex items-center justify-center">
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            className="h-10 w-10 text-gray-600 mr-2"
                            viewBox="0 0 20 20"
                            fill="currentColor"
                        >
                            <path
                                fillRule="evenodd"
                                d="M10 2a8 8 0 100 16 8 8 0 000-16zM6 10a1 1 0 011-1h6a1 1 0 110 2H7a1 1 0 01-1-1zm8-3a1 1 0 00-1-1H7a1 1 0 100 2h6a1 1 0 001-1zm0 6a1 1 0 01-1 1H7a1 1 0 010-2h6a1 1 0 011 1z"
                                clipRule="evenodd"
                            />
                        </svg>
                        <h2 className="text-2xl font-bold">Cart Item Count</h2>
                    </div>
                    <p className="text-gray-800 mt-4">
                        Cart Item Count: {itemCount}
                    </p>
                </div>
            </div>

            {showQRCode && (
                <div className="max-w-md mx-auto mt-8 bg-white rounded-lg overflow-hidden shadow-lg">
                    <div className="p-4">
                        <div className="flex items-center justify-center">
                            <h2 className="text-2xl font-bold">QR Code</h2>
                        </div>
                        <div className="mt-4 flex justify-center">
                            <QRCode value="https://example.com" size={200} />
                        </div>
                    </div>
                </div>
            )}
            <div className="flex justify-center mt-4">
                <button
                    className={`py-2 px-4 rounded ${
                        showQRCode
                            ? "bg-gray-400 cursor-not-allowed"
                            : "bg-blue-500 hover:bg-blue-700"
                    } text-white font-bold`}
                    onClick={handleGenerateQRCode}
                    disabled={showQRCode}
                >
                    {showQRCode
                        ? `Disabled after ... ${timer}s`
                        : "Generate QR Code"}
                </button>
            </div>
        </div>
    );
};

export default ProfilePage;



           