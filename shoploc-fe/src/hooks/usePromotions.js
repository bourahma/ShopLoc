import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import promotionService from "../services/promotion";

const useOffers = (token, commerceId) => {
  return useQuery({
    queryKey: ["offers", commerceId, token],
    queryFn: () => promotionService.getOffers(token, commerceId),
    enabled: !!commerceId && !!token,
  });
};

const useDiscounts = (token, commerceId) => {
  return useQuery({
    queryKey: ["discounts", commerceId, token],
    queryFn: () => promotionService.getDiscounts(token, commerceId),
    enabled: !!commerceId && !!token,
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

const usePromotions = {
  useOffers,
  useDiscounts,
  useCreateOffer,
  useCreateDiscount,
};

export default usePromotions;
