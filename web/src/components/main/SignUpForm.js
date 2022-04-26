import React, { useState } from "react";
import { TextField, Button, styled } from '@mui/material';

const SignUpFormStyle = styled('form')(({theme}) => ({
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    padding: theme.spacing(2),

    '& .MuiTextField-root': {
    margin: theme.spacing(1),
    width: '300px',
    },
    '& .MuiButtonBase-root': {
    margin: theme.spacing(2),
    },
}));

function SignUpForm({closeFunc}) {
    return (
    <SignUpFormStyle>
        <TextField label="First Name" variant="filled" required/>
        <TextField label="Last Name" variant="filled" required/>
        <TextField label="Email" variant="filled" type="email" required/>
        <TextField label="Password" variant="filled" type="password" required/>

        <div>
        <Button variant="contained" onClick={closeFunc}>
            Cancel
        </Button>
        <Button type="submit" variant="contained" color="primary">
            Signup
        </Button>
        </div>
    </SignUpFormStyle>);
}

export default SignUpForm;
