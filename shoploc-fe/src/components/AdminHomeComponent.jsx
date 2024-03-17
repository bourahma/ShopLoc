import React, { useState } from "react";
import { Sidebar } from "flowbite-react";
import { HiArrowSmRight, HiUser, HiViewGrid } from "react-icons/hi";
import CommerceRegistrationForm from "./RegisterCommerce";
import MerchantRegistrationForm from "./RegisterMerchant";

const AdminHomeComponent = () => {
  const [task, setTask] = useState("createCommerce");

  const handleLaunchPromo = () => {
    // Logic for launching promos
  };

  return (
    <div className="flex flex-wrap">
      <Sidebar
        aria-label="Main navigation"
        className="bg-gray-800 text-gray-400 shadow-lg"
      >
        <Sidebar.Items>
          <Sidebar.ItemGroup>
            <Sidebar.Item
              onClick={() => setTask("createCommerce")}
              icon={HiArrowSmRight}
              className={`hover:bg-gray-700 hover:cursor-pointer hover:text-white ${
                task === "createCommerce" ? "bg-gray-700 text-white" : ""
              }`}
            >
              Créer un commerce
            </Sidebar.Item>
            <Sidebar.Item
              onClick={() => setTask("createMerchant")}
              icon={HiArrowSmRight}
              className={`hover:bg-gray-700 hover:cursor-pointer hover:text-white ${
                task === "createMerchant" ? "bg-gray-700 text-white" : ""
              }`}
            >
              Créer un commerçant
            </Sidebar.Item>
            <Sidebar.Item
              onClick={() => setTask("launchPromo")}
              icon={HiArrowSmRight}
              className={`hover:bg-gray-700 hover:cursor-pointer hover:text-white ${
                task === "launchPromo" ? "bg-gray-700 text-white" : ""
              }`}
            >
              Lancer une promotion
            </Sidebar.Item>
            <Sidebar.Item
              onClick={() => setTask("viewMerchants")}
              icon={HiUser}
              className={`hover:bg-gray-700 hover:cursor-pointer hover:text-white ${
                task === "viewMerchants" ? "bg-gray-700 text-white" : ""
              }`}
            >
              Commerçants
            </Sidebar.Item>
            <Sidebar.Item
              onClick={() => setTask("viewCommerces")}
              icon={HiViewGrid}
              className={`hover:bg-gray-700 hover:cursor-pointer hover:text-white ${
                task === "viewCommerces" ? "bg-gray-700 text-white" : ""
              }`}
            >
              Commerces
            </Sidebar.Item>
          </Sidebar.ItemGroup>
        </Sidebar.Items>
      </Sidebar>

      {task === "createCommerce" && <CommerceRegistrationForm />}

      {task === "createMerchant" && <MerchantRegistrationForm />}
      {task === "launchPromo" && (
        <div>
          <h1>Lancer une promotion</h1>
          <button onClick={handleLaunchPromo}>Lancer</button>
        </div>
      )}

      {task === "viewMerchants" && (
        <div>
          <h1>Commerçants</h1>
        </div>
      )}
      {task === "viewCommerces" && (
        <div>
          <h1>Commerces</h1>
        </div>
      )}
    </div>
  );
};

export default AdminHomeComponent;
