import React, { useState, useEffect } from "react";
import { useCart } from "../services/CartContext";
import { FaUser, FaStar } from "react-icons/fa";
import QRCode from "react-qr-code";
import axios from "axios";
import { fetchCustomerProfile } from "../services/customer";
import { fetchLoyaltyCard } from "../services/carteDeFidelite";
import vfp from "../images/vfp.png";
import { Link } from "react-router-dom";

const SERVER_URL = process.env.REACT_APP_SERVER_URL;

const ProfilePage = () => {
    const { itemCount } = useCart();
    const [customerData, setCustomerData] = useState(null);
    const [loyaltyPoints, setLoyaltyPoints] = useState(0);
    const [loggedUser, setLoggedUser] = useState(null);
    const [showQRCode, setShowQRCode] = useState(false);
    const [timer, setTimer] = useState(0);
    const [isVfpMember, setIsVfpMember] = useState(false);
    const [vfpDetails, setVfpDetails] = useState(null);

    const token = localStorage.getItem("userToken");
    const cleanedToken = JSON.parse(token);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const profileData = await fetchCustomerProfile(cleanedToken);
                const loyaltyCredit = await fetchLoyaltyCard(cleanedToken);
                const vfpData = await fetchVfpMembership(cleanedToken);

                if (profileData) {
                    setCustomerData(profileData);
                }
                if (loyaltyCredit) {
                    setLoyaltyPoints(loyaltyCredit);
                }
                if (vfpData) {
                    setIsVfpMember(vfpData.vfpMember);
                    setVfpDetails(vfpData);
                }
            } catch (error) {
                console.error(
                    "Erreur lors de la récupération des données de profil :",
                    error
                );
            }
        };

        fetchData();
    }, [cleanedToken]);

    const fetchVfpMembership = async (token) => {
        try {
            const response = await axios.get(`${SERVER_URL}/vfp/`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            return response.data;
        } catch (error) {
            console.error(
                "Erreur lors de la récupération des données d'adhésion VFP :",
                error
            );
            return null;
        }
    };

    const handleGenerateQRCode = () => {
        setShowQRCode(true);
        setTimer(30);
        const interval = setInterval(() => {
            setTimer((prevTimer) => prevTimer - 1);
        }, 1000);
        setTimeout(() => {
            setShowQRCode(false);
            clearInterval(interval);
        }, 30000); // Masquer le code QR après 30 secondes
    };

    return (
        <div className="container mx-auto py-8">
            {isVfpMember && (
                <div className="max-w-md mx-auto mb-8 bg-shopgray border text-center border-shopred rounded-lg overflow-hidden p-4">
                    <div className="flex justify-center">
                        <img
                            src={vfp}
                            alt="vfp"
                            className="w-20 text-center h-20 object-cover"
                        />
                    </div>
                    <div className="flex items-center justify-center mb-4">
                        <h2 className="text-2xl font-bold">
                            Félicitations ! <br /> Vous êtes membre VFP
                        </h2>
                    </div>
                    <div className="text-gray-800 mt-4">
                        <p>Date de début : {vfpDetails.grantedDate}</p>
                        <p>Date d'expiration : {vfpDetails.expirationDate}</p>
                    </div>
                    <Link to="/profile/vfp">
                        <button
                            className={
                                "py-1 px-2 mt-2 rounded bg-shopred hover:bg-red-700 text-white font-bold"
                            }
                        >
                            Mon espace VFP
                        </button>
                    </Link>
                </div>
            )}
            <div className="max-w-md mx-auto bg-shopgray border border-shopred rounded-lg overflow-hidden">
                <div className="p-4">
                    <div className="flex items-center justify-center">
                        <FaUser className="text-3xl text-shopred mr-2" />
                        <h2 className="text-2xl font-bold">
                            Informations du profil
                        </h2>
                    </div>
                    {customerData && (
                        <div className="mt-4">
                            <p className="text-gray-800">
                                <span className="font-semibold">Nom :</span>{" "}
                                {customerData.firstname} {customerData.lastname}
                            </p>
                            <p className="text-gray-800">
                                <span className="font-semibold">Email :</span>{" "}
                                {customerData.email}
                            </p>
                            <p className="text-gray-800">
                                <span className="font-semibold">
                                    Numéro de téléphone :
                                </span>{" "}
                                {customerData.phoneNumber}
                            </p>
                        </div>
                    )}
                </div>
            </div>
            <div className="max-w-md mx-auto mt-8 bg-shopgray border border-shopred rounded-lg overflow-hidden p-4">
                <div className="flex items-center justify-center mb-4">
                    <FaStar className="text-yellow-500 text-4xl mr-2" />
                    <h2 className="text-2xl font-bold">Carte de fidélité</h2>
                </div>
                <div className="flex items-center justify-center">
                    <div className="text-gray-800 flex items-center justify-center">
                        <span className="font-semibold text-2xl mr-2">
                            {loyaltyPoints.points}
                        </span>
                        <span className="text-xl font-bold">points</span>
                    </div>
                    <div className="text-gray-600 mx-4">=</div>
                    <div className="text-gray-800 flex items-center justify-center">
                        <span className="font-semibold text-2xl mr-2">
                            {(loyaltyPoints.points * 1).toFixed(2)}
                        </span>
                        <span className="text-xl">€</span>
                    </div>
                </div>
                <div className="flex items-center justify-center mt-2">
                    <div className="text-gray-800 flex items-center justify-center">
                        <span className="text-xl font-bold">Balance</span>
                    </div>
                    <div className="text-gray-600 mx-4">=</div>
                    <div className="text-gray-800 flex items-center justify-center">
                        <span className="font-semibold text-2xl mr-2">
                            {(loyaltyPoints.balance * 1).toFixed(2)}
                        </span>
                        <span className="text-xl">&euro;</span>
                    </div>
                </div>
            </div>

            <div className="max-w-md mx-auto mt-8 bg-shopgray border border-shopred rounded-lg overflow-hidden">
                <div className="p-4">
                    <div className="flex items-center justify-center">
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            className="h-10 w-10 text-shopred mr-2"
                            viewBox="0 0 20 20"
                            fill="currentColor"
                        >
                            <path
                                fillRule="evenodd"
                                d="M10 2a8 8 0 100 16 8 8 0 000-16zM6 10a1 1 0 011-1h6a1 1 0 110 2H7a1 1 0 01-1-1zm8-3a1 1 0 00-1-1H7a1 1 0 100 2h6a1 1 0 001-1zm0 6a1 1 0 01-1 1H7a1 1 0 010-2h6a1 1 0 011 1z"
                                clipRule="evenodd"
                            />
                        </svg>
                        <h2 className="text-2xl font-bold">
                            Nombre d'articles dans le panier
                        </h2>
                    </div>
                    <p className="text-gray-800 mt-4">
                        Nombre d'articles dans le panier : {itemCount}
                    </p>
                </div>
            </div>
            {showQRCode && (
                <div className="max-w-md mx-auto mt-8 bg-shopgray border border-shopred rounded-lg overflow-hidden">
                    <div className="p-4">
                        <div className="flex items-center justify-center">
                            <h2 className="text-2xl font-bold">Code QR</h2>
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
                            : "bg-shopred hover:bg-red-700"
                    } text-white font-bold`}
                    onClick={handleGenerateQRCode}
                    disabled={showQRCode}
                >
                    {showQRCode
                        ? `Désactivé après ... ${timer}s`
                        : "Générer le code QR"}
                </button>
            </div>
        </div>
    );
};

export default ProfilePage;
