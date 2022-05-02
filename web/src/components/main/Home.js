import * as React from 'react';
import Tournaments from '../public/Tournaments'
import Dictionaries from '../public/Dictionaries';
import { Route, Routes } from 'react-router-dom';

const Home = ({ activeTab }) => {

  return (
    <Routes>
      <Route path="/torneos" element={<Tournaments />} />
      <Route path="/diccionarios" element={<Dictionaries activeTab={ activeTab } />} />
    </Routes>
  );

}

export default Home;
