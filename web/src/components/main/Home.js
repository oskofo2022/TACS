import * as React from 'react';
import Tournaments from '../user/public/Tournaments'
import Dictionaries from '../user/public/Dictionaries';
import {Route, Routes} from 'react-router-dom';
import Signout from "../user/private/Signout";
import Container from "@mui/material/Container";
import MyInscriptions from "../user/private/MyInscriptions";
import MyMatches from "../user/private/MyMatches";
import AuthContext from "../context/AuthContext";
import NewTournament from "../user/private/NewTournament";
import MyTournaments from "../user/private/MyTournaments";
import MyPositions from "../user/private/MyPositions";

const Home = () => {
    const authContext = React.useContext(AuthContext);
    return (
        <Routes>
            <Route path="/" element={<Container><p>Bienvenido {authContext.name}!</p></Container>} />
            <Route path="/torneos" element={<Tournaments/>} />
            <Route path="/diccionarios" element={<Dictionaries/>} />
            <Route path="/mis-torneos" element={authContext.authenticated && <MyTournaments/>} />
            <Route path="/inscripciones" element={authContext.authenticated && <MyInscriptions/>} />
            <Route path="/nuevo-torneo" element={authContext.authenticated && <NewTournament/>} />
            <Route path="/mis-partidas" element={authContext.authenticated && <MyMatches/>} />
            {/*<Route path="/mis-posiciones/" element={<></>} />*/}
            <Route path="/inscripciones/:torneoName/positions" element={<MyPositions/>} />
            <Route path="/logout" element={<Signout/>} />
        </Routes>
    );

}

export default Home;
