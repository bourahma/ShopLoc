import React from "react";

import { Footer } from "flowbite-react";
import { Link } from "react-router-dom";

const FooterComponent = () => {
    return (
        <Footer container className="bg-shopgray text-black rounded-none">
            <div className="w-full">
                <div className="flex text-shopgraytext flex-wrap justify-around font-bold">
                    <Link to="#">A propos</Link>
                    <Link to="#">FAQ</Link>
                    <Link to="#">Politique de confidentialité</Link>
                    <Link to="#">Conditions générales d'utilisation</Link>
                </div>
            </div>
        </Footer>
    );
};

export default FooterComponent;
