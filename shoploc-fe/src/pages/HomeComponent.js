import { useEffect, useState } from "react";
import CommerceCard from "../components/CommerceCard";
import commerceService from "../services/commerce";
import NewsSlider from "../components/NewsSlider";
import Map from "../components/Map";
import CommerceFilters from "../components/CommerceFilters";
import fetchCommerceTypes from "../services/commerceTypesService";
import moment from "moment";

function HomeComponent() {
    const [commercants, setCommercants] = useState([]);
    const [searchQuery, setSearchQuery] = useState("");
    const [isOpenNow, setIsOpenNow] = useState(false);
    const [distance, setDistance] = useState(5);
    const [commerceTypes, setCommerceTypes] = useState([]);
    const [selectedType, setSelectedType] = useState("");
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

        fetchCommerceTypes(cleanedToken)
            .then((data) => {
                console.log(data);
                setCommerceTypes(data);
            })
            .catch((error) => {
                console.error("Error fetching commerce types: ", error);
            });
    }, [cleanedToken]);
    console.log(commerceTypes);

    const isOpen = (commercant) => {
        const now = moment(); // Heure actuelle
        const openingTime = moment(commercant.openingHour, "HH:mm:ss");
        const closingTime = moment(commercant.closingHour, "HH:mm:ss");

        return now.isBetween(openingTime, closingTime);
    };

    const handleTypeChange = (e) => {
        setSelectedType(e.target.value);
    };

    const filteredCommercants = commercants
        .filter((commercant) =>
            commercant.commerceName
                .toLowerCase()
                .includes(searchQuery.toLowerCase())
        )
        .filter((commercant) => (!isOpenNow ? commercant : isOpen(commercant)))
        .filter(
            (commercant) =>
                selectedType === "" ||
                commercant.commerceType.label === selectedType
        );
    // .filter((commercant) => /* Logique pour vérifier la distance */);

    // console.log(filteredCommercants);
    return (
        <div className="grid md:grid-cols-12 grid-cols-3 md:gap-6 gap-4 my-12 mx-auto">
            <div className="md:col-span-4 col-span-3 relative flex flex-col rounded">
                <div className="rounded mb-3 pl-2">
                    <Map height="400px" commerces={commercants} />
                </div>
                <NewsSlider />
            </div>
            <div className="md:col-span-8 col-span-3 container px-4 mx-auto by-8">
                <div className="grid md:grid-cols-4 gap-x-4 gap-y-4">
                    <div className="md:col-span-4 w-full flex items-center justify-between gap-4 ">
                        <div className="md:col-span-4 w-full flex flex-1 items-center justify-between">
                            <select
                                className="p-2 border border-shopred rounded bg-shopgray"
                                value={selectedType}
                                onChange={handleTypeChange}
                            >
                                <option value="">
                                    Tous les types de commerces
                                </option>
                                {commerceTypes.map((type) => (
                                    <option
                                        key={type.commerceTypeId}
                                        value={type.label}
                                    >
                                        {type.label}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <input
                            type="text"
                            placeholder="Rechercher un commerce..."
                            className="px-2 border border-shopred rounded w-full bg-shopgray"
                            value={searchQuery}
                            onChange={(e) => setSearchQuery(e.target.value)}
                        />
                    </div>
                    <div className="md:col-span-4 mb-2 w-full flex flex-1 items-center justify-between">
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
