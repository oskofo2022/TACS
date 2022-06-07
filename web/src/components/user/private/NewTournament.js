import React from "react";
import {Navigate} from 'react-router-dom'
import AuthContext from "../../context/AuthContext";
import {
    Alert,
    Button,
    Collapse,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    styled,
    Typography
} from "@mui/material";
import {NewTournamentRequest} from "../../../request/NewTournamentRequest";
import NewTournamentInputs from "./NewTournamentInputs";
import StatusCodeHandler from "errors/StatusCodeHandler";


const NewTournamentFormStyle = styled('form')(
    ({ theme }) => ({
        display: 'flex',
        backgroundColor: 'black',
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

const body = new Map()
body.set('name', { value: '', isValid: false })
body.set('beginDate', { value: '', isValid: false })
body.set('endDate', { value: '', isValid: false })
body.set('language', { value: '', isValid: false })
body.set('visibility', { value: '', isValid: false })

const NewTournament = () => {
    const authContext = React.useContext(AuthContext);
    const [redirect, setRedirect] = React.useState(null);
    const [signinOpen, setSigninOpen] = React.useState(true);

    const [failedRequest, setFailedRequest] = React.useState(false);
    const [errorMessage, setErrorMessage] = React.useState('');

    const handleOpenSigninDialog = () => {
        setSigninOpen(true);
    };

    const handleCloseNewTournamentDialog = () => {
        setSigninOpen(false);
        setRedirect(<Navigate to={'/'} />);
    };

    const handlePostTournament = async () => {
        const bodyRequest = {}
        const newTournamentRequest = NewTournamentRequest.from(
            JSON.stringify({
                name: body.get('name').value,
                startDate: body.get('beginDate').value,
                endDate: body.get('endDate').value,
                language: body.get('language').value,
                visibility: body.get('visibility').value,
            })
        )
        return await newTournamentRequest.fetch();
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        if (failedRequest) {
            setFailedRequest(false);
            setTimeout(() => setErrorMessage(''), 500);
        }

        const bodyValues = Array.from(body.values());
        const isValid = bodyValues.map(i => i.isValid)
            .reduce((p, c) => p && c);
        if (!isValid) return;

        handlePostTournament()
            .then(StatusCodeHandler)
            .then(() => handleCloseNewTournamentDialog())
            .catch(e => setRedirect(authContext.handleUnauthorized(e)))
            .catch(e => e.response.json())
            .then(r => {
                console.log(r);
                setTimeout(() => {
                    setFailedRequest(true);
                    setErrorMessage(r.message || 'Error al procesar la petición');
                }, 500);
            });
    }

    return (
        <React.Fragment>
            {redirect}
            <Dialog open={signinOpen} onClose={handleCloseNewTournamentDialog} className='newtournamentmodal'>
                <form onSubmit={handleSubmit}>
                    <DialogTitle>
                        <Typography sx={{ color: "#BFE3B4" }} textAlign="center">Create Tournament</Typography>
                        <Collapse in={failedRequest} sx={{ minWidth: 'inherit', maxWidth: 'inherit' }} >
                            <Alert variant="filled" sx={{ mb: 2, minWidth: 'inherit', maxWidth: 'inherit' }} severity="error">
                                {errorMessage}
                            </Alert>
                        </Collapse>
                    </DialogTitle>
                    <DialogContent onSubmit={handleSubmit}>
                        <NewTournamentInputs formData={body} />
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={handleCloseNewTournamentDialog}>Cancel</Button>
                        <Button type='submit'>Create</Button>
                    </DialogActions>
                </form>
            </Dialog>

        </React.Fragment>
    );
};

export default NewTournament;