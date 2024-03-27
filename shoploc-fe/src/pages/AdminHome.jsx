import React, { useState } from "react";
import { Sidebar } from "flowbite-react";
import { HiArrowSmRight } from "react-icons/hi";
import CommerceRegistrationForm from "../components/RegisterCommerce";
import MerchantRegistrationForm from "../components/RegisterMerchant";

const AdminHome = () => {
  const [task, setTask] = useState("createCommerce");

  const handleLaunchPromo = () => {
    // Logic for launching promos
  };

  return (
    <div className="flex flex-col sm:flex-row">
      <Sidebar
        aria-label="Main navigation"
        className="bg-gray-800 text-gray-400 shadow-lg flex-none"
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
          </Sidebar.ItemGroup>
        </Sidebar.Items>
      </Sidebar>

      <div className="flex-grow">
        {task === "createCommerce" && <CommerceRegistrationForm />}
        {task === "createMerchant" && <MerchantRegistrationForm />}
        {task === "launchPromo" && (
          <div>
            <h1>Lancer une promotion</h1>
            <button onClick={handleLaunchPromo}>Lancer</button>
          </div>
        )}
      </div>
    </div>
  );
};

export default AdminHome;
