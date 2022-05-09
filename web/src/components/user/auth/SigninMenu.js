import * as React from 'react';
//import axios from 'axios';
import * as URL from '../../../constants/wordleURLs'
import {
    Button,
    Container,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    MenuItem,
    TextField,
    Typography
} from '@mui/material';
import SignUpDialog from './SignUpDialog'
import AuthContext from "../../context/AuthContext";
import {Request} from "../../../httpUtils/Request";

const SigninMenu = () => {

    const [signinOpen, setSigninOpen] = React.useState(false);
    const [dialogMessage, setDialogMessage] = React.useState('Incorrect user or password.');
    const [incorrectAccount, setIncorrectAccount] = React.useState(false);
    const [username, setUsername] = React.useState('');
    const [password, setPassword] = React.useState('');
    const [isSignUpDialogOpen, setIsSignUpDialogOpen] = React.useState(false);
    const authContext = React.useContext(AuthContext);

    const handleCloseSignUpDialog = () => {
        setIsSignUpDialogOpen(false);
    }

    const handleOpenSignUpDialog = () => {
        setIsSignUpDialogOpen(true);
    }

    const handleOpenSigninDialog = () => {
        setSigninOpen(true);
    };

    const handleCloseSigninDialog = () => {
        setIncorrectAccount(false);
        setSigninOpen(false);
    };

    const handleSigninPost = async () => {
        // POST request using fetch with async/await
        const url = URL.LOGIN
        const requestOptions = Request.post(JSON.stringify({email: username, password: password}));
        const response = await fetch(url, requestOptions);
        const responseJson = await response.json();

        if (response.status === 200) {
            const name = responseJson.name;
            authContext.signin(name);
            handleCloseSigninDialog();
        } else {
            setIncorrectAccount(true);
        }
    }

    const handleUsernameOnChange = (e) => setUsername(e.target.value);

    const handlepasswordOnChange = (e) => setPassword(e.target.value);

    return (
        <Container
            sx={{display: "flex", flexDirection: "row"}}>
            <MenuItem key="Sign in" onClick={handleOpenSigninDialog}>
                <Typography sx={{color: "#BFE3B4"}} textAlign="center">Sign in</Typography>
            </MenuItem>
            <MenuItem key="Sign up" onClick={handleOpenSignUpDialog}>
                <Typography sx={{color: "#BFE3B4"}} textAlign="center">Sign up</Typography>
            </MenuItem>
            <Dialog open={signinOpen} onClose={handleCloseSigninDialog} className='signinmodal'>
                <DialogTitle>
                    <Typography sx={{color: "#BFE3B4"}} textAlign="center">Sign in</Typography>
                </DialogTitle>
                <DialogContent>
                    <DialogContentText sx={{display: incorrectAccount ? 'block' : 'none', color:'red'}}>
                        {dialogMessage}
                    </DialogContentText>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="username"
                        label="Username or email address"
                        type="email"
                        fullWidth
                        variant="standard"
                        onChange={handleUsernameOnChange}
                    />
                    <TextField
                        margin="dense"
                        id="password"
                        label="Password"
                        type="password"
                        fullWidth
                        variant="standard"
                        onChange={handlepasswordOnChange}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseSigninDialog}>Cancel</Button>
                    <Button type='Submit' onClick={handleSigninPost}>Submit</Button>
                </DialogActions>
            </Dialog>
            <SignUpDialog open={isSignUpDialogOpen} handleClose={handleCloseSignUpDialog}/>
        </Container>
    );
};

export default SigninMenu;