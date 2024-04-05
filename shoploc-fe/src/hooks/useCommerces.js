import { useQuery } from "@tanstack/react-query";
import commerceService from "../services/commerce";

const useCommerceId = (token, merchantId) => {
  return useQuery({
    queryKey: ["commerceId", merchantId, token],
    queryFn: () => commerceService.getCommerceId(token, merchantId),
    enabled: !!merchantId && !!token,
  });
};

const useCommerceDetails = (token, commerceId) => {
  return useQuery({
    queryKey: ["commerceDetails", commerceId, token],
    queryFn: () => commerceService.fetchCommerce(token, commerceId),
    enabled: !!commerceId && !!token,
  });
};

const useCommerces = {
  useCommerceId,
  useCommerceDetails,
};

export default useCommerces;

// TODO: Les tokens ont une durée de vie de 2h ce qui n'est pas bon si la personne
