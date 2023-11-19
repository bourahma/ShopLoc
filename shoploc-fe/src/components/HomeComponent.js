import { useNavigate } from "react-router-dom";
import { Card } from "flowbite-react";
import Auchan from "../images/auchan.png";
import Planetbain from "../images/planetbain.jpg";
import LeRoyMerlin from "../images/le roy merlin.jpeg";

function HomeComponent() {
    const navigate = useNavigate();
    const dummyData = require("../utils/commerçants.json"); 

    const handleCardClick = (commercantId) => {
        navigate(`/commercant/${commercantId}`);
    };
    return (
        <div>
            <div className="container mx-auto mt-8">
                <h1 className="text-3xl font-semibold mb-4">Merchants</h1>
                <div className="grid grid-cols-3 gap-4">
                    {dummyData.commercants.map((commercant) => (
                        <Card
                            key={commercant.id}
                            className="max-w-sm cursor-pointer"
                            imgAlt="Meaningful alt text for an image that is not purely decorative"
                            imgSrc={
                                require(`../images/${commercant.image}`)
                            }
                            onClick={() => handleCardClick(commercant.id)}
                        >
                            <h5 className="text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                                {commercant.nom}
                            </h5>
                            <p className="font-normal text-gray-700 dark:text-gray-400">
                                {commercant.description}
                            </p>
                        </Card>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default HomeComponent;
