import { Card } from "flowbite-react";
import { useNavigate } from "react-router-dom";

const CommerceCard = ({commercant}) => {
    const navigate = useNavigate();

    const handleCardClick = (commercantId) => {
        navigate(`/commercant/${commercantId}`);
    };

    return (
        <Card
            key={commercant.id}
            className="max-w-sm cursor-pointer"
            imgAlt={`commerce ${commercant.id}`}
            imgSrc={require(`../images/${commercant.image}`)}
            onClick={() => handleCardClick(commercant.id)}
        >
            <h5 className="text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                {commercant.nom}
            </h5>
            <p className="font-normal text-gray-700 dark:text-gray-400">
                {commercant.description}
            </p>
        </Card>
    );
};

export default CommerceCard;
