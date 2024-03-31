import { useQuery } from "@tanstack/react-query";
import commerceService from "../services/commerce";

const useCommerceId = (token, merchantId) => {
  return useQuery({
    queryKey: ["commerceId", merchantId, token],
    queryFn: () => commerceService.getCommerceId(token, merchantId),
    enabled: !!merchantId,
  });
};

const useCommerces = {
  useCommerceId,
};

export default useCommerces;
