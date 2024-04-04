import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import commerceService from "../services/commerce";
import productServices from "../services/product";

const useMerchantProducts = (token, merchantId) => {
  return useQuery({
    queryKey: ["merchantProducts", merchantId, token],
    queryFn: () => commerceService.fetchMerchantProducts(token, merchantId),
    enabled: !!token && !!merchantId,
  });
};

const useProductsCategories = (token, commerceId) => {
  return useQuery({
    queryKey: ["productsCategories", commerceId, token],
    queryFn: () => productServices.getProductsCategories(token, commerceId),
    enabled: !!commerceId && !!token,
  });
};

const useCreateProductCategory = (token, commerceId) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (category) =>
      productServices.addProductCategory(token, commerceId, category),
    onSuccess: () => {
      queryClient.invalidateQueries("productsCategories");
    },
  });
};

const useProductDetails = (token, productId) => {
  return useQuery({
    queryKey: ["productDetails", productId, token],
    queryFn: () => productServices.getProductDetails(token, productId),
    enabled: !!productId,
  });
};

const useProducts = {
  useMerchantProducts,
  useProductsCategories,
  useCreateProductCategory,
  useProductDetails,
};

export default useProducts;
