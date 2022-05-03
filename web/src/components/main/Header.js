import React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import './Header.css';
import '../user/auth/SigninMenu.js';
import {Link} from 'react-router-dom';
import UserMenu from "../user/UserMenu";
import AppMenu from "../user/AppMenu";

// const pages = ['Diccionarios', 'Torneos'];
// const settings = ['Mis Torneos', 'Mis partidas', 'Logout'];

const Header = ({pages, handleTab}) => {

    const Wordle = ({display}) => (
        <Link to={'/'} style={{textDecoration: 'none'}}>
            <Typography
                variant="h6"
                noWrap
                component="div"
                sx={{flexGrow: 1, display: display, color: 'white'}}
            >
                WORDLE
            </Typography>
        </Link>
    )

    return (
        <AppBar position="static" className='navbar'>
            <Container maxWidth="xl">
                <Toolbar disableGutters>
                    <Wordle display={{xs: 'none', md: 'flex'}}/>
                    <AppMenu size={'md'} pages={pages} handleTab={handleTab}/>
                    <Wordle display={{xs: 'flex', md: 'none'}}/>
                    <AppMenu size={'sd'} pages={pages} handleTab={handleTab}/>
                    <UserMenu/>
                </Toolbar>
            </Container>
        </AppBar>
    );
};
export default Header;