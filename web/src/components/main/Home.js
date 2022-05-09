import * as React from 'react';
import Tournaments from '../user/public/Tournaments'
import Dictionaries from '../user/public/Dictionaries';
import {Route, Routes} from 'react-router-dom';
import Signout from "../user/private/Signout";
import Container from "@mui/material/Container";
import MyTournaments from "../user/private/MyTournaments";
import MyMatches from "../user/private/MyMatches";
import AuthContext from "../context/AuthContext";

const Home = ({activeTab}) => {
    const authContext = React.useContext(AuthContext);
    return (
        <Routes>
            <Route path="/" element={<Container><p>Bienvenido {authContext.name}!</p></Container>}></Route>
            <Route path="/torneos" element={<Tournaments/>}/>
            <Route path="/diccionarios" element={<Dictionaries/>}/>
            <Route path="/mis-torneos" element={<MyTournaments/>} ></Route>
            <Route path="/mis-partidas" element={<MyMatches/>}></Route>
            <Route path="/logout" element={<Signout/>} ></Route>
        </Routes>
    );

}

export default Home;
