import React from "react";
import { useNavigate, useParams } from "react-router-dom";

const Product = () => {
  const { commercantId } = useParams();
  const navigate = useNavigate();
  console.log(commercantId);
  const dummyData = require("../utils/commerçants.json");
  const commercant = dummyData.commercants.find(
    (f) => f.id === parseInt(commercantId)
  );

  if (!commercant) {
    return <div>commercant non trouvé</div>;
  }

  const handleBackButtonClick = () => {
    navigate(-1); // This will navigate back to the previous page
  };

  return (
    <div className="container mx-auto mt-8">
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
          <div key={produit.id} className="border border-gray-300 rounded">
            <img
              src={require(`../images/${produit.image}`)}
              alt={produit.nom}
              className="w-full h-52 object-contain mb-2"
            />
            <div className="p-4">
              <h3 className="text-lg font-semibold mb-1">{produit.nom}</h3>
              <p className="text-gray-600 mb-2">{produit.description}</p>
              <p className="text-gray-700">Prix: ${produit.prix.toFixed(2)}</p>
              <p className="text-gray-700">En stock: {produit.stock}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Product;
