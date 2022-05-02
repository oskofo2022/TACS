import React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import {Menu as MenuIcon} from "@material-ui/icons";
import Container from '@mui/material/Container';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import MenuItem from '@mui/material/MenuItem';
import './Header.css';
import '../user/auth/SigninMenu.js';
import SigninMenu from '../user/auth/SigninMenu.js';
import {Link} from 'react-router-dom';
import AuthContext from "../context/AuthContext";
import UserMenu from "../user/UserMenu";

// const pages = ['Diccionarios', 'Torneos'];
// const settings = ['Mis Torneos', 'Mis partidas', 'Logout'];

const Header = ({ pages, handleTab }) => {
  const [anchorElNav, setAnchorElNav] = React.useState(null);
  const authContext = React.useContext(AuthContext);

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleMenuOnclick = (page) => { handleCloseNavMenu(); handleTab(page); }
  return (
    <AppBar position="static" className='navbar'>
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <Typography
            variant="h6"
            noWrap
            component="div"
            sx={{ mr: 2, display: { xs: 'none', md: 'flex' } }}
          >
            WORDLE
          </Typography>

          <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleOpenNavMenu}
              color="inherit"
            >
              <MenuIcon />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'left',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'left',
              }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={{
                display: { xs: 'block', md: 'none' },
              }}
            >
              {pages.map((page) => (
                <Link to={'/' + page} style={{textDecoration: 'none'}}>
                  <MenuItem key={page} onClick={() => handleMenuOnclick(page)} style={{color: '#BFE3B4'}}>
                    <Typography textAlign="center">{page}</Typography>
                  </MenuItem>
                </Link>
              ))}
            </Menu>
          </Box>
          <Typography
            variant="h6"
            noWrap
            component="div"
            sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}
          >
            WORDLE
          </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
            {pages.map((page) => (
              <Link to={'/' + page} style={{textDecoration: 'none'}}>
                <Button
                  key={page}
                  onClick={() => handleMenuOnclick(page)}
                  sx={{ my: 2, color: 'white', display: 'block',}}
                >
                  {page}
                </Button>
              </Link>
            ))}
          </Box>
          <UserMenu/>
        </Toolbar>
      </Container>
    </AppBar>
  );
};
export default Header;