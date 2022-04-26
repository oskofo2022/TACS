import * as React from 'react';
//import axios from 'axios';
import { Container, MenuItem, Typography, Button } from '@mui/material';
import { TextField, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import Header from './Header.js'
import SignUpDialog from './SignUpDialog'

const Login = () => {

  const [signinOpen, setSigninOpen] = React.useState(false);
  const [username, setUsername] = React.useState('');
  const [password, setPassword] = React.useState('');
  const [isSignUpDialogOpen, setIsSignUpDialogOpen] = React.useState(false);

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
      const requestOptions = {
          method: 'POST',
          mode: 'cors',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ email: username, password: password })
      };
      const response = await fetch('http://localhost:8080/api/logins', requestOptions);
      const data = await response.headers;
      
      console.log(data);
      console.log(response);
  }

  const handleUsernameOnChange = (e) => setUsername(e.target.value);

  const handlepasswordOnChange = (e) => setPassword(e.target.value);

  return (
    <Container
      // open={Boolean(anchorElNav)}
      // onClose={handleCloseNavMenu}
      sx={{ display: "flex", flexDirection: "row" }}>
      <MenuItem key="Sign in" onClick={handleOpenSigninDialog}>
        <Typography sx={{ color: "#BFE3B4" }} textAlign="center">Sign in</Typography>
      </MenuItem>
      <MenuItem key="Sign up" onClick={() => setIsSignUpDialogOpen(true)}>
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
export default Login;