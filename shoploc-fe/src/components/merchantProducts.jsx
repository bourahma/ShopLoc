import { useState } from "react";
import { jwtDecode } from "jwt-decode";
import { Table, TextInput } from "flowbite-react";
import useProducts from "../hooks/useProducts";
import { Link } from "react-router-dom";

const MerchantProducts = () => {
  const [searchQuery, setSearchQuery] = useState("");
  const token = localStorage.getItem("userToken");
  const cleanedToken = JSON.parse(token);

  // Decode the token
  let decoded = jwtDecode(cleanedToken);

  // Get the merchant ID
  let merchantId = decoded.userId;

  const { isLoading, isError, error, data } = useProducts.useMerchantProducts(
    cleanedToken,
    merchantId
  );

  return (
    <div className="overflow-x-auto">
      {isError && <div>Error: {error.message}</div>}
      {isLoading && <div>Loading...</div>}

      <TextInput
        className="w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
        placeholder="Rechercher un produit ..."
        value={searchQuery}
        onChange={(e) => setSearchQuery(e.target.value)}
      ></TextInput>

      <Table hoverable>
        <Table.Head>
          <Table.HeadCell>Nom du produit</Table.HeadCell>
          <Table.HeadCell>Catégorie</Table.HeadCell>
          <Table.HeadCell>Cadeau</Table.HeadCell>
          <Table.HeadCell>Réduction</Table.HeadCell>
          <Table.HeadCell>Prix</Table.HeadCell>
          <Table.HeadCell>
            <span className="sr-only">Détails</span>
          </Table.HeadCell>
        </Table.Head>
        <Table.Body className="divide-y">
          {data
            ?.filter((product) =>
              product.productName
                .toLowerCase()
                .includes(searchQuery.toLowerCase())
            )
            .map((product) => (
              <Table.Row
                className="bg-white dark:border-gray-700 dark:bg-gray-800"
                key={product.productId}
              >
                <Table.Cell className="whitespace-nowrap font-medium text-gray-900 dark:text-white">
                  {product.productName}
                </Table.Cell>
                <Table.Cell>{product.productCategoryLabel}</Table.Cell>
                <Table.Cell>{product.gift ? "Oui" : "Non"}</Table.Cell>
                <Table.Cell>
                  {product.promotion?.discountPercent || 0} %
                </Table.Cell>
                <Table.Cell>{product.price} €</Table.Cell>
                <Table.Cell>
                  <Link
                    className="font-medium text-cyan-600 hover:underline dark:text-cyan-500"
                    to={`/merchant/home/productDetails/${product.productId}`}
                  >
                    Détails
                  </Link>
                </Table.Cell>
              </Table.Row>
            ))}
        </Table.Body>
      </Table>
    </div>
  );
};

export default MerchantProducts;
