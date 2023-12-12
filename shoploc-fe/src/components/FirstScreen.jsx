import React from "react";
import { Link } from "react-router-dom";
import { Map } from "./Map";
import offre_img from "../images/offre_1.png";


function FirstScreen() {
    
    return (
        <div className="grid md:grid-cols-12 gap-20 mt-12">
            <div className="col-span-2 bg-shopgray justify-center items-center hidden md:flex">
                Pub Area
            </div>
            <div className="col-span-5">
                <Map />
            </div>
            <div className="col-span-2 bg-shopgray flex justify-center items-center rounded">
                <div className="">
                    <img src={offre_img} alt="" />
                </div>
            </div>
            <div className="col-span-3 flex flex-col gap-10 items-center">
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
