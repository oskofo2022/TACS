import React, { useState } from 'react';
import './App.css';
import Header from './Header'
import Home from './Home'
import Footer from './Footer'
import {BrowserRouter as Router} from 'react-router-dom'

function App() {
  const pages = ['diccionarios', 'torneos'];
  const [activeTab, setActiveTab] = React.useState(pages[0]);

  const handleTab = (tab) => {
    setActiveTab(tab);
  }

  return (
    <main>
      <Router>
        <Header pages={pages} handleTab={handleTab} />
        <Home activeTab={activeTab} />
        <Footer />
      </Router>
    </main>
  );
}

export default App;
