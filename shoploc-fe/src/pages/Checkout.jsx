import React, { useState, useEffect } from "react";
import Cards from "react-credit-cards-2";
import "react-credit-cards-2/dist/es/styles-compiled.css";
import { useNavigate } from "react-router-dom";
import { useCart } from "../services/CartContext";
import { FaCreditCard, FaHandHoldingHeart } from "react-icons/fa";
import { fetchLoyaltyCard } from "../services/carteDeFidelite";
import { FaUser, FaStar } from "react-icons/fa";

import axios from "axios";

const SERVER_URL = process.env.REACT_APP_SERVER_URL;

const CheckoutPage = () => {
    const { cartItems, totalPrice } = useCart();
    const [loyaltyPoints, setLoyaltyPoints] = useState(0);
    const [customerData, setCustomerData] = useState(null);
    const [paymentMethod, setPaymentMethod] = useState("credits");
    const [showCreditCard, setShowCreditCard] = useState(false);
    const [formData, setFormData] = useState({
        cvc: "",
        expiry: "",
        focus: "",
        name: "",
        number: "",
    });
    console.log(loyaltyPoints);

    const [paymentSuccess, setPaymentSuccess] = useState(false);
    const navigate = useNavigate();
    const token = localStorage.getItem("userToken");
    const cleanedToken = token.replace(/['"]+/g, "");

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handlePaymentMethodChange = (method) => {
        setPaymentMethod(method);
    };

    useEffect(() => {
        const fetchLoyalty = async () => {
            const loyaltyCredit = await fetchLoyaltyCard(cleanedToken);
            if (loyaltyCredit) {
                setLoyaltyPoints(loyaltyCredit);
            }
        };

        fetchLoyalty();
    }, []);

    const createOrder = async () => {
        try {
            const response = await axios.post(
                `${SERVER_URL}/order/`,
                {
                    commerceId: cartItems[0].commerceId,
                    // commerceName: "Nom du Commerce", // Remplacer par le nom du commerce
                    products: cartItems.map((item) => ({
                        productId: item.productId,
                        productName: item.productName,
                        price: item.price,
                        rewardPointsPrice: item.rewardPointsPrice,
                        promotionId: item.promotionId,
                        quantity: item.quantity,
                    })),
                },
                {
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${cleanedToken}`,
                    },
                }
            );
            if (response.status === 200) {
                return response.data.orderId; // Retourne l'ID de la commande
            }
        } catch (error) {
            console.error("Error creating order:", error);
        }
    };

    const handlePaymentWithLoyaltyPoints = async () => {
        // Créer d'abord la commande
        const orderId = await createOrder();
        console.log(orderId);

        if (orderId) {
            try {
                const response = await axios.get(
                    `${SERVER_URL}/order/settle/using-points/${orderId}`,
                    {
                        headers: {
                            Authorization: `Bearer ${cleanedToken}`,
                        },
                        // data: {
                        //     fidelity_card_id: loyaltyPoints.fidelityCardId,
                        // },
                    }
                );
                // Vérifier si le paiement a réussi
                if (response.data.success) {
                    setPaymentSuccess(true);
                    // Rediriger vers la page de profil après 2 secondes
                    setTimeout(() => {
                        navigate("/profile");
                    }, 2000);
                }
            } catch (error) {
                console.error("Error processing loyalty card payment:", error);
            }
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (paymentMethod === "creditCard") {
            // Logic here
            console.log("Processing credit card payment...");
        } else {
            // Paiement avec la carte de fidélité
            handlePaymentWithLoyaltyPoints();
        }
    };

    return (
        <div className="container mx-auto py-8">
            {paymentSuccess && (
                <div className="text-green-600 font-bold text-center">
                    Payment successful! Redirecting to profile page...
                </div>
            )}
            <div className="text-center mb-4">
                <h2 className="text-3xl font-semibold">Checkout</h2>
            </div>

            <div className="flex justify-center">
                <button
                    className={`mx-4 px-4 py-2 rounded ${
                        paymentMethod === "credits"
                            ? "bg-blue-500 text-white"
                            : "bg-gray-300 text-gray-800"
                    }`}
                    onClick={() => handlePaymentMethodChange("credits")}
                >
                    <FaCreditCard className="inline-block mr-2" /> Payer avec la
                    carte de credit
                </button>
                <button
                    className={`mx-4 px-4 py-2 rounded ${
                        paymentMethod === "points"
                            ? "bg-blue-500 text-white"
                            : "bg-gray-300 text-gray-800"
                    }`}
                    onClick={() => handlePaymentMethodChange("points")}
                >
                    <FaHandHoldingHeart className="inline-block mr-2" /> Payer
                    avec vos points de fidelité
                </button>
            </div>
            <form onSubmit={handleSubmit} className="mt-8">
                {paymentMethod === "credits" ? (
                    <div className="max-w-md mx-auto bg-white rounded-lg overflow-hidden shadow-lg p-4">
                        <div className="flex items-center justify-center mb-4">
                            <FaStar className="text-yellow-500 text-4xl mr-2" />
                            <h2 className="text-2xl font-bold">Loyalty Card</h2>
                        </div>
                        <div className="flex items-center justify-center">
                            <div className="text-gray-800 flex items-center justify-center">
                                <span className="text-xl">Balance</span>
                            </div>
                            <div className="text-gray-600 mx-4">=</div>
                            <div className="text-gray-800 flex items-center justify-center">
                                <span className="font-semibold text-2xl mr-2">
                                    {(loyaltyPoints.balance * 1).toFixed(2)}
                                </span>
                                <span className="text-xl">&euro;</span>
                            </div>
                        </div>
                        <div className="flex justify-center mt-2">
                            <button
                                type="button"
                                className="bg-shopred text-white px-4 py-1 rounded-md hover:bg-red-600"
                                onClick={() => setShowCreditCard(true)}
                            >
                                Recharger Mon portfeuille
                            </button>
                        </div>
                        ShowCreditCard?(
                        <div className="max-w-md mx-auto">
                            <Cards
                                cvc={formData.cvc}
                                expiry={formData.expiry}
                                focused={formData.focus}
                                name={formData.name}
                                number={formData.number}
                            />
                            <div className="mt-4">
                                <input
                                    type="tel"
                                    name="number"
                                    placeholder="Card Number"
                                    className="input-field"
                                    onChange={handleInputChange}
                                />
                                <input
                                    type="text"
                                    name="name"
                                    placeholder="Card Holder Name"
                                    className="input-field"
                                    onChange={handleInputChange}
                                />
                                <input
                                    type="text"
                                    name="expiry"
                                    placeholder="MM/YY Expiry"
                                    className="input-field"
                                    onChange={handleInputChange}
                                />
                                <input
                                    type="tel"
                                    name="cvc"
                                    placeholder="CVC"
                                    className="input-field"
                                    onChange={handleInputChange}
                                />
                            </div>
                            )
                        </div>
                    </div>
                ) : (
                    <div className="max-w-md mx-auto bg-white rounded-lg overflow-hidden shadow-lg p-4">
                        <div className="flex items-center justify-center mb-4">
                            <FaStar className="text-yellow-500 text-4xl mr-2" />
                            <h2 className="text-2xl font-bold">Loyalty Card</h2>
                        </div>
                        <div className="flex items-center justify-center">
                            <div className="text-gray-800 flex items-center justify-center">
                                <span className="font-semibold text-2xl mr-2">
                                    {loyaltyPoints.points}
                                </span>
                                <span className="text-xl">points</span>
                            </div>
                            <div className="text-gray-600 mx-4">=</div>
                            <div className="text-gray-800 flex items-center justify-center">
                                <span className="font-semibold text-2xl mr-2">
                                    {(loyaltyPoints.points * 1).toFixed(2)}
                                </span>
                                <span className="text-xl">&euro;</span>
                            </div>
                        </div>
                    </div>
                )}
                <div className="flex justify-center mt-8">
                    <button
                        type="submit"
                        className="bg-blue-500 text-white px-6 py-3 rounded-md hover:bg-blue-600"
                    >
                        Proceed to Payment
                    </button>
                </div>
            </form>
            <div className="max-w-md mx-auto mt-8">
                {/* Display cart summary */}
                <h3 className="text-xl font-semibold mb-4">Cart Summary</h3>
                <ul>
                    {cartItems.map((item) => (
                        <li
                            key={item.productId}
                            className="flex justify-between mb-2"
                        >
                            <span>{item.productName}</span>
                            <span>{item.price} €</span>
                        </li>
                    ))}
                </ul>
                <div className="flex justify-between mt-4">
                    <span className="font-semibold">Total:</span>
                    <span>{totalPrice} €</span>
                </div>
            </div>
        </div>
    );
};

export default CheckoutPage;
