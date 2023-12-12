import React from "react";

import { Footer } from "flowbite-react";
import { Link } from "react-router-dom";

const FooterComponent = () => {
  return (
    <Footer container className="bg-black rounded-none">
      <div className="w-full">
        <div className="grid w-full justify-between sm:flex sm:justify-between md:flex md:grid-cols-1">
          <div></div>
          <div className="grid grid-cols-2 gap-8 sm:mt-4 sm:grid-cols-3 sm:gap-6 ">
            <div>
              <Footer.LinkGroup col>
                <Link to="#" className="text-white">
                  A propos
                </Link>
                <Link to="#" className="text-white">
                  FAQ
                </Link>
              </Footer.LinkGroup>
            </div>
            <div>
              <Footer.LinkGroup col>
                <Link to="#" className="text-white">
                  Politique de confidentialité
                </Link>
                <Link to="#" className="text-white">
                  Conditions générales d'utilisation
                </Link>
              </Footer.LinkGroup>
            </div>
          </div>
        </div>
      </div>
    </Footer>
  );
};

export default FooterComponent;
