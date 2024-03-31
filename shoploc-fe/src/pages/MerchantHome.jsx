import React, { useState } from "react";
import { Sidebar } from "flowbite-react";
import { HiArrowSmRight } from "react-icons/hi";
import AddProduct from "../components/AddProduct";
import MerchantProducts from "../components/merchantProducts";
import AddPromotion from "../components/AddPromotion";
import AddCategory from "../components/AddCategory";
import useCommerces from "../hooks/useCommerces";
import { jwtDecode } from "jwt-decode";

const MerchantHome = () => {
  const [task, setTask] = useState("produits");
  const [error, setError] = useState(null);

  const token = localStorage.getItem("userToken");
  const cleanedToken = token ? token.replace(/['"]+/g, "") : null;

  const merchantId = jwtDecode(cleanedToken).userId;

  const commerceIdResponse = useCommerces.useCommerceId(
    cleanedToken,
    merchantId
  );

  if (commerceIdResponse.isError) {
    setError(commerceIdResponse.error);
    console.log("Error fetching commerceId", commerceIdResponse.error);
  }

  if (commerceIdResponse.isLoading) {
    return <div>Loading...</div>;
  }

  if (commerceIdResponse.isSuccess) {
    console.log("commerceId", commerceIdResponse.data);
  }

  if (error) {
    setTimeout(() => {
      setError(null);
    }, 5000);
  }

  return (
    <div className="flex flex-col sm:flex-row">
      {error && <div>Error: {error.message}</div>}

      <Sidebar
        aria-label="Main navigation"
        className="bg-gray-800 text-gray-400 shadow-lg flex-none"
      >
        <Sidebar.Items>
          <Sidebar.ItemGroup>
            <Sidebar.Item
              onClick={() => setTask("produits")}
              icon={HiArrowSmRight}
              className={`hover:bg-gray-700 hover:cursor-pointer hover:text-white ${
                task === "produits" ? "bg-gray-700 text-white" : ""
              }`}
            >
              Produits
            </Sidebar.Item>

            <Sidebar.Item
              onClick={() => setTask("ajouterProduit")}
              icon={HiArrowSmRight}
              className={`hover:bg-gray-700 hover:cursor-pointer hover:text-white ${
                task === "ajouterProduit" ? "bg-gray-700 text-white" : ""
              }`}
            >
              Ajouter un produit
            </Sidebar.Item>
            <Sidebar.Item
              onClick={() => setTask("promotion")}
              icon={HiArrowSmRight}
              className={`hover:bg-gray-700 hover:cursor-pointer hover:text-white ${
                task === "promotion" ? "bg-gray-700 text-white" : ""
              }`}
            >
              Ajouter une promotion
            </Sidebar.Item>
            <Sidebar.Item
              onClick={() => setTask("addCategory")}
              icon={HiArrowSmRight}
              className={`hover:bg-gray-700 hover:cursor-pointer hover:text-white ${
                task === "addCategory" ? "bg-gray-700 text-white" : ""
              }`}
            >
              Ajouter une catégorie
            </Sidebar.Item>
          </Sidebar.ItemGroup>
        </Sidebar.Items>
      </Sidebar>
      <div className="flex-grow">
        {task === "produits" && <MerchantProducts />}
        {task === "ajouterProduit" && commerceIdResponse.isSuccess && (
          <AddProduct commerceId={commerceIdResponse.data} />
        )}
        {task === "promotion" && commerceIdResponse.isSuccess && (
          <AddPromotion commerceId={commerceIdResponse.data} />
        )}
        {task === "addCategory" && commerceIdResponse.isSuccess && (
          <AddCategory commerceId={commerceIdResponse.data} />
        )}
      </div>
    </div>
  );
};

export default MerchantHome;
