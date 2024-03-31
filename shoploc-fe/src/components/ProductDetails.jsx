import { Card } from "flowbite-react";
import { useParams } from "react-router-dom";
import { useState } from "react";
import useProducts from "../hooks/useProducts";

const ProductDetails = () => {
  const [error, setError] = useState(null);

  const { productId } = useParams();
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
  }

  return (
    <div>
      {error && <div>Error: {error.message}</div>}
      <div>
        <Card>
          <div>
            <h1>{product?.productName}</h1>
            <p>Description: {product?.description}</p>
            <p>Prix: {product?.price}</p>
            <p>Quantité: {product?.quantity}</p>
            <p>Prix en points de récompense: {product?.rewardPointsPrice}</p>
            <p>Cadeau: {product?.gift ? "Oui" : "Non"}</p>
            <p>Catégorie de produit: {product?.productCategoryLabel}</p>
            {product?.promotion && (
              <>
                <p>
                  Date de début de la promotion: {product?.promotion.startDate}
                </p>
                <p>Date de fin de la promotion: {product?.promotion.endDate}</p>
                <p>
                  Description de la promotion: {product?.promotion.description}
                </p>
                <p>
                  Pourcentage de réduction de la promotion:{" "}
                  {product?.promotion.discountPercent}
                </p>
                <p>Type de promotion: {product?.promotion.promotionType}</p>
                <p>
                  Articles requis pour la promotion:{" "}
                  {product?.promotion.requiredItems}
                </p>
                <p>
                  Articles offerts dans la promotion:{" "}
                  {product?.promotion.offeredItems}
                </p>
              </>
            )}
          </div>
        </Card>
      </div>
    </div>
  );
};

export default ProductDetails;
