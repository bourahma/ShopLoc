import logo from "../images/logo.png"
import offre from "../images/offre-bienvenue.png"

const InfosComponent = () => {

  return (
      <div id="legacy-info">
          <div className="flex items-center justify-center h-full">
              <img className="h-full" src={logo} alt="Gain de points" />
          </div>
          <div className="flex items-center justify-center">
              <img id="shfe-spg" src={offre} alt="Gain de points" />
          </div>
      </div>
  );
};

export default InfosComponent;