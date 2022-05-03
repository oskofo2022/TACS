import * as React from 'react';
//import axios from 'axios';
import {
    Button,
    Container,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    MenuItem,
    TextField,
    Typography
} from '@mui/material';
import SignUpDialog from './SignUpDialog'
import AuthContext from "../../context/AuthContext";

const SigninMenu = () => {

  const [signinOpen, setSigninOpen] = React.useState(false);
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
    setSigninOpen(false);
  };

  const handleSigninPost = async () => {
      // POST request using fetch with async/await
      const url = 'http://localhost:8080/api/logins'
      const requestOptions = {
          method: 'POST',
          mode: 'cors',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ email: username, password: password }),
          credentials: 'include'
      };
      const response = await fetch(url, requestOptions);
      const responseJson = await response.json();
      const name = responseJson.name;
      console.log(name);
      handleCloseSigninDialog();
      authContext.signin(name);
  }

  const handleUsernameOnChange = (e) => setUsername(e.target.value);

  const handlepasswordOnChange = (e) => setPassword(e.target.value);

  return (
    <Container
      sx={{ display: "flex", flexDirection: "row" }}>
      <MenuItem key="Sign in" onClick={handleOpenSigninDialog}>
        <Typography sx={{ color: "#BFE3B4" }} textAlign="center">Sign in</Typography>
      </MenuItem>
      <MenuItem key="Sign up" onClick={handleOpenSignUpDialog}>
        <Typography sx={{ color: "#BFE3B4" }} textAlign="center">Sign up</Typography>
      </MenuItem>
      <Dialog open={signinOpen} onClose={handleCloseSigninDialog} className='signinmodal'>
        <DialogTitle>
          <Typography sx={{ color: "#BFE3B4" }} textAlign="center">Sign in</Typography>
        </DialogTitle>
        <DialogContent>
          {/* <DialogContentText>
            Use your wordle account.
          </DialogContentText> */}
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