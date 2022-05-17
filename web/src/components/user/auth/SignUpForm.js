import React from "react";
import {Button, styled, TextField} from '@mui/material';
import {SignUpRequest} from "../../../request/SignUpRequest";

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


const SignUpForm = ({closeFunc}) => {
    const [name, setName] = React.useState(null);
    const [email, setEmail] = React.useState(null);
    const [password, setPassword] = React.useState(null);

    const handleSignUpPost = async () => {
        const body = JSON.stringify({name: name, email: email, password: password})
        const signUpRequest = SignUpRequest.from(body)
        return await signUpRequest.fetch();
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        handleSignUpPost()
            .then(() => closeFunc());
    }

    const handleNameOnChange = (e) => {
        setName(e.target.value);
    }
    const handleEmailOnChange = (e) => {
        setEmail(e.target.value);
    }
    const handlePasswordOnChange = (e) => {
        setPassword(e.target.value);
    }

    return (
        <SignUpFormStyle onSubmit={handleSubmit}>
            <TextField label="Name" variant="filled" required onChange={handleNameOnChange}/>
            <TextField label="Email" variant="filled" type="email" required onChange={handleEmailOnChange}/>
            <TextField label="Password" variant="filled" type="password" required onChange={handlePasswordOnChange}/>
            <div>
            <Button variant="contained" onClick={closeFunc}>
                Cancel
            </Button>
            <Button type="submit" variant="contained" color="primary">
                Signup
            </Button>
            </div>
        </SignUpFormStyle>
    );
}

export default SignUpForm;
