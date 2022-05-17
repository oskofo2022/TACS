import * as React from 'react';
import Tournaments from '../user/public/Tournaments'
import Dictionaries from '../user/public/Dictionaries';
import {Route, Routes} from 'react-router-dom';
import Signout from "../user/private/Signout";
import Container from "@mui/material/Container";
import MyInscriptions from "../user/private/MyInscriptions";
import AddGuess from "../user/private/AddGuess";
import AuthContext from "../context/AuthContext";
import NewTournament from "../user/private/NewTournament";
import MyTournaments from "../user/private/MyTournaments";

const Home = () => {
    const authContext = React.useContext(AuthContext);
    return (
        <Routes>
            <Route path="/" element={<Container><p>Bienvenido {authContext.name}!</p></Container>}></Route>
            <Route path="/torneos" element={<Tournaments/>}/>
            <Route path="/diccionarios" element={<Dictionaries/>}/>
            <Route path="/mis-torneos" element={authContext.authenticated && <MyTournaments/>} ></Route>
            <Route path="/inscripciones" element={authContext.authenticated && <MyInscriptions/>} ></Route>
            <Route path="/inscripciones/:id/positions" element={authContext.authenticated && <MyInscriptions/>} ></Route>
            <Route path="/nuevo-torneo" element={authContext.authenticated && <NewTournament/>} ></Route>
            <Route path="/nueva-jugada" element={authContext.authenticated && <AddGuess/>}></Route>
            <Route path="/logout" element={<Signout/>} ></Route>
        </Routes>
    );

}

export default Home;
