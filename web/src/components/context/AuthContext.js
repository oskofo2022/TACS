import React from 'react';

const AuthContext = React.createContext({
    authenticated: false,
    token: null,
    name:null,
    signin: () => {},
    signout: () => {},
});

export default AuthContext;