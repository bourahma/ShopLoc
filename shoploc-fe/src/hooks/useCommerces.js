import fetchCommerces from "../services/commerceService";
import { useQuery } from "@tanstack/react-query";

const useConcert = (id) => {
  return useQuery({
    queryKey: ["concerts", id],
    queryFn: () => concertService.getConcertById(id),
    enabled: !!id,
  });
};

export default useConcert;
