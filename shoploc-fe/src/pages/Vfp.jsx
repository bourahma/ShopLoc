import React, { useState, useEffect } from "react";
import { fetchCustomerProfile } from "../services/customer";
import vfp from "../images/vfp.png";
import axios from "axios";
import QRCode from "react-qr-code";
import { FaParking, FaBicycle, FaBus } from "react-icons/fa";

const SERVER_URL = process.env.REACT_APP_SERVER_URL;

const Vfp = () => {
    const [benefits, setBenefits] = useState([]);
    const [selectedBenefitId, setSelectedBenefitId] = useState(null);
    const [licensePlate, setLicensePlate] = useState("");
    const [qrCodeData, setQrCodeData] = useState(null);
        const [customerData, setCustomerData] = useState(null);

    const [error, setError] = useState("");

    const token = localStorage.getItem("userToken");
    const cleanedToken = JSON.parse(token);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const profileData = await fetchCustomerProfile(cleanedToken);
                if (profileData) {
                    setCustomerData(profileData);
                }

                const benefitsData = await fetchBenefits(cleanedToken);
                if (benefitsData) {
                    setBenefits(benefitsData);
                }
            } catch (error) {
                console.error("Error fetching profile data:", error);
            }
        };

        fetchData();
    }, [cleanedToken]);

    const fetchBenefits = async (token) => {
        try {
            const response = await axios.get(`${SERVER_URL}/benefit/`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            return response.data;
        } catch (error) {
            console.error("Error fetching benefits data:", error);
            return [];
        }
    };

    const handleApplyBenefit = async (benefitId) => {
        if (benefitId === 1 && licensePlate === "") {
            setError("Veuillez remplir la plaque d'immatriculation.");
            return;
        }
        setSelectedBenefitId(benefitId);
        try {
            const response = await axios.get(
                `${SERVER_URL}/benefit/${benefitId}`,
                {
                    headers: {
                        Authorization: `Bearer ${cleanedToken}`,
                    },
                }
            );
            setQrCodeData(response.data);
            setError("");
        } catch (error) {
            console.error("Error fetching QR code:", error);
        }
    };

    return (
        <div className="container mx-auto py-8">
            <div className="flex justify-center">
                <img src={vfp} alt="VFP" className="w-20 h-20 object-cover" />
            </div>
            <div className="flex justify-center">
                <h1 className="text-4xl font-bold">Mon Espace VFP</h1>
            </div>
            <div className="mt-10 px-20">
                {benefits.map((benefit) => (
                    <form
                        key={benefit.benefitId}
                        className="flex items-center justify-between py-2 border-b"
                        onSubmit={(e) => {
                            e.preventDefault();
                        }}
                    >
                        <div className="flex items-center gap-2">
                            {benefit.benefitId === 1 && (
                                <FaParking className="text-shopred" />
                            )}
                            {benefit.benefitId === 2 && (
                                <FaBicycle className="text-shopred" />
                            )}
                            {benefit.benefitId === 3 && (
                                <FaBus className="text-shopred" />
                            )}
                        <p className="text-lg">{benefit.description}</p>
                        </div>
                        {benefit.benefitId === 1 && (
                            <div className="mt-4">
                                <input
                                    type="text"
                                    className="border border-gray-300 p-2 rounded-md"
                                    placeholder="Plaque d'immatriculation"
                                    value={licensePlate}
                                    onChange={(e) =>
                                        setLicensePlate(e.target.value)
                                    }
                                    required
                                />
                                {error && (
                                    <p className="text-red-500 mt-1">{error}</p>
                                )}
                            </div>
                        )}
                        <button
                            className={`px-4 py-2 ${
                                selectedBenefitId === benefit.benefitId
                                    ? "bg-gray-500 cursor-not-allowed"
                                    : "bg-blue-500 hover:bg-blue-600"
                            } text-white rounded-md shadow`}
                            disabled={selectedBenefitId === benefit.benefitId}
                            onClick={() =>
                                handleApplyBenefit(benefit.benefitId)
                            }
                            type="submit"
                        >
                            Appliquer cet avantage
                        </button>
                    </form>
                ))}
            </div>
            {qrCodeData && (
                <>
                    <p className="text-green-500 text-center mt-4">
                        Ce QR code est valable une fois pendant 24 heures.
                    </p>
                    <div className="mt-4 flex justify-center">
                        <QRCode value={qrCodeData} />
                    </div>
                </>
            )}
        </div>
    );
};

export default Vfp;
