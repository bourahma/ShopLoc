import React, { createContext, useContext, useState, useEffect } from "react";

const CartContext = createContext();

export const CartProvider = ({ children }) => {
    const [cartItems, setCartItems] = useState([]);
    const [itemCount, setItemCount] = useState(0);
    const [totalPrice, setTotalPrice] = useState(0); // New state for total price

    const addToCart = (product) => {
        const existingItem = cartItems.find(
            (item) => item.productId === product.productId
        );

        if (existingItem) {
            // If the product is already in the cart, update its quantity
            setCartItems((prevItems) =>
                prevItems.map((item) =>
                    item.productId === product.productId
                        ? { ...item, quantity: item.quantity + 1 }
                        : item
                )
            );
        } else {
            // If the product is not in the cart, add it with quantity 1
            setCartItems((prevItems) => [
                ...prevItems,
                { ...product, quantity: 1 },
            ]);
            setItemCount((prevCount) => prevCount + 1);
        }
    };

    const decreaseQuantity = (productId) => {
        setCartItems((prevItems) =>
            prevItems.map((item) =>
                item.productId === productId
                    ? {
                          ...item,
                          quantity: Math.max(item.quantity - 1, 0),
                      }
                    : item
            )
        );
    };

    const removeFromCart = (productId) => {
        setCartItems((prevItems) =>
            prevItems.filter((item) => item.productId !== productId)
        );
        setItemCount((prevCount) => prevCount - 1);
    };

    const clearCart = () => {
        setCartItems([]);
        setItemCount(0);
    };

    // Calculate total price whenever cart is modified
    useEffect(() => {
        const calculateTotalPrice = () => {
            const total = cartItems.reduce(
                (acc, item) => acc + item.price * item.quantity,
                0
            );
            setTotalPrice(total);
        };

        calculateTotalPrice();
    }, [cartItems]);

    return (
        <CartContext.Provider
            value={{
                cartItems,
                itemCount,
                totalPrice,
                addToCart,
                removeFromCart,
                clearCart,
                decreaseQuantity,
            }}
        >
            {children}
        </CartContext.Provider>
    );
};

export const useCart = () => {
    return useContext(CartContext);
};
