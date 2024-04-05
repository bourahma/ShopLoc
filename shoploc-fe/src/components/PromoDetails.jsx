import { Card, Button } from "flowbite-react";
import { useParams, useNavigate } from "react-router-dom";
import { useState } from "react";
import usePromotions from "../hooks/usePromotions";
import useCommerces from "../hooks/useCommerces";
import promotionService from "../services/promotion";

const PromoDetails = () => {
  const [error, setError] = useState(null);

  const navigate = useNavigate();
  const { promotionId } = useParams();

  const token = localStorage.getItem("userToken");
  const cleanedToken = JSON.parse(token);

  const promotionQuery = usePromotions.usePromotionDetails(
    cleanedToken,
    promotionId
  );

  const commerceQuery = useCommerces.useCommerceDetails(
    cleanedToken,
    promotionQuery.data?.commerceId
  );

  if (promotionQuery.isError) {
    setError(promotionQuery.error);
    console.log("Error fetching promotion", promotionQuery.error);
  }

  if (promotionQuery.isLoading) {
    return <div>Loading...</div>;
  }

  let promotion = null;
  if (promotionQuery.isSuccess) {
    promotion = promotionQuery.data;
    console.log("promotion", promotion);
  }

  const launchPromo = async (promotionId) => {
    try {
      await promotionService.launchPromotion(cleanedToken, promotionId);
      navigate(-1);
    } catch (error) {
      setError(error);
    }
  };

  if (error) {
    setTimeout(() => {
      setError(null);
    }, 5000);
  }

  return (
    <div>
      {error && (
        <div
          className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative"
          role="alert"
        >
          Error: {error.message}
        </div>
      )}
      <div className="flex flex-col my-6 mx-12 md:flex-row md:space-x-4">
        <div className="flex-1">
          <Card className="max-w-sm" horizontal>
            <h5 className="text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
              {promotion?.label}
            </h5>

            <p className="text-gray-700 dark:text-gray-400">
              Commerce: <b>{commerceQuery.data?.commerceName}</b>
            </p>

            <p className="text-gray-700 dark:text-gray-400">
              Début promotion:{" "}
              <b>
                {new Date(promotion?.startDate).toLocaleDateString("fr", {
                  weekday: "long",
                  year: "numeric",
                  month: "long",
                  day: "numeric",
                })}
              </b>
            </p>
            <p className="text-gray-700 dark:text-gray-400">
              Fin promotion:{" "}
              <b>
                {new Date(promotion?.endDate).toLocaleDateString("fr", {
                  weekday: "long",
                  year: "numeric",
                  month: "long",
                  day: "numeric",
                })}
              </b>
            </p>
            <p className="text-gray-700 dark:text-gray-400">
              {promotion?.description}
            </p>
            {promotion?.promotionType === "DISCOUNT" && (
              <p className="text-gray-700 dark:text-gray-400">
                <b>{promotion?.discountPercent}</b> % de réduction
              </p>
            )}
            {promotion?.promotionType === "OFFER" && (
              <>
                <p className="text-gray-700 dark:text-gray-400">
                  <b>{promotion?.requiredItems}</b> produits achetés
                </p>
                <p className="text-gray-700 dark:text-gray-400">
                  <b>{promotion?.offeredItems}</b>{" "}
                  {promotion?.offeredItems > 1
                    ? "produits offerts"
                    : "produit offert"}
                </p>
              </>
            )}
          </Card>

          <div className="flex flex-row space-x-4 mt-2">
            <Button onClick={() => navigate(-1)}>Retour</Button>
            <Button onClick={() => launchPromo(promotion?.promotionId)}>
              Envoyer la promotion
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PromoDetails;
