import React from "react";
import { Link } from "react-router-dom";
import { Map } from "./Map";
import offre_img from "../images/offre_1.png";


function FirstScreen() {
    
    return (
        <div className="grid md:grid-cols-12 grid-cols-3 md:gap-20 gap-4 my-12">
            <div className="md:col-span-2 bg-shopgray justify-center items-center hidden md:flex md:order-1">
                Pub Area
            </div>
            <div className="md:col-span-5 col-span-3 order-last md:order-2">
                <Map />
            </div>
            <div className="md:col-span-2 col-span-1 bg-shopgray flex justify-center items-center rounded order-1 md:order-3">
                <div className="">
                    <img src={offre_img} alt="" />
                </div>
            </div>
            <div className="md:col-span-3 col-span-2 flex flex-col gap-10 items-center justify-center md:justify-start order-2 md:order-4">
                <Link to="/login">
                    <button className="bg-shopred text-white font-bold px-6 py-2 rounded text-center w-48 ">
                        Se connecter
                    </button>
                </Link>
                <Link to="/signup">
                    <button className="bg-shopyellow text-white font-bold px-6 py-2 rounded text-center w-48">
                        Créer Mon Compte
                    </button>
                </Link>
            </div>
        </div>
    );
}

export default FirstScreen;
