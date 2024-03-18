import React, { useState } from "react";
import { Sidebar } from "flowbite-react";
import { HiArrowSmRight } from "react-icons/hi";
import AddProduct from "./AddProduct";
import MerchantProducts from "./merchantProducts";

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
              onClick={() => setTask("cadeaux")}
              icon={HiArrowSmRight}
              className={`hover:bg-gray-700 hover:cursor-pointer hover:text-white ${
                task === "cadeaux" ? "bg-gray-700 text-white" : ""
              }`}
            >
              Cadeaux
            </Sidebar.Item>
            <Sidebar.Item
              onClick={() => setTask("commandes")}
              icon={HiArrowSmRight}
              className={`hover:bg-gray-700 hover:cursor-pointer hover:text-white ${
                task === "commandes" ? "bg-gray-700 text-white" : ""
              }`}
            >
              Commandes du jour
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
          </Sidebar.ItemGroup>
        </Sidebar.Items>
      </Sidebar>
      <div className="flex-1 p-4">
        {task === "produits" && <MerchantProducts />}

        {task === "cadeaux" && (
          <div>
            <h1>Cadeaux</h1>
          </div>
        )}
        {task === "commandes" && (
          <div>
            <h1>Commandes du jour</h1>
          </div>
        )}
        {task === "ajouterProduit" && <AddProduct />}
      </div>
    </div>
  );
};

export default MerchantHome;
