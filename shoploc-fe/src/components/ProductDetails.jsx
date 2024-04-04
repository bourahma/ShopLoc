import { Card, Button, Avatar } from "flowbite-react";
import { useParams, useNavigate } from "react-router-dom";
import { useState } from "react";
import useProducts from "../hooks/useProducts";
import productSample from "../images/productSample.png";

const ProductDetails = () => {
  const [error, setError] = useState(null);

  const navigate = useNavigate();
  const productId = useParams().productId;

  console.log(productId);
  const token = localStorage.getItem("userToken");

  const cleanedToken = token ? token.replace(/['"]+/g, "") : null;

  const productResponse = useProducts.useProductDetails(
    cleanedToken,
    productId
  );

  if (productResponse.isError) {
    setError(productResponse.error);
    console.log("Error fetching product", productResponse.error);
  }

  if (productResponse.isLoading) {
    return <div>Loading...</div>;
  }

  let product = null;
  if (productResponse.isSuccess) {
    product = productResponse.data;
    console.log("product", product);
  }

  return (
    <div>
      {error && <div>Error: {error.message}</div>}
      <div className="flex flex-col my-6 mx-12 md:flex-row md:space-x-4">
        <div className="flex-1">
          <Card
            className="max-w-sm"
            imgSrc={product?.imageUrl || productSample}
            horizontal
          >
            <h5 className="text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
              {product?.productName}
            </h5>
            <p className="font-normal text-gray-700 dark:text-gray-400">
              {product?.description}
            </p>

            <p className="text-gray-700 dark:text-gray-400">
              Prix: {product?.price}
            </p>
            <p className="text-gray-700 dark:text-gray-400">
              Quantité: {product?.quantity}
            </p>
            <p className="text-gray-700 dark:text-gray-400">
              Prix en points de récompense: {product?.rewardPointsPrice}
            </p>
            <p className="text-gray-700 dark:text-gray-400">
              Cadeau: {product?.gift ? "Oui" : "Non"}
            </p>
            <p className="text-gray-700 dark:text-gray-400">
              Catégorie de produit: {product?.productCategoryLabel}
            </p>
            {product?.promotion && (
              <>
                <p className="text-gray-700 dark:text-gray-400">
                  Date de début de la promotion: {product?.promotion.startDate}
                </p>
                <p className="text-gray-700 dark:text-gray-400">
                  Date de fin de la promotion: {product?.promotion.endDate}
                </p>
                <p className="text-gray-700 dark:text-gray-400">
                  Description de la promotion: {product?.promotion.description}
                </p>
                {product?.promotion.promotionType === "DISCOUNT" && (
                  <p className="text-gray-700 dark:text-gray-400">
                    Pourcentage de réduction de la promotion:{" "}
                    {product?.promotion.discountPercent}
                  </p>
                )}
                {product?.promotion.promotionType === "OFFER" && (
                  <>
                    <p className="text-gray-700 dark:text-gray-400">
                      Nombre d'articles requis pour profiter de la promotion:{" "}
                      {product?.promotion.requiredItems}
                    </p>
                    <p className="text-gray-700 dark:text-gray-400">
                      Nombre d'articles offerts :{" "}
                      {product?.promotion.offeredItems}
                    </p>
                  </>
                )}
              </>
            )}
          </Card>

          <div className="flex flex-row space-x-4 mt-2">
            <Button onClick={() => navigate(-1)}>Retour</Button>
            <Button onClick={() => console.log("wow")}>Modifier</Button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetails;

// TODO: une promotion devrait avoir un titre pour pouvoir choisir la promotion à appliquer
