import React from "react";

const AdminHomeComponent = () => {
  const handleCreateMerchant = () => {
    // Logic for creating merchants
  };

  const handleLaunchPromo = () => {
    // Logic for launching promos
  };

  return (
    <div>
      <h1>Welcome to the Admin Home</h1>
      <button onClick={handleCreateMerchant}>Create Merchant</button>
      <button onClick={handleLaunchPromo}>Launch Promo</button>
    </div>
  );
};

export default AdminHomeComponent;
