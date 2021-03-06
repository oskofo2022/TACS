import MenuItem from "@mui/material/MenuItem";
import Typography from "@mui/material/Typography";
import Menu from "@mui/material/Menu";
import React from "react";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import {Container} from "@mui/material";
import {Link} from "react-router-dom";
import AuthContext from "../context/AuthContext";
import {deepOrange} from "@mui/material/colors";
import AddGuess from "./private/AddGuess";

const settings = ['Nuevo Torneo', 'Inscripciones', 'Mis Jugadas', 'Mis Torneos', 'Logout'];

const SignedupMenu = () => {
    const [anchorElUser, setAnchorElUser] = React.useState(null);

    const [nuevaJugadaOpen, setNuevaJugadaOpen] = React.useState(false);

    const authContext = React.useContext(AuthContext);

    const handleOpenUserMenu = (event) => {
        setAnchorElUser(event.currentTarget);
    };

    const handleCloseUserMenu = () => {
        setAnchorElUser(null);
    };

    const handleNuevaJugadaOnClick = () =>{
        setNuevaJugadaOpen(true);
    }
    const handleNuevaJugadaOnClose = () => (setNuevaJugadaOpen(false));

    const handleMenuOnclick = () => { handleCloseUserMenu(); }

    return (
        <Container>
            <IconButton key={'avatar'} onClick={handleOpenUserMenu} sx={{p: 0}}>
                <Avatar sx={{ bgcolor: deepOrange[500] }} >{authContext.name.match(/\b(\w)/g).join('').substring(0,2)} </Avatar>
            </IconButton>
            <Menu
                key={'menu-user'}
                id="menu-user"
                sx={{mt: '45px'}}
                anchorEl={anchorElUser}
                anchorOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                }}
                keepMounted
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                }}
                open={Boolean(anchorElUser)}
                onClose={handleCloseUserMenu}
            >
                <MenuItem onClick={handleNuevaJugadaOnClick} style={{color: '#BFE3B4'}}>
                    <Typography textAlign="center">Nueva Jugada</Typography>
                </MenuItem>
                {settings.map((setting) => (
                    <Link key={setting} to={'/' + setting.toLowerCase().replace(' ', '-')} style={{textDecoration: 'none'}}>
                        <MenuItem key={setting} onClick={handleMenuOnclick} style={{color: '#BFE3B4'}}>
                            <Typography textAlign="center">{setting}</Typography>
                        </MenuItem>
                    </Link>
                ))}
            </Menu>
            <AddGuess open={nuevaJugadaOpen} onClose={handleNuevaJugadaOnClose}/>
        </Container>

    )
}

export default SignedupMenu;