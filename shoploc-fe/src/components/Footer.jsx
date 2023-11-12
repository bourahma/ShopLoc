import React from "react";

import { Footer } from "flowbite-react";
import { Link } from "react-router-dom";

const FooterComponent = () => {
  return (
    <Footer container>
      <div className="w-full">
        <div className="grid w-full justify-between sm:flex sm:justify-between md:flex md:grid-cols-1">
          <div>
            <Footer.Brand
              href="#"
              src="https://picsum.photos/200/300"
              alt="shoploc Logo"
              name="shoploc"
            />
          </div>
          <div className="grid grid-cols-2 gap-8 sm:mt-4 sm:grid-cols-3 sm:gap-6">
            <div>
              <Footer.LinkGroup col>
                <Link to="#">S'inscrire</Link>
                <Link to="#">Se connecter</Link>
              </Footer.LinkGroup>
            </div>
            <div>
              <Footer.LinkGroup col>
                <Link to="#">A propos</Link>
                <Link to="#">FAQ</Link>
              </Footer.LinkGroup>
            </div>
            <div>
              <Footer.LinkGroup col>
                <Link to="#">Politique de confidentialité</Link>
                <Link to="#">Conditions générales d'utilisation</Link>
              </Footer.LinkGroup>
            </div>
          </div>
        </div>
      </div>
    </Footer>
  );
};

export default FooterComponent;
