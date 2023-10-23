import './InfosComponent.css';
import logo from "../images/logo.png"
import offre from "../images/offre-bienvenue.png"

const InfosComponent = () => {

  return (
      <div id="legacy-info">
          <div id="shfe-logo">
              <img src={logo} alt="Gain de points" />
          </div>
          <div>
              <img id="shfe-spg" src={offre} alt="Gain de points" />
          </div>
      </div>
  );
};

export default InfosComponent;