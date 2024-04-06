import React, { useState } from "react";
import { Sidebar } from "flowbite-react";
import { HiArrowSmRight } from "react-icons/hi";
import { Outlet, Link } from "react-router-dom";

const AdminHome = () => {
  const [task, setTask] = useState("createCommerce");

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
              as={Link}
              to="/admin/home/"
              icon={HiArrowSmRight}
              className={`hover:bg-gray-700 hover:cursor-pointer hover:text-white ${
                task === "createCommerce" ? "bg-gray-700 text-white" : ""
              }`}
            >
              Créer un commerce
            </Sidebar.Item>
            <Sidebar.Item
              onClick={() => setTask("createMerchant")}
              as={Link}
              to="/admin/home/create-merchant"
              icon={HiArrowSmRight}
              className={`hover:bg-gray-700 hover:cursor-pointer hover:text-white ${
                task === "createMerchant" ? "bg-gray-700 text-white" : ""
              }`}
            >
              Créer un commerçant
            </Sidebar.Item>
            <Sidebar.Item
              onClick={() => setTask("launchPromo")}
              as={Link}
              to="/admin/home/launch-promo"
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
        <Outlet />
      </div>
    </div>
  );
};

export default AdminHome;
