import "../../App.css";

import AuthComponent from "../authentication/AuthComponent";
import InfosComponent from "../InfosComponent";
import LegacyInfosComponent from "../LegacyInfosComponent";

function HomeComponent() {
  return (
    <div>
      {/* Add router and move that to home page */}
      <div id="header">
        <div id='auth'>
          <InfosComponent></InfosComponent>
        </div>
        <div id='info'>
          <AuthComponent></AuthComponent>
        </div>
      </div>
      <div id='bottom'>
        <LegacyInfosComponent></LegacyInfosComponent>
      </div>

    </div>
  );
}

export default HomeComponent;