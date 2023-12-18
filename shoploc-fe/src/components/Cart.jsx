import React, { useState } from "react";
import { Link } from "react-router-dom";
import { Button } from "flowbite-react";
import { useCart } from "./CartContext";
import productSample from "../images/productSample.png";
import deleteicon from "../images/delete.png";
import paymentMethods from "../images/paymentMethods.png"; 


const Cart = () => {
    const {
        cartItems,
        removeFromCart,
        clearCart,
        totalPrice,
        itemsCount,
        addToCart,
        decreaseQuantity,
    } = useCart();



    return (
        <div className="container mx-auto my-8 px-12">
            <h2 className="text-3xl font-semibold mb-4">Panier</h2>
            {cartItems.length === 0 ? (
                <p className="text-gray-600">Votre panier est vide.</p>
            ) : (
                <div className="grid grid-cols-6 gap-4 ">
                    <div className="col-span-3 grid grid-cols-1 gap-4">
                        {cartItems.map((product) => (
                            <div className="flex flex-col rounded-lg bg-white shadow-[0_2px_15px_-3px_rgba(0,0,0,0.07),0_10px_20px_-2px_rgba(0,0,0,0.04)] dark:bg-neutral-700 md:max-w-xl md:flex-row">
                                <img
                                    className="h-28 w-28 object-cover rounded-t-lg object-cover md:!rounded-none md:!rounded-l-lg"
                                    src={productSample}
                                    alt=""
                                />
                                <div className="flex flex-col justify-start py-2 px-6">
                                    <h5 className="mb-2 text-xl font-medium text-neutral-800 dark:text-neutral-50">
                                        {product.productName}
                                    </h5>
                                    <p className="mb-4 text-base text-neutral-600 dark:text-neutral-200">
                                        {product.description}
                                    </p>
                                    <div className="flex items-center gap-6">
                                        <p className="text-neutral-500 dark:text-neutral-300 font-bold">
                                            Prix : {product.price} €
                                        </p>
                                        <div className="flex items-center">
                                            <button
                                                className="bg-gray-500 text-sm text-white px-2 py-0.5 rounded-l"
                                                onClick={() =>
                                                    addToCart(product)
                                                }
                                            >
                                                +
                                            </button>
                                            <p className="mx-2">
                                                {product.quantity}
                                            </p>
                                            <button
                                                className="bg-gray-500 text-sm text-white px-2 py-0.5 rounded-r"
                                                onClick={() =>
                                                    decreaseQuantity(
                                                        product.productId
                                                    )
                                                }
                                                disabled={product.quantity <= 1}
                                            >
                                                -
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                <button
                                    onClick={() =>
                                        removeFromCart(product.productId)
                                    }
                                    className="text-red-500 flex items-center ml-auto p-4"
                                >
                                    <img
                                        src={deleteicon}
                                        className="w-4 h-4 mr-2"
                                        alt=""
                                    />
                                    Supprimer produit
                                </button>
                            </div>
                        ))}
                    </div>
                    <div className="col-span-3 ">
                        <div className="flex justify-between items-center mt-8">
                            <div className="text-xl font-semibold text-green-600">
                                <p>Prix Total: {totalPrice.toFixed(2)} €</p>
                            </div>
                            <Button
                                className="bg-red-600 text-sm text-white hover:bg-red-200"
                                onClick={clearCart}
                            >
                                Vider le panier
                            </Button>
                        </div>
                        <div className="flex justify-center">

                        <img src={paymentMethods} alt="" className="h-42 w-3/4 mt-20 mb-3" />
                        </div>
                        <Link to="/checkout" className="w-full">
                            <button className="bg-blue-500 text-white px-4 py-2 rounded mt-4 w-full">
                                Passer au paiement
                            </button>
                        </Link>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Cart;
