import React, { useState } from "react";

const AddPromotion = () => {
  const [promotion, setPromotion] = useState({
    productName: "",
    discount: "",
    startDate: "",
    endDate: "",
  });

  const handleChange = (e) => {
    setPromotion({ ...promotion, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Add your logic to submit the form and save the promotion
    console.log(promotion);
  };

  return (
    <div>
      <h2>Add Promotion</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Product Name:
          <input
            type="text"
            name="productName"
            value={promotion.productName}
            onChange={handleChange}
          />
        </label>
        <br />
        <label>
          Discount:
          <input
            type="text"
            name="discount"
            value={promotion.discount}
            onChange={handleChange}
          />
        </label>
        <br />
        <label>
          Start Date:
          <input
            type="date"
            name="startDate"
            value={promotion.startDate}
            onChange={handleChange}
          />
        </label>
        <br />
        <label>
          End Date:
          <input
            type="date"
            name="endDate"
            value={promotion.endDate}
            onChange={handleChange}
          />
        </label>
        <br />
        <button type="submit">Add Promotion</button>
      </form>
    </div>
  );
};

export default AddPromotion;
