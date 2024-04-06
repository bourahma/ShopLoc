import React, { useState, useEffect } from "react";
import Cards from "react-credit-cards-2";
import "react-credit-cards-2/dist/es/styles-compiled.css";
import { useNavigate } from "react-router-dom";
import { useCart } from "../services/CartContext";
import { FaCreditCard, FaHandHoldingHeart } from "react-icons/fa";
import { fetchLoyaltyCard } from "../services/carteDeFidelite";
import { FaUser, FaStar, FaGift } from "react-icons/fa";

import axios from "axios";

const SERVER_URL = process.env.REACT_APP_SERVER_URL;

const CheckoutPage = () => {
    const { cartItems, totalPrice, clearCart, removeFromCart } = useCart();
    const [loyaltyPoints, setLoyaltyPoints] = useState(0);
    const [customerData, setCustomerData] = useState(null);
    const [paymentMethod, setPaymentMethod] = useState("points");
    const [showCreditCard, setShowCreditCard] = useState(false);
    const [rechargeSuccess, setRechargeSuccess] = useState(false);
    const [totalBeforePayment, setTotalBeforePayment] = useState(0);
    const [paymentOverlay, setPaymentOverlay] = useState(false);
    const [allGift, setAllGift] = useState(false);
    const [allNoGift, setAllNoGift] = useState(false);

    const [formData, setFormData] = useState({
        cvc: "",
        expiry: "",
        focus: "",
        name: "",
        number: "",
    });
    const [validationErrors, setValidationErrors] = useState({
        cvc: "",
        expiry: "",
        name: "",
        number: "",
        montant: "",
    });

    const [paymentSuccess, setPaymentSuccess] = useState(false);
    const navigate = useNavigate();
    const token = localStorage.getItem("userToken");
    const cleanedToken = JSON.parse(token);

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

    const validateInputs = () => {
        const errors = {};

        // Validation du numéro de carte
        if (formData.number.length !== 16) {
            errors.number = "Le numéro de la carte doit comporter 16 chiffres";
        }

        // Validation du CVC
        if (formData.cvc.length !== 3) {
            errors.cvc = "Le CVC doit comporter 3 chiffres";
        }

        // Validation de la date d'expiration
        const today = new Date();
        const [month, year] = formData.expiry
            .split("/")
            .map((item) => parseInt(item));
        const expiryDate = new Date(2000 + year, month - 1); // MM/YY format

        if (expiryDate < today) {
            errors.expiry =
                "La date d'expiration doit être ultérieure à aujourd'hui";
        }

        // Validation de la somme du numéro de carte égale au CVC
        let sum = 0;
        for (let i = 0; i < formData.number.length; i++) {
            sum += parseInt(formData.number[i]);
        }
        if (sum !== parseInt(formData.cvc)) {
            errors.number =
                "La somme des chiffres du numéro de carte doit être égale au CVC";
            errors.cvc =
                "La somme des chiffres du numéro de carte doit être égale au CVC";
        }

        // Validation du montant
        const montant = parseFloat(formData.montant);
        if (montant < 1 || montant > 50 || isNaN(montant)) {
            errors.montant = "Le montant doit être compris entre 1 et 50 euros";
        }

        setValidationErrors(errors);
        return Object.keys(errors).length === 0;
    };

    const rechargeMontant = async () => {
        if (validateInputs()) {
            try {
                const response = await axios.post(
                    `${SERVER_URL}/fidelity-card/credit`,
                    {
                        amount: formData.montant,
                        fidelityCardId: loyaltyPoints.fidelityCardId,
                        numeroCarte: formData.number,
                        cvc: formData.cvc,
                        expiry: formData.expiry,
                        name: formData.name,
                    },
                    {
                        headers: {
                            Authorization: `Bearer ${cleanedToken}`,
                        },
                    }
                );
                if (response.status === 200) {
                    // Mettre à jour les points de fidélité une fois le paiement réussi
                    const updatedLoyaltyCredit = await fetchLoyaltyCard(
                        cleanedToken
                    );
                    if (updatedLoyaltyCredit) {
                        setLoyaltyPoints(updatedLoyaltyCredit);
                    }
                    setRechargeSuccess(true);
                    setShowCreditCard(false);
                    // Nettoyer les champs après la recharge
                    setFormData({
                        cvc: "",
                        expiry: "",
                        focus: "",
                        name: "",
                        number: "",
                    });
                    setTimeout(() => {
                        setRechargeSuccess(false); // Réinitialiser l'état de succès de la recharge après la redirection
                    }, 3000);
                }
            } catch (error) {
                console.error("Error recharging montant:", error);
            }
        }
    };

    const createOrder = async (fidelityCardId, giftItems) => {
        const items = paymentMethod === "points" ? giftItems : cartItems;
        try {
            const response = await axios.post(
                `${SERVER_URL}/order/`,
                {
                    commerceId: items[0].commerceId,
                    products: items.map((item) => ({
                        productId: item.productId,
                        productName: item.productName,
                        price: item.price,
                        rewardPointsPrice: item.rewardPointsPrice,
                        promotionId: item.promotionId,
                        quantity: item.quantity,
                    })),
                    fidelityCardId: fidelityCardId, // Ajoutez le fidelityCardId ici
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

    useEffect(() => {
        // Vérifier si tous les produits sont des cadeaux
        const allGifts = cartItems.every((item) => item.gift);
        const allNotGifts = cartItems.every((item) => !item.gift);

        // Effectuer le paiement uniquement pour les produits qui sont des cadeaux
        const giftItems = cartItems.filter((item) => item.gift);
        console.log(allGifts);
        if (paymentMethod === "points") {
            if (allGifts) {
                setAllGift(true);
                setAllNoGift(false);
                console.log(
                    "Tous les produits sont des cadeaux vous pouvez tous payer avec les points !"
                );
            } else if (allNotGifts) {
                setAllGift(false);
                setAllNoGift(true);
                console.log(
                    "Aucun produit cadeau dans le panier, vous pouvez pas utiliser cette methode de payment"
                );
            } else {
                setAllGift(false);
                setAllNoGift(false);
                console.log(
                    "Attention ! Vous ne pouvez pas payer tous les produits avec des points"
                );
            }
        }
    }, [paymentMethod]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        // Effectuer le paiement uniquement pour les produits qui sont des cadeaux
        const giftItems = cartItems.filter((item) => item.gift);

        if (paymentMethod === "credits") {
            console.log("Processing credit card payment...");
            handlePaymentWithLoyaltyBalance();
        } else {
            console.log("Processing point payment...");
            handlePaymentWithLoyaltyPoints(giftItems);
        }
    };

    const handlePaymentWithLoyaltyPoints = async (giftItems) => {
        // Créer d'abord la commande
        const orderId = await createOrder(
            loyaltyPoints.fidelityCardId,
            giftItems
        ); // Utilisez le fidelityCardId
        console.log(orderId);
        if (orderId) {
            try {
                const response = await axios.get(
                    `${SERVER_URL}/order/settle/using-points/${orderId}`,
                    {
                        headers: {
                            Authorization: `Bearer ${cleanedToken}`,
                        },
                    }
                );
                // Vérifier si le paiement a réussi
                if (response.status === 200) {
                    // Retirer les produits cadeaux du panier
                    const GiftItems = cartItems.filter((item) => item.gift);
                    GiftItems.forEach((item) => removeFromCart(item.productId));
                    // Mettre à jour les points de fidélité une fois le paiement réussi
                    const updatedLoyaltyCredit = await fetchLoyaltyCard(
                        cleanedToken
                    );
                    if (updatedLoyaltyCredit) {
                        setLoyaltyPoints(updatedLoyaltyCredit);
                    }
                    setPaymentSuccess(true);
                    console.log(response);
                }
            } catch (error) {
                console.error("Error processing loyalty card payment:", error);
            }
        }
    };

    const handlePaymentWithLoyaltyBalance = async () => {
        // Créer d'abord la commande
        const orderId = await createOrder(loyaltyPoints.fidelityCardId); // Utilisez le fidelityCardId
        console.log(orderId);
        if (orderId) {
            try {
                const response = await axios.get(
                    `${SERVER_URL}/order/settle/using-balance/${orderId}`,
                    {
                        headers: {
                            Authorization: `Bearer ${cleanedToken}`,
                        },
                    }
                );
                setTotalBeforePayment(totalPrice);
                // Vérifier si le paiement a réussi
                if (response.status === 200) {
                    clearCart();
                    // Mettre à jour les points de fidélité une fois le paiement réussi
                    const updatedLoyaltyCredit = await fetchLoyaltyCard(
                        cleanedToken
                    );
                    if (updatedLoyaltyCredit) {
                        setLoyaltyPoints(updatedLoyaltyCredit);
                    }
                    setPaymentSuccess(true);
                    console.log(response);
                    // Rediriger vers la page de profil après 2 secondes
                    // setTimeout(() => {
                    //     navigate("/profile");
                    // }, 2000);
                }
            } catch (error) {
                console.error("Error processing loyalty card payment:", error);
            }
        }
    };
    return (
        <div className="container mx-auto py-8">
            {paymentOverlay && (
                <div className="fixed top-0 left-0 right-0 bottom-0 flex items-center justify-center z-10 bg-red-500 text-white">
                    <div className="text-center">
                        Vous ne pouvez pas payer avec des points pour les
                        produits qui ne sont pas des cadeaux.
                    </div>
                </div>
            )}
            {rechargeSuccess && (
                <div className="fixed top-0 left-0 right-0 bottom-0 flex items-center justify-center z-10">
                    <div className="bg-green-500/90 text-white rounded-lg p-8 max-w-md w-full mx-4">
                        <div className="text-center">
                            Rechargement de balance réussi. Vous pouvez
                            maintenant acheter.
                        </div>
                    </div>
                </div>
            )}
            {paymentSuccess && (
                <div className="fixed top-0 left-0 right-0 bottom-0 flex items-center justify-center z-10">
                    <div className="bg-green-500/90 text-white rounded-lg p-8 max-w-md w-full mx-4">
                        <div className="text-center">
                            <p className="text-2xl font-semibold">
                                {paymentMethod === "credits" && (
                                    <p>
                                        Wooaah ! Vous Avez Gagné{" "}
                                        {totalBeforePayment} Points
                                    </p>
                                )}
                            </p>
                            <p
                                className={`${
                                    paymentMethod === "credits"
                                        ? "text-md font-semibold "
                                        : "text-2xl font-semibold"
                                }`}
                            >
                                Paiement réussi !
                            </p>
                            <p className="mb-4">
                                Allez voir vos commandes dans votre profil.
                            </p>
                            <div className="flex justify-center space-x-4">
                                <button
                                    className="bg-white text-green-500 text-sm px-6 py-2 rounded-md hover:bg-green-100"
                                    onClick={() => navigate("/")}
                                >
                                    Continuer le shopping
                                </button>
                                {!allGift && !allNoGift && (
                                    <button
                                        className="bg-white text-green-500 text-sm px-6 py-2 rounded-md hover:bg-green-100"
                                        onClick={() => {
                                            setPaymentSuccess(false);
                                            setPaymentMethod("credits");
                                            setAllGift(false);
                                            setAllNoGift(true);
                                        }}
                                    >
                                        Payer le reste
                                    </button>
                                )}
                                <button
                                    className="bg-white text-green-500 text-sm px-6 py-2 rounded-md hover:bg-green-100"
                                    onClick={() => navigate("/profile")}
                                >
                                    Allez voir dans votre profil
                                </button>
                            </div>
                        </div>
                    </div>
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
                    <>
                        <div>
                            <div className="max-w-md mx-auto bg-white border border-shopyellow rounded-lg overflow-hidden shadow-lg p-4">
                                <div className="flex items-center justify-center mb-4">
                                    <FaStar className="text-yellow-500 text-4xl mr-2" />
                                    <h2 className="text-2xl font-bold">
                                        Loyalty Card
                                    </h2>
                                </div>
                                <div className="flex items-center justify-center">
                                    <div className="text-gray-800 flex items-center justify-center">
                                        <span className="text-xl">Balance</span>
                                    </div>
                                    <div className="text-gray-600 mx-4">=</div>
                                    <div className="text-gray-800 flex items-center justify-center">
                                        <span className="font-semibold text-2xl mr-2">
                                            {(
                                                loyaltyPoints.balance * 1
                                            ).toFixed(2)}
                                        </span>
                                        <span className="text-xl">&euro;</span>
                                    </div>
                                </div>
                            </div>
                            <div className="max-w-md mx-auto flex justify-between items-center mt-4">
                                <button
                                    type="submit"
                                    className="bg-shopred text-white px-4 py-2 rounded-md hover:bg-red-600"
                                >
                                    Procéder au paiement
                                </button>

                                {!showCreditCard ? (
                                    <button
                                        type="button"
                                        className="bg-shopred text-white px-4 py-2 rounded-md hover:bg-red-600"
                                        onClick={() => setShowCreditCard(true)}
                                    >
                                        Recharger Mon portfeuille
                                    </button>
                                ) : (
                                    <button
                                        type="button"
                                        className="bg-shopred text-white px-4 py-2 rounded-md hover:bg-red-600"
                                        onClick={() => setShowCreditCard(false)}
                                    >
                                        Fermer Recharge de carte
                                    </button>
                                )}
                            </div>
                            <div className="text-center mt-4 font-semibold">
                                {allGift && (
                                    <p className="text-green-400">
                                        Tout vos produits sont des cadeaux,
                                        préfére de les acheter en utilisant des
                                        points si vous avez
                                    </p>
                                )}
                                {!allGift && !allNoGift && (
                                    <p className="text-orange-400">
                                        Attention ! Quelques produits sont
                                        payable avec des points si vous avez
                                    </p>
                                )}
                            </div>
                            {showCreditCard && (
                                <div className="max-w-md mx-auto mt-4">
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
                                            className={`border rounded-md p-2 ${
                                                validationErrors.number &&
                                                "border-red-500"
                                            }`}
                                            onChange={handleInputChange}
                                            value={formData.number}
                                        />
                                        {validationErrors.number && (
                                            <p className="text-red-500 text-xs mt-1">
                                                {validationErrors.number}
                                            </p>
                                        )}
                                        <input
                                            type="text"
                                            name="name"
                                            placeholder="Card Holder Name"
                                            className={`border rounded-md p-2 ${
                                                validationErrors.name &&
                                                "border-red-500"
                                            }`}
                                            onChange={handleInputChange}
                                            value={formData.name}
                                        />
                                        {validationErrors.name && (
                                            <p className="text-red-500 text-xs mt-1">
                                                {validationErrors.name}
                                            </p>
                                        )}
                                        <input
                                            type="text"
                                            name="expiry"
                                            placeholder="MM/YY Expiry"
                                            className={`border rounded-md p-2 ${
                                                validationErrors.expiry &&
                                                "border-red-500"
                                            }`}
                                            onChange={handleInputChange}
                                            value={formData.expiry}
                                        />
                                        {validationErrors.expiry && (
                                            <p className="text-red-500 text-xs mt-1">
                                                {validationErrors.expiry}
                                            </p>
                                        )}
                                        <input
                                            type="tel"
                                            name="cvc"
                                            placeholder="CVC"
                                            className={`border rounded-md p-2 ${
                                                validationErrors.cvc &&
                                                "border-red-500"
                                            }`}
                                            onChange={handleInputChange}
                                            value={formData.cvc}
                                        />
                                        {validationErrors.cvc && (
                                            <p className="text-red-500 text-xs mt-1">
                                                {validationErrors.cvc}
                                            </p>
                                        )}
                                        <input
                                            type="number"
                                            name="montant"
                                            placeholder="Montant"
                                            className={`border rounded-md p-2 ${
                                                validationErrors.montant &&
                                                "border-red-500"
                                            }`}
                                            onChange={handleInputChange}
                                            value={formData.montant}
                                        />
                                        {validationErrors.montant && (
                                            <p className="text-red-500 text-xs mt-1">
                                                {validationErrors.montant}
                                            </p>
                                        )}
                                    </div>
                                    <div className="flex justify-center mt-8">
                                        <button
                                            type="button"
                                            className="bg-blue-500 text-white px-6 py-3 rounded-md hover:bg-blue-600"
                                            onClick={rechargeMontant}
                                        >
                                            Recharger la carte
                                        </button>
                                    </div>
                                </div>
                            )}
                        </div>
                    </>
                ) : (
                    <>
                        <div className="max-w-md mx-auto bg-white  border border-shopyellow  rounded-lg overflow-hidden shadow-lg p-4">
                            <div className="flex items-center justify-center mb-4">
                                <FaStar className="text-yellow-500 text-4xl mr-2" />
                                <h2 className="text-2xl font-bold">
                                    Loyalty Card
                                </h2>
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
                        <div className="text-center mt-4 font-semibold">
                            {allGift && (
                                <p className="text-green-500">
                                    Tous les produits sont des cadeaux, vous
                                    pouvez tous payer avec les points !
                                </p>
                            )}
                            {allNoGift && (
                                <p className="text-red-500">
                                    Aucun produit cadeau dans le panier, vous ne
                                    pouvez pas utiliser cette méthode de
                                    paiement.
                                </p>
                            )}
                            {!allGift && !allNoGift && (
                                <p className="text-orange-400">
                                    Attention ! Vous ne pouvez pas payer tous
                                    les produits avec des points.
                                </p>
                            )}
                        </div>
                        <div className="flex justify-center mt-4">
                            <button
                                type="submit"
                                className={`bg-shopred text-white px-6 py-2 rounded-md hover:bg-blue-600 ${
                                    paymentMethod === "points" && allNoGift
                                        ? "opacity-50 cursor-not-allowed"
                                        : ""
                                }`}
                                disabled={allNoGift === true ? true : false}
                            >
                                {allGift
                                    ? "Procéder au paiement"
                                    : allNoGift
                                    ? "Payment Impossible"
                                    : "Payer que les cadeaux"}
                            </button>
                        </div>
                    </>
                )}
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
                            <div className="flex gap-1 items-center">
                                <span>{item.productName}</span>
                                {item.gift && (
                                    <div className="flex text-sm items-center text-shopred justify-center bg-shopyellow/75 px-2 rounded-full">
                                        Gift
                                        <FaGift className="text-shopred text-sm ml-1" />
                                    </div>
                                )}
                            </div>
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
