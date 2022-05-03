import React from 'react';
import './App.css';
import Header from './Header'
import Home from './Home'
import Footer from './Footer'
import {BrowserRouter as Router} from 'react-router-dom'
import AuthContext from '../context/AuthContext'

function App() {
    const pages = ['diccionarios', 'torneos'];
    const defaultName = 'invitado';
    const [activeTab, setActiveTab] = React.useState(pages[0]);
    const [authenticated, setAuthenticated] = React.useState(false);
    const [name, setName] = React.useState(defaultName);

    const handleTab = (tab) => {
        setActiveTab(tab);
    }

    const signin = (name=null) => {
        setAuthenticated(true);
        setName(name);
    }

    const signout = () => {
        setAuthenticated(false);
        setName(defaultName);
    }

    return (
        <main>
            <Router>
                <AuthContext.Provider value={{authenticated: authenticated, signin: signin, signout: signout, name: name}}>
                    <Header pages={pages} handleTab={handleTab}/>
                    <Home activeTab={activeTab}/>
                    <Footer/>
                </AuthContext.Provider>
            </Router>
        </main>
    );
}

export default App;
