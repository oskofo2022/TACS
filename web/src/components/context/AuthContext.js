import React from 'react';

const AuthContext = React.createContext({
    authenticated: false,
    token: null,
    name:null,
    signin: () => {},
    signout: () => {},
    handleUnauthorized: () => {},
});

export default AuthContext;