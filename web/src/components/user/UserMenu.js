import React from "react";
import SigninMenu from "./auth/SigninMenu";
import Box from "@mui/material/Box";
import AuthContext from "../context/AuthContext";
import SignedupMenu from "./SignedupMenu";

const UserMenu = () => {

    const authContext = React.useContext(AuthContext);

    const getSignedUpTemplate = () => {
        return (<SignedupMenu/>);
    }

    const getUnauthenticatedTemplate = () => {
        return (<SigninMenu/>);
    }

    const SetLoginTemplateBasedOnAuthentication = ({isAuthenticated}) => {
        return isAuthenticated ? getSignedUpTemplate() : getUnauthenticatedTemplate();
    }

    return (
        <Box sx={{flexGrow: 0}}>
            <SetLoginTemplateBasedOnAuthentication isAuthenticated={authContext.authenticated}/>
        </Box>
    )
}

export default UserMenu;