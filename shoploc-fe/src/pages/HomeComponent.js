import { useEffect, useState } from "react";
import CommerceCard from "../components/CommerceCard";
import commerceService from "../services/commerce";
import NewsSlider from "../components/NewsSlider";
import Map from "../components/Map";
import CommerceFilters from "../components/CommerceFilters";
import moment from "moment";

function HomeComponent() {
  // const dummyData = require("../utils/commerçants.json");
  const [commercants, setCommercants] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [isOpenNow, setIsOpenNow] = useState(false);
  const [distance, setDistance] = useState(5);
  const token = localStorage.getItem("userToken");
  const cleanedToken = token ? token.replace(/['"]+/g, "") : null;

  console.log(isOpenNow);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await commerceService.fetchCommerces(cleanedToken);
        setCommercants(data);
      } catch (error) {
        console.error("Error in HomeComponent:", error);
      }
    };

    fetchData();
  }, [cleanedToken]);
  console.log(commercants);
  const isOpen = (commercant) => {
    const now = moment(); // Heure actuelle
    const openingTime = moment(commercant.openingHour, "HH:mm:ss");
    const closingTime = moment(commercant.closingHour, "HH:mm:ss");

    return now.isBetween(openingTime, closingTime);
  };

  const filteredCommercants = commercants
    .filter((commercant) =>
      commercant.commerceName.toLowerCase().includes(searchQuery.toLowerCase())
    )
    .filter((commercant) => (!isOpenNow ? commercant : isOpen(commercant)));
  // .filter((commercant) => /* Logique pour vérifier la distance */);

  // console.log(filteredCommercants);
  return (
    <div className="grid md:grid-cols-12 grid-cols-3 md:gap-12 gap-4 my-12 mx-auto">
      <div className="md:col-span-3 col-span-3 relative flex flex-col rounded">
        <NewsSlider />
      </div>
      <div className="md:col-span-9 col-span-3 container px-4 mx-auto by-8">
        <div className="grid md:grid-cols-4 gap-x-4 gap-y-8">
          <div className="md:col-span-4 rounded">
            <Map height="150px" />
          </div>
          <h1 className="md:col-span-4 text-xl font-bold mb-1">
            Les commerces
          </h1>
          <div className="md:col-span-4 mb w-full">
            <input
              type="text"
              placeholder="Rechercher un commerce..."
              className="p-2 border border-gray-300 rounded w-full"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
          </div>
          <div className="md:col-span-4 mb-4 w-full flex flex-1 items-center justify-between">
            <CommerceFilters
              distance={distance}
              onDistanceChange={setDistance}
              onOpenNowChange={setIsOpenNow}
            />
          </div>

          {filteredCommercants.length === 0 ? (
            <p className="md:col-span-4 text-red-500 font-bold">
              Aucun commerce trouvé
            </p>
          ) : (
            filteredCommercants.map((commercant) => (
              <CommerceCard
                commercant={commercant}
                key={commercant.commerceId}
              />
            ))
          )}
        </div>
      </div>
    </div>
  );
}

export default HomeComponent;
