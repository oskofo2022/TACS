import React from 'react';
import './App.css';
import Header from './Header'
import Home from './Home'
import Footer from './Footer'
import {BrowserRouter as Router, Navigate} from 'react-router-dom'
import AuthContext from '../context/AuthContext'
import {UnauthorizedException} from "../../errors/UnauthorizedException";

const pages = ['diccionarios', 'torneos', 'helper'];
const defaultName = 'invitado';



function App() {

    const [activeTab, setActiveTab] = React.useState(pages[0]);
    const [authenticated, setAuthenticated] = React.useState(false);
    const [name, setName] = React.useState(defaultName);

    const handleTab = (tab) => {
        setActiveTab(tab);
    }

    const signin = (name = null) => {
        setAuthenticated(true);
        setName(name);
        sessionStorage.setItem('userData',
            JSON.stringify({name})
        );
    }

    const signout = () => {
        setAuthenticated(false);
        setName(defaultName);
        sessionStorage.removeItem('userData');
        document.cookie = "wordle-session=;path=/";
    }

    const handleUnauthorized = (e) => {
        if (e instanceof UnauthorizedException) 
            return (<Navigate to={'/logout'}/>);
        else
            throw e;
    };

    React.useEffect(() => {
        const storedData = JSON.parse(sessionStorage.getItem('userData'));
        if (storedData && storedData.name)
            signin(storedData.name);

    });

    return (
        <main>
            <Router>
                <AuthContext.Provider value={{authenticated: authenticated, signin: signin, signout: signout, name: name, handleUnauthorized: handleUnauthorized}}>
                    <Header pages={pages} handleTab={handleTab}/>
                    <Home activeTab={activeTab}/>
                    <Footer/>
                </AuthContext.Provider>
            </Router>
        </main>
    );
}

export default App;
