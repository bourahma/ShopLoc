import './App.css';

import React, { useState, useEffect } from 'react';
import AuthComponent from "./components/authentication/AuthComponent";
import InfosComponent from "./components/InfosComponent";
import LegacyInfosComponent from "./components/LegacyInfosComponent";

function App() {
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

export default App;
