import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import promotionService from "../services/promotion";

const useCommercePromotions = (token, commerceId) => {
  return useQuery({
    queryKey: ["commercePromotions", commerceId, token],
    queryFn: () => promotionService.getCommercePromotions(token, commerceId),
    enabled: !!commerceId && !!token,
  });
};

const useAllPromotions = (token) => {
  return useQuery({
    queryKey: ["promotions", token],
    queryFn: () => promotionService.getAllPromotions(token),
    enabled: !!token,
  });
};

const useCreateOffer = (token, commerceId) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (offer) => promotionService.addOffer(token, offer, commerceId),
    onSuccess: () => {
      queryClient.invalidateQueries("offers");
    },
  });
};

const useCreateDiscount = (token, commerceId) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (discount) =>
      promotionService.addDiscount(token, discount, commerceId),
    onSuccess: () => {
      queryClient.invalidateQueries("discounts");
    },
  });
};

const usePromotionDetails = (token, promotionId) => {
  return useQuery({
    queryKey: ["promotionDetails", promotionId, token],
    queryFn: () => promotionService.getPromotionDetails(token, promotionId),
    enabled: !!promotionId && !!token,
  });
};

const usePromotions = {
  useCreateOffer,
  useCreateDiscount,
  useCommercePromotions,
  useAllPromotions,
  usePromotionDetails,
};

export default usePromotions;
