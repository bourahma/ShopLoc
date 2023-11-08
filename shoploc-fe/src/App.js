
import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import HomeComponent from './components/home/HomeComponent';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomeComponent />}>
          <Route index element={<HomeComponent />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
