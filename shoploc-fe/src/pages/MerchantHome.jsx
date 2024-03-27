import React, { useState } from "react";
import { Sidebar } from "flowbite-react";
import { HiArrowSmRight } from "react-icons/hi";
import AddProduct from "../components/AddProduct";
import MerchantProducts from "../components/merchantProducts";
import AddPromotion from "../components/AddPromotion";
import AddCategory from "../components/AddCategory";

const MerchantHome = () => {
  const [task, setTask] = useState("produits");

  return (
    <div className="flex flex-wrap flex-row">
      <Sidebar
        aria-label="Main navigation"
        className="bg-gray-800 text-gray-400 shadow-lg"
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
      <div className="flex-1 p-4">
        {task === "produits" && <MerchantProducts />}
        {task === "ajouterProduit" && <AddProduct />}
        {task === "promotion" && <AddPromotion />}
        {task === "addCategory" && <AddCategory />}
      </div>
    </div>
  );
};

export default MerchantHome;
