import { useEffect, useState } from "react";
import CommerceCard from "./CommerceCard";
import fetchCommercants from "../services/fetchCommerces";

function HomeComponent() {
  const dummyData = require("../utils/commerçants.json");
  const [commercants, setCommercants] = useState([]);
  const token = localStorage.getItem("userToken");
  const cleanedToken = token ? token.replace(/['"]+/g, "") : null;
  console.log(cleanedToken);
  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await fetchCommercants(cleanedToken);
        setCommercants(data);
      } catch (error) {
        console.error("Error in HomeComponent:", error);
      }
    };

    fetchData().then((r) => console.log(r));
  }, [token, cleanedToken]);
  console.log(commercants);
  return (
    <div>
      <div className="container mx-auto my-8">
        <div className="grid md:grid-cols-3 gap-4">
          {dummyData.commercants.map((commercant) => (
            <CommerceCard commercant={commercant} key={commercant.id} />
          ))}
        </div>
      </div>
    </div>
  );
}

export default HomeComponent;
