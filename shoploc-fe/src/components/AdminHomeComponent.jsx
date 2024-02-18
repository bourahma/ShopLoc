import React from "react";
import { Sidebar } from "flowbite-react";
import { HiArrowSmRight } from "react-icons/hi";

const AdminHomeComponent = () => {
  const handleCreateMerchant = () => {
    // Logic for creating merchants
  };

  const handleLaunchPromo = () => {
    // Logic for launching promos
  };

  return (
    <Sidebar
      aria-label="Main navigation"
      className="bg-gray-800 text-gray-400 flex-grow"
    >
      <Sidebar.Items>
        <Sidebar.ItemGroup>
          <Sidebar.Item href="#" icon={HiArrowSmRight}>
            Admin Home
          </Sidebar.Item>
          <Sidebar.Item href="#" icon={HiArrowSmRight}>
            Create Merchants
          </Sidebar.Item>
          <Sidebar.Item href="#" icon={HiArrowSmRight}>
            Launch Promo
          </Sidebar.Item>
        </Sidebar.ItemGroup>
      </Sidebar.Items>
    </Sidebar>
  );
};

export default AdminHomeComponent;
