import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import fetchProducts from "../services/fetchProducts";
import fetchCommercant  from "../services/fetchCommerce";
import productSample from "../images/productSample.png";

const Product = () => {
    const { commercantId } = useParams();
    const navigate = useNavigate();
    const [commerce, setCommerce] = useState(null);
    const [searchQuery, setSearchQuery] = useState("");
    const [products, setProducts] = useState([]);
    const token = localStorage.getItem("userToken");
    const cleanedToken = token ? token.replace(/['"]+/g, "") : null;

    useEffect(() => {
        const fetchData = async () => {
            try {
                // fetchCommerce
                const commerceData = await fetchCommercant(
                    cleanedToken,
                    commercantId
                );
                setCommerce(commerceData);

                // Fetch products
                const commerceProducts = await fetchProducts(
                    cleanedToken,
                    commercantId
                );
                setProducts(commerceProducts);
            } catch (error) {
                console.error("Error fetching commerce or products:", error);
            }
        };

        fetchData();
    }, [commercantId, cleanedToken]);
    console.log(commerce, products);

    if (!commerce) {
        return <div>Commerce non trouvé</div>;
    }

    const filteredProducts = products.filter((product) =>
        product.productName.toLowerCase().includes(searchQuery.toLowerCase())
    );

    const handleBackButtonClick = () => {
        navigate(-1); // This will navigate back to the previous page
    };

    return (
        <div className="container mx-auto my-8 px-12">
            <div className="flex items-center mb-4">
                <button
                    type="button"
                    className="bg-gray-800 text-white rounded-md border-r border-gray-100 py-2 hover:bg-red-700 hover:text-white px-3 mr-4"
                    onClick={handleBackButtonClick}
                >
                    <div className="flex flex-row align-middle">
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
                        <p className="ml-2">Back</p>
                    </div>
                </button>
                <img
                    src={commerce.imageUrl}
                    alt={commerce.commerceName}
                    className="w-16 h-16 object-cover rounded-full mr-4"
                />
                <div>
                    <h1 className="text-2xl font-semibold">
                        {commerce.commerceName}
                    </h1>
                </div>
                <div className="px-6 flex-1">
                    <input
                        type="text"
                        placeholder="Rechercher un produit..."
                        className="p-2 border border-gray-300 rounded w-full"
                        value={searchQuery}
                        onChange={(e) => setSearchQuery(e.target.value)}
                    />
                </div>
            </div>
            <h2 className="text-xl font-semibold mb-4">Produits</h2>
            <div className="grid md:grid-cols-4  grid-cols-1 gap-4">
                {filteredProducts.length === 0 ? (
                    <p className="md:col-span-4 text-red-500 font-bold">
                        Aucun produit trouvé
                    </p>
                ) : (
                    filteredProducts.map((produit) => (
                        <div
                            key={produit.productId}
                            className="border border-gray-300 rounded"
                        >
                            <img
                                src={productSample}
                                alt={produit.productName}
                                className="w-full h-52 object-contain mb-2"
                            />
                            <div className="p-4">
                                <h3 className="text-lg font-semibold mb-1">
                                    {produit.productName}
                                </h3>
                                <p className="text-gray-600">
                                    Description : {produit.description}
                                </p>
                                <p className="text-gray-700">
                                    Prix : {produit.price.toFixed(2)} €
                                </p>

                                {produit.quantity > 1 ? (
                                    <p className="text-gray-700">
                                        {produit.quantity} produits restants
                                    </p>
                                ) : produit.quantity === 1 ? (
                                    <p className="text-gray-700">
                                        {produit.quantity} produit restant
                                    </p>
                                ) : (
                                    <p className="text-red-600">
                                        Produit indisponible
                                    </p>
                                )}
                            </div>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
};

export default Product;
