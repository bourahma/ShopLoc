import { Link } from "react-router-dom";
import openingHour from "../images/openHour.png";
import closingHour from "../images/closeHour.png";

const CommerceCard = ({ commercant }) => {
    const formatTime = (time) => {
        const [hours, minutes] = time.split(":");
        return `${hours}:${minutes}`;
    };

    return (
        <Link
            to={`/commercant/${commercant.commerceId}`}
            className="max-w-sm rounded border shadow-sm overflow-hidden cursor-pointer"
        >
            <img
                className="h-48 w-full object-cover "
                src={commercant.imageUrl}
                alt={commercant.imageUrl}
                width="100"
            />
            <div className="px-2 pt-4">
                <div className="font-bold text-xl mb-2">
                    {commercant.commerceName}
                </div>
            </div>
            <div className="px-2">
                <div className="flex items-center space-x-2 mt-1">
                    <img
                        src={openingHour}
                        alt="openingHour"
                        className="h-10 w-10"
                    />
                    <span className="text-gray-700 ">
                        Ouverture: {formatTime(commercant.openingHour)}
                    </span>
                </div>
                <div className="flex items-center space-x-2 mt-1">
                    <img
                        src={closingHour}
                        alt="closingHour"
                        className="h-10 w-10"
                    />
                    <span className="text-gray-700 ">
                        {" "}
                        Fermeture: {formatTime(commercant.closingHour)}
                    </span>
                </div>
            </div>
        </Link>
    );
};

export default CommerceCard;
