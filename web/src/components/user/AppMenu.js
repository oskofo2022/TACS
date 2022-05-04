import {Link} from "react-router-dom";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
import React from "react";
import IconButton from "@mui/material/IconButton";
import {Menu as MenuIcon} from "@material-ui/icons";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import Typography from "@mui/material/Typography";

const AppMenu = ({size, pages, handleTab}) => {
    const [anchorElNav, setAnchorElNav] = React.useState(null);

    const handleOpenNavMenu = (event) => {
        setAnchorElNav(event.currentTarget);
    };
    const handleCloseNavMenu = () => {
        setAnchorElNav(null);
    };

    const handleMenuOnclick = (page) => {
        handleCloseNavMenu();
        handleTab(page);
    }

    const sdAppMenu = () => (
        <Box sx={{flexGrow: 1, display: {xs: 'none', md: 'flex'}}}>
            {pages.map((page) => (
                <Link key={page} to={'/' + page} style={{textDecoration: 'none'}}>
                    <Button
                        key={page}
                        onClick={() => handleMenuOnclick(page)}
                        sx={{my: 2, color: 'white', display: 'block',}}
                    >
                        {page}
                    </Button>
                </Link>
            ))}
        </Box>
    )

    const mdAppMenu = () => (
        <Box sx={{flexGrow: 1, display: {xs: 'flex', md: 'none'}}}>
            <IconButton
                key={'appIconButton'}
                size="large"
                aria-label="account of current user"
                aria-controls="menu-appbar"
                aria-haspopup="true"
                onClick={handleOpenNavMenu}
                color="inherit"
            >
                <MenuIcon/>
            </IconButton>
            <Menu
                id="menu-appbar"
                key={'mdAppMenu'}
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
                    display: {xs: 'block', md: 'none'},
                }}
            >
                {pages.map((page) => (
                    <Link key={page} to={'/' + page} style={{textDecoration: 'none'}}>
                        <MenuItem key={page} onClick={() => handleMenuOnclick(page)}
                                  style={{color: '#BFE3B4'}}>
                            <Typography textAlign="center">{page}</Typography>
                        </MenuItem>
                    </Link>
                ))}
            </Menu>
        </Box>
    )

    return size === 'sd' ? (sdAppMenu()) : (mdAppMenu());
};

export default  AppMenu;