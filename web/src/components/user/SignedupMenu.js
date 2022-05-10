import MenuItem from "@mui/material/MenuItem";
import Typography from "@mui/material/Typography";
import Menu from "@mui/material/Menu";
import React from "react";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import {Container} from "@mui/material";
import {Link} from "react-router-dom";

const settings = ['Mis Torneos', 'Mis partidas', 'Logout'];

const SignedupMenu = () => {
    const [anchorElUser, setAnchorElUser] = React.useState(null);

    const handleOpenUserMenu = (event) => {
        setAnchorElUser(event.currentTarget);
    };

    const handleCloseUserMenu = () => {
        setAnchorElUser(null);
    };

    const handleMenuOnclick = () => { handleCloseUserMenu(); }

    return (
        <Container>
            <IconButton key={'avatar'} onClick={handleOpenUserMenu} sx={{p: 0}}>
                <Avatar/>
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
                {settings.map((setting) => (
                    <Link key={setting} to={'/' + setting.toLowerCase().replace(' ', '-')} style={{textDecoration: 'none'}}>
                        <MenuItem key={setting} onClick={handleMenuOnclick} style={{color: '#BFE3B4'}}>
                            <Typography textAlign="center">{setting}</Typography>
                        </MenuItem>
                    </Link>
                ))}
            </Menu>
        </Container>

    )
}

export default SignedupMenu;