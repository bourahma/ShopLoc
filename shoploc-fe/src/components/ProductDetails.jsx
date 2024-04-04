import { Card, Button } from "flowbite-react";
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

  const cleanedToken = JSON.parse(token);

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
              <b>{product?.price} € </b>
            </p>
            <p className="text-gray-700 dark:text-gray-400">
              <b>{product?.quantity}</b> produits en stock
            </p>
            <p className="text-gray-700 dark:text-gray-400">
              <b>{product?.rewardPointsPrice}</b> points de fidélité
            </p>
            <p className="text-gray-700 dark:text-gray-400">
              Cadeau: <b>{product?.gift ? "Oui" : "Non"}</b>
            </p>
            <p className="text-gray-700 dark:text-gray-400">
              Catégorie: <b>{product?.productCategoryLabel}</b>
            </p>
            {product?.promotion && (
              <>
                <p className="text-gray-700 dark:text-gray-400">
                  Début promotion: <b>{product?.promotion.startDate}</b>
                </p>
                <p className="text-gray-700 dark:text-gray-400">
                  Fin promotion: <b>{product?.promotion.endDate}</b>
                </p>
                <p className="text-gray-700 dark:text-gray-400">
                  {product?.promotion.description}
                </p>
                {product?.promotion.promotionType === "DISCOUNT" && (
                  <p className="text-gray-700 dark:text-gray-400">
                    <b>{product?.promotion.discountPercent}</b> % de réduction
                  </p>
                )}
                {product?.promotion.promotionType === "OFFER" && (
                  <>
                    <p className="text-gray-700 dark:text-gray-400">
                      <b>{product?.promotion.requiredItems}</b> produits achetés
                    </p>
                    <p className="text-gray-700 dark:text-gray-400">
                      <b>{product?.promotion.offeredItems}</b>{" "}
                      {product?.promotion.offeredItems > 1
                        ? "produits offerts"
                        : "produit offert"}
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
