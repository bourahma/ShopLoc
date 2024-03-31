import React from "react";
import { FaStar, FaStarHalf } from "react-icons/fa";


const RatingStars = () => {
    return (
        <div className="flex gap-1 px-3 py-1">
            <FaStar className="text-yellow-500 text-4xl" />
            <FaStar className="text-yellow-500 text-4xl" />
            <FaStar className="text-yellow-500 text-4xl" />
            <FaStar className="text-yellow-500 text-4xl" />
            <FaStarHalf className="text-yellow-500 text-4xl" />
        </div>
    );
};

export default RatingStars;
