import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import commerceService from "../services/commerce";
import productSample from "../images/productSample.png";
import { useCart } from "../services/CartContext";
import RatingStars from "../components/RatingStars";
import { FaGift } from "react-icons/fa";

const Product = () => {
    const { commercantId } = useParams();
    const navigate = useNavigate();
    const [commerce, setCommerce] = useState(null);
    const [searchQuery, setSearchQuery] = useState("");
    const [selectedCategory, setSelectedCategory] = useState(""); // State to store the selected category
    const { addToCart } = useCart();
    const [products, setProducts] = useState([]);
    const [showOverlay, setShowOverlay] = useState(true);
    const [categories, setCategories] = useState([]); // State to store the fetched categories
    const token = localStorage.getItem("userToken");
    const cleanedToken = JSON.parse(token);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const commerceData = await commerceService.fetchCommerce(
                    cleanedToken,
                    commercantId
                );
                setCommerce(commerceData);

                const commerceProducts = await commerceService.fetchProducts(
                    cleanedToken,
                    commercantId
                );
                setProducts(commerceProducts);

                // Fetch categories
                const categoriesData =
                    await commerceService.fetchProductsCategories(
                        cleanedToken,
                        commercantId
                    );
                setCategories(categoriesData);
            } catch (error) {
                console.error("Error fetching commerce or products:", error);
            }
        };

        fetchData();
    }, [commercantId, cleanedToken]);

    if (!commerce) {
        return (
            <div className="container mx-auto my-8 px-12">
                Commerce non trouvé
            </div>
        );
    }

    console.log(products);

    // Filter products based on the selected category
    const filteredProducts = products.filter((product) => {
        const productNameIncludesQuery = product.productName
            .toLowerCase()
            .includes(searchQuery.toLowerCase());
        const productBelongsToSelectedCategory =
            !selectedCategory ||
            product.productCategoryLabel === selectedCategory;
        return productNameIncludesQuery && productBelongsToSelectedCategory;
    });

    const handleBackButtonClick = () => {
        navigate(-1); // This will navigate back to the previous page
    };

    const handleAddToCart = (product) => {
        addToCart(product);
        console.log(`Product added to cart: ${product.productIdName}`);
    };

    const toggleOverlay = () => {
        setShowOverlay(!showOverlay);
    };

    return (
        <div className="container grid grid-cols-12 gap-10 mx-auto my-8 px-12">
            <div className="col-span-2">
                <button
                    className="flex bg-shopred w-full text-center items-center text-white rounded-md border-r border-gray-100 py-2 hover:bg-red-700 hover:text-white px-3 mr-4"
                    onClick={handleBackButtonClick}
                >
                    <svg
                        className="w-5 mr-2"
                        fill="currentColor"
                        viewBox="0 0 20 20"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            fillRule="evenodd"
                            d="M7.707 14.707a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 1.414L5.414 9H17a1 1 0 110 2H5.414l2.293 2.293a1 1 0 010 1.414z"
                            clipRule="evenodd"
                        ></path>
                    </svg>
                    Back
                </button>
                <img
                    src={commerce.imageUrl}
                    alt={commerce.commerceName}
                    className="w-full object-cover mt-4 border-4 border-shopred"
                />
                <div>
                    <h1 className="text-lg font-bold text-center mt-1 bg-shopgray px-1 py-2 border-4 border-shopred">
                        {commerce.commerceName}
                    </h1>
                </div>
                <div className="bg-shopgray px-1 py-2 mt-3">
                    <RatingStars rating={4} />
                </div>
                <div className="bg-shopgray px-1 py-2 mt-3 text-sm font-semibold">
                    <p>
                        {commerce.addressDTO.street}, {commerce.addressDTO.city}
                    </p>
                    <p>
                        {commerce.openingHour}-{commerce.closingHour}
                    </p>
                </div>
            </div>
            <div className="col-span-10">
                <div className="flex items-center mb-4 flex-1 gap-6">
                    <div className="">
                        <select
                            className="p-2 border border-shopred rounded bg-shopgray"
                            value={selectedCategory}
                            onChange={(e) =>
                                setSelectedCategory(e.target.value)
                            }
                        >
                            <option value="">Toutes les catégories</option>
                            {categories.map((category) => (
                                <option
                                    key={category.productCategoryId}
                                    value={category.label}
                                >
                                    {category.label}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="flex-1">
                        <input
                            type="text"
                            placeholder="Rechercher un produit..."
                            className="p-2 border border-shopred rounded w-full bg-shopgray"
                            value={searchQuery}
                            onChange={(e) => setSearchQuery(e.target.value)}
                        />
                    </div>
                </div>
                <div className="grid md:grid-cols-4 gap-4">
                    {filteredProducts.length === 0 ? (
                        <p className="text-red-500 font-bold">
                            Aucun produit trouvé
                        </p>
                    ) : (
                        filteredProducts.map((produit) => (
                            <div
                                key={produit.productId}
                                className="border border-gray-300 rounded overflow-hidden relative "
                            >
                                <div className="relative">
                                    <img
                                        src={productSample}
                                        alt={produit.productName}
                                        className="w-full h-40 object-cover bg-shopgray"
                                    />
                                    {showOverlay && produit.promotion && (
                                        <div className="absolute inset-0 flex items-center justify-center">
                                            <div className="absolute inset-0 bg-yellow-500 opacity-75"></div>
                                            <div className="relative text-white text-center">
                                                <button
                                                    className="absolute top-[-40px] right-0 m-2 text-white"
                                                    onClick={toggleOverlay}
                                                >
                                                    <svg
                                                        xmlns="http://www.w3.org/2000/svg"
                                                        className="h-6 w-6"
                                                        fill="none"
                                                        viewBox="0 0 24 24"
                                                        stroke="currentColor"
                                                    >
                                                        <path
                                                            strokeLinecap="round"
                                                            strokeLinejoin="round"
                                                            strokeWidth={2}
                                                            d="M6 18L18 6M6 6l12 12"
                                                        />
                                                    </svg>
                                                </button>
                                                <p className="text-lg font-bold mb-2">
                                                    {
                                                        produit.promotion
                                                            .description
                                                    }
                                                </p>
                                                <p className="text-sm">
                                                    Fin de la promotion:{" "}
                                                    {produit.promotion.endDate}
                                                </p>
                                            </div>
                                        </div>
                                    )}
                                </div>

                                <div className="px-4 py-2 mt-3 bg-shopgray">
                                    <h3 className="text-lg font-semibold flex justify-between">
                                        {produit.productName}
                                        {produit.gift && (
                                            <div className="flex text-sm items-center text-shopred justify-center bg-shopyellow/75 px-2 rounded-full">                   
                                                Gift
                                                <FaGift className="text-shopred text-sm ml-1" />
                                            </div>
                                        )}
                                    </h3>
                                    <p className="text-gray-600">
                                        {produit.description}
                                    </p>
                                    <p className="text-gray-700 mb-2">
                                        {produit.quantity > 0
                                            ? `${produit.quantity} produits restants`
                                            : "Produit indisponible"}
                                    </p>
                                    <div className="flex items-center justify-between">
                                        <p className="text-gray-700">
                                            Prix: {produit.price.toFixed(2)} €
                                        </p>
                                        <button
                                            className="bg-blue-500 text-white px-4 py-1 rounded-xs bg-shopred"
                                            onClick={() =>
                                                handleAddToCart(produit)
                                            }
                                        >
                                            Ajouter au panier
                                        </button>
                                    </div>
                                </div>
                            </div>
                        ))
                    )}
                </div>
            </div>
        </div>
    );
};

export default Product;
