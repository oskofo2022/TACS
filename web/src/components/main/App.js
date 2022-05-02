import React, {useState} from 'react';
import './App.css';
import Header from './Header'
import Home from './Home'
import Footer from './Footer'
import {BrowserRouter as Router} from 'react-router-dom'
import AuthContext from '../context/AuthContext'

function App() {
    const pages = ['diccionarios', 'torneos'];
    const [activeTab, setActiveTab] = React.useState(pages[0]);
    const [authenticated, setAuthenticated] = React.useState(false);

    const handleTab = (tab) => {
        setActiveTab(tab);
    }

    const signin = () => {
        setAuthenticated(true);
    }

    const signout = () => {
        setAuthenticated(false);
    }

    return (
        <main>
            <Router>
                <AuthContext.Provider value={{authenticated: authenticated, signin: signin, signout: signout}}>
                    <Header pages={pages} handleTab={handleTab}/>
                    <Home activeTab={activeTab}/>
                    <Footer/>
                </AuthContext.Provider>
            </Router>
        </main>
    );
}

export default App;
