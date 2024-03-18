import React, { useState } from "react";
import Cards from "react-credit-cards-2";
import "react-credit-cards-2/dist/es/styles-compiled.css";
import { useCart } from "../services/CartContext"; 
import { FaCreditCard, FaHandHoldingHeart } from "react-icons/fa"; 

const CheckoutPage = () => {
    const { cartItems, totalPrice } = useCart();
    const [paymentMethod, setPaymentMethod] = useState("creditCard");
    const [formData, setFormData] = useState({
        cvc: "",
        expiry: "",
        focus: "",
        name: "",
        number: "",
    });

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handlePaymentMethodChange = (method) => {
        setPaymentMethod(method);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (paymentMethod === "creditCard") {
            // Logic here
            console.log("Processing credit card payment...");
        } else {
            //Logic here
            console.log("Processing loyalty card payment...");
        }
    };

    return (
        <div className="container mx-auto py-8">
            <div className="text-center mb-4">
                <h2 className="text-3xl font-semibold">Checkout</h2>
            </div>

            <div className="flex justify-center">
                <button
                    className={`mx-4 px-4 py-2 rounded ${
                        paymentMethod === "creditCard"
                            ? "bg-blue-500 text-white"
                            : "bg-gray-300 text-gray-800"
                    }`}
                    onClick={() => handlePaymentMethodChange("creditCard")}
                >
                    <FaCreditCard className="inline-block mr-2" /> Pay with
                    Credit Card
                </button>
                <button
                    className={`mx-4 px-4 py-2 rounded ${
                        paymentMethod === "loyaltyCard"
                            ? "bg-blue-500 text-white"
                            : "bg-gray-300 text-gray-800"
                    }`}
                    onClick={() => handlePaymentMethodChange("loyaltyCard")}
                >
                    <FaHandHoldingHeart className="inline-block mr-2" /> Pay
                    with Loyalty Card
                </button>
            </div>
            <form onSubmit={handleSubmit} className="mt-8">
                {paymentMethod === "creditCard" ? (
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
                    </div>
                ) : (
                    <div className="max-w-md mx-auto">
                        {/* Content for loyalty card payment */}
                        <p>Loyalty Card Payment Content</p>
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
