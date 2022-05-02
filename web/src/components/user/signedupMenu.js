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

    const handleMenuOnclick = (setting) => { handleCloseUserMenu(); }

    return (
        <Container>
            <IconButton onClick={handleOpenUserMenu} sx={{p: 0}}>
                <Avatar/>
            </IconButton>
            <Menu
                sx={{mt: '45px'}}
                id="menu-user"
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
                    <Link to={'/' + setting} style={{textDecoration: 'none'}}>
                        <MenuItem key={setting} onClick={() => handleMenuOnclick(setting)} style={{color: '#BFE3B4'}}>
                            <Typography textAlign="center">{setting}</Typography>
                        </MenuItem>
                    </Link>
                ))}
            </Menu>
        </Container>

    )
}

export default SignedupMenu;