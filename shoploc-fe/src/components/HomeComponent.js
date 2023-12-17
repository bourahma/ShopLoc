import { useEffect, useState } from "react";
import CommerceCard from "./CommerceCard";
import fetchCommercants from "../services/fetchCommerces";
import NewsSlider from "./NewsSlider";
import Map from "./Map";

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

        fetchData();
    }, [token]);
    console.log(commercants)
    return (
        <div className="grid md:grid-cols-12 grid-cols-3 md:gap-12 gap-4 my-12 mx-auto">
            <div className="md:col-span-3 col-span-3 relative flex flex-col rounded">
                <NewsSlider />
            </div>
            <div className="md:col-span-9 col-span-3 container mx-auto by-8">
                <div className="grid md:grid-cols-4 gap-4">
                    <div className="md:col-span-4 rounded">
                        <Map height="150px" />
                    </div>
                    <h1 className="md:col-span-4 text-xl font-bold mb-3">
                        Les commerces
                    </h1>
                    {dummyData.commercants.map((commercant) => (
                        <CommerceCard
                            commercant={commercant}
                            key={commercant.id}
                        />
                    ))}
                </div>
            </div>
        </div>
    );
}

export default HomeComponent;
