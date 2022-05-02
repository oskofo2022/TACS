import React from 'react'
import AuthContext from "../../context/AuthContext";
import {Navigate} from 'react-router-dom';
//import Cookies from 'universal-cookie';

const Signout = () => {
    const authContext = React.useContext(AuthContext);
    //const cookies = new Cookies();
    authContext.signout();
    // cookies.remove('wordle-session', {path:'/', Secure:true,SameSite:'None', httpOnly:true});
    // cookies.remove('wordle-session', { path: '/' });
    return (<Navigate to={'/'}/> );
};

export default  Signout;