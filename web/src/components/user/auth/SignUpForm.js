import React from "react";
import {Alert, Button, Collapse, styled} from '@mui/material';
import {SignUpRequest} from "../../../request/SignUpRequest";
import {LoginRequest} from "../../../request/LoginRequest";
import AuthContext from "../../../components/context/AuthContext";
import WordleTextField from "../WordleInput";
import {validateEmail, validatePassword, validateTextAlpha} from "components/formValidations/formValidations";
import StatusCodeHandler from "errors/StatusCodeHandler";

const SignUpFormStyle = styled('form')(({ theme }) => ({
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    maxWidth: '300px',
    padding: theme.spacing(2),

    '& .MuiTextField-root': {
        margin: theme.spacing(1),
        width: '300px',
    },
    '& .MuiButtonBase-root': {
        margin: theme.spacing(2),
    },
}));



const SignUpForm = ({ closeFunc }) => {
    const authContext = React.useContext(AuthContext);

    const [name, setName] = React.useState({ value: '', isValid: false });
    const [email, setEmail] = React.useState({ value: '', isValid: false });
    const [password, setPassword] = React.useState({ value: '', isValid: false });

    const [failedRequest, setFailedRequest] = React.useState(false);
    const [errorMessage, setErrorMessage] = React.useState('');

    const handleSignUpPost = async () => {
        const body = new Map()
        body.set('name', name)
        body.set('email', email)
        body.set('password', password)

        const bodyValues = Array.from(body.values());
        const isValid = bodyValues.map(i => i.isValid)
            .reduce((p, c) => p && c);
        if (!isValid) return;

        const signUpRequest = SignUpRequest.from(JSON.stringify({ name: name.value, email: email.value, password: password.value }));
        return await signUpRequest.fetch();
    }

    const handleSignIn = async () => {
        const body = JSON.stringify({ email: email.value, password: password.value });
        const loginRequest = LoginRequest.from(body);
        const responseJson = await loginRequest.fetchAsJSON();
        const name = responseJson.name;
        authContext.signin(name);
    }



    const handleSubmit = (e) => {
        e.preventDefault();
        
        if(failedRequest) {
            setFailedRequest(false);        
            setTimeout(() => setErrorMessage(''), 500);
        }

        handleSignUpPost()
            .then(StatusCodeHandler)
            .then(() => handleSignIn().then(() => closeFunc()))
            .catch(e => e.response.json())
            .then(r => {
                console.log(r);
                setTimeout( () => {
                    setFailedRequest(true);
                    setErrorMessage(r.message || 'Error al procesar la petición');
                }, 500);
            });
    }

    return (
        <SignUpFormStyle onSubmit={handleSubmit}>
            <Collapse in={failedRequest} sx={{ minWidth: 'inherit', maxWidth: 'inherit' }} >
                <Alert variant="filled" sx={{ mb: 2, minWidth: 'inherit', maxWidth: 'inherit' }} severity="error">
                    {errorMessage}
                </Alert>
            </Collapse>
            {/* <TextField label="Name" variant="filled" required onChange={handleNameOnChange}/> */}
            <WordleTextField
                autoFocus
                margin="dense"
                id="name"
                label="Name"
                type="text"
                required={true}
                fullWidth
                validation={validateTextAlpha}
                valueSetter={setName}
            />
            {/* <TextField label="Email" variant="filled" type="email" required onChange={handleEmailOnChange}/> */}
            <WordleTextField
                autoFocus
                margin="dense"
                id="email"
                label="Email"
                type="email"
                required={true}
                fullWidth
                validation={validateEmail}
                valueSetter={setEmail}
            />
            {/* <TextField label="Password" variant="filled" type="password" required onChange={handlePasswordOnChange}/> */}
            <WordleTextField
                autoFocus
                margin="dense"
                id="password"
                label="Password"
                type="password"
                required={true}
                fullWidth
                validation={validatePassword}
                valueSetter={setPassword}
            />
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
