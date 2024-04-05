import { useState } from "react";
import { Table, TextInput } from "flowbite-react";
import { Link } from "react-router-dom";
import usePromotions from "../hooks/usePromotions";

const LaunchPromo = () => {
  const [searchQuery, setSearchQuery] = useState("");
  const token = localStorage.getItem("userToken");
  const cleanedToken = JSON.parse(token);

  const { data, isLoading, isError, Error } =
    usePromotions.useAllPromotions(cleanedToken);

  console.log(data);

  return (
    <div className="overflow-x-auto">
      {isError && <div>Error: {Error.message}</div>}
      {isLoading && <div>Loading...</div>}

      <TextInput
        className="w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
        placeholder="Rechercher un produit ..."
        value={searchQuery}
        onChange={(e) => setSearchQuery(e.target.value)}
      ></TextInput>

      <Table hoverable>
        <Table.Head>
          <Table.HeadCell>Label</Table.HeadCell>
          <Table.HeadCell>Type</Table.HeadCell>
          <Table.HeadCell>Début</Table.HeadCell>
          <Table.HeadCell>Fin</Table.HeadCell>
          <Table.HeadCell>
            <span className="sr-only">Détails</span>
          </Table.HeadCell>
        </Table.Head>
        <Table.Body className="divide-y">
          {data
            ?.filter((promotion) =>
              promotion.label?.toLowerCase().includes(searchQuery.toLowerCase())
            )
            .filter((promotion) => promotion.sent === false)
            .map((promotion) => (
              <Table.Row
                className="bg-white dark:border-gray-700 dark:bg-gray-800"
                key={promotion.promotionId}
              >
                <Table.Cell className="whitespace-nowrap font-medium text-gray-900 dark:text-white">
                  {promotion.label}
                </Table.Cell>
                <Table.Cell>
                  {promotion.promotionType === "OFFER" ? "Offre" : "Réduction"}
                </Table.Cell>
                <Table.Cell>
                  {new Date(promotion.startDate).toLocaleDateString(
                    ("fr",
                    {
                      weekday: "long",
                      year: "numeric",
                      month: "long",
                      day: "numeric",
                    })
                  )}
                </Table.Cell>
                <Table.Cell>
                  {new Date(promotion.endDate).toLocaleDateString(
                    ("fr",
                    {
                      weekday: "long",
                      year: "numeric",
                      month: "long",
                      day: "numeric",
                    })
                  )}
                </Table.Cell>
                <Table.Cell>
                  <Link
                    className="font-medium text-cyan-600 hover:underline dark:text-cyan-500"
                    to={`/admin/home/promo-details/${promotion.promotionId}`}
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

export default LaunchPromo;
