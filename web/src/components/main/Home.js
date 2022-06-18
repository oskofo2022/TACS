import * as React from 'react';
import {Route, Routes} from 'react-router-dom';
import Container from "@mui/material/Container";
import AuthContext from "../context/AuthContext";
import Tournaments from '../user/public/Tournaments'
import Dictionaries from '../user/public/Dictionaries';
import Signout from "../user/private/Signout";
import MyInscriptions from "../user/private/MyInscriptions";
import MyPositions from "../user/private/MyPositions";
import NewTournament from "../user/private/NewTournament";
import MyTournaments from "../user/private/MyTournaments";
import {styled} from "@mui/material";
import Helper from 'components/user/public/Helper';

const HomeStyle = styled('section')(({theme}) => ({
    // width: 'max-content',
    maxWidth: '1000px',
    width: '100%',
    margin: 'auto',
    marginTop: '15vh',

    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',

}));

const Home = () => {
    const authContext = React.useContext(AuthContext);
    return (
        <HomeStyle>
            <Routes>
                <Route path="/" element={<Container><p>Bienvenido {authContext.name}!</p></Container>}></Route>
                <Route path="/torneos" element={<Tournaments/>}/>
                <Route path="/diccionarios" element={<Dictionaries/>}/>
                <Route path="/helper" element={<Helper/>}/>
                <Route path="/mis-torneos" element={authContext.authenticated && <MyTournaments/>} ></Route>
                <Route path="/inscripciones" element={authContext.authenticated && <MyInscriptions/>} ></Route>
                <Route path="/nuevo-torneo" element={authContext.authenticated && <NewTournament/>} ></Route>
                <Route path="/inscripciones/:torneoName/positions" element={<MyPositions/>} />
                <Route path="/logout" element={<Signout/>} ></Route>
            </Routes>
        </HomeStyle>
    );

}

export default Home;
