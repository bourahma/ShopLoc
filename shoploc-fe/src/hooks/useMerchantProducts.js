import { useQuery } from "@tanstack/react-query";
import commerceService from "../services/commerce";

const useMerchantProducts = (token, merchantId) => {
  return useQuery({
    queryKey: ["merchantProducts", merchantId],
    queryFn: () => commerceService.fetchMerchantProducts(token, merchantId),
    enabled: !!merchantId,
  });
};

export default useMerchantProducts;
