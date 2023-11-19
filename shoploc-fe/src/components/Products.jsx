import React from "react";
import { useParams } from "react-router-dom";

const Product = () => {
    const { commercantId } = useParams();
    console.log(commercantId);
    const dummyData = require("../utils/commerçants.json");
    const commercant = dummyData.commercants.find(
        (f) => f.id === parseInt(commercantId)
    );

    if (!commercant) {
        return <div>commercant non trouvé</div>;
    }

    return (
        <div className="container mx-auto mt-8">
            <div className="flex items-center mb-4">
                <img
                    src={require(`../images/${commercant.image}`)}
                    alt={commercant.nom}
                    className="w-16 h-16 object-cover rounded-full mr-4"
                />
                <div>
                    <h1 className="text-2xl font-semibold">{commercant.nom}</h1>
                    <p className="text-gray-600">{commercant.description}</p>
                </div>
            </div>
            <h2 className="text-xl font-semibold mb-4">Produits</h2>
            <div className="grid grid-cols-4 gap-4">
                {commercant.produits.map((produit) => (
                    <div
                        key={produit.id}
                        className="border border-gray-300 rounded"
                    >
                        <img
                            src={require(`../images/${produit.image}`)}
                            alt={produit.nom}
                            className="w-full h-52 object-contain mb-2"
                        />
                        <div className="p-4">
                            <h3 className="text-lg font-semibold mb-1">
                                {produit.nom}
                            </h3>
                            <p className="text-gray-600 mb-2">
                                {produit.description}
                            </p>
                            <p className="text-gray-700">
                                Prix: ${produit.prix.toFixed(2)}
                            </p>
                            <p className="text-gray-700">
                                En stock: {produit.stock}
                            </p>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Product;
