import './App.css';

import React, { useState, useEffect } from 'react';
import AuthComponent from "./components/AuthComponent";
import InfosComponent from "./components/InfosComponent";
import LegacyInfosComponent from "./components/LegacyInfosComponent";

function App() {
  return (
    <div>
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
