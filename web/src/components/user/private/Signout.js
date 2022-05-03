import React from 'react'
import AuthContext from "../../context/AuthContext";
import {Navigate} from 'react-router-dom';

const Signout = () => {
    const authContext = React.useContext(AuthContext);
    authContext.signout();
    return (<Navigate to={'/'}/> );
};

export default  Signout;