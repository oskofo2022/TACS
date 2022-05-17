import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    SelectChangeEvent,
    TextField,
    Typography
} from "@mui/material";
import React from "react";
import {InscriptUserToTournamentRequest} from "../../../request/InscriptUserToTournamentRequest";

const _user_not_exists = 'El usuario no existe.'
const _incorrect_text_msg = 'Sólo se admiten numeros';
const _empty_msg = 'Debe ingresar una palabra.';

const AddUser = ({open, tournamentId, tournamentName, onClose}) => {

    const [userId, setUserId] = React.useState('');
    const [userNameValid, setUserNameValid] = React.useState(true);
    const [userNameHelper, setUserNameHelper] = React.useState('');

    const [success, setSuccess] = React.useState(false);

    const userNotExists = () => {
        setUserNameHelper(_user_not_exists);
        setUserNameValid(false);
    }

    const handleSuccess = () => {
        onClose();
        setSuccess(true)
    }

    const handleAddUserPost = async () => {
        const body = JSON.stringify({userId: userId});
        const pathParam = {name: 'tournamentId', value: tournamentId }
        const inscriptUserToTournament = InscriptUserToTournamentRequest.from(pathParam, body);
        return await inscriptUserToTournament.fetch();
    }

    const handleCloseAddUserDialogSuccess = () => {
        handleAddUserPost()
            .then((r) => (r.status === 201)? handleSuccess():userNotExists())
            // .catch(e => setRedirect(authContext.handleUnauthorized(e)));
    }

    const validateUserId = (value) => {
        const empty_re = new RegExp("^$");
        const correct_re = /^\d+$/;
        const isCorrect = correct_re.test(value);
        const isEmpty = empty_re.test(value);
        const isValid = (isCorrect && !isEmpty);

        setUserNameHelper('');
        if (!isCorrect) setUserNameHelper(_incorrect_text_msg);
        if (isEmpty) setUserNameHelper(_empty_msg);

        setUserNameValid(isValid);
        return isValid;
    };

    const handleUserIdOnChange = (e: SelectChangeEvent) => {
        const value = e.target.value;
        validateUserId(value);
        setUserId(value);
    }

    const handleCloseSuccessDialog = () => {
        setSuccess(false);
    }

    return (
        <React.Fragment>
            <Dialog open={open} onClose={onClose} className='addusermodal' fullWidth>
                <DialogTitle>
                    <Typography variant="h5" sx={{color: "green"}} textAlign="center">Add User</Typography>
                </DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="userId"
                        label="User id"
                        type="text"
                        required={true}
                        fullWidth
                        variant="standard"
                        onChange={handleUserIdOnChange}
                        helperText={userNameHelper}
                        error={!userNameValid}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose}>Cancel</Button>
                    <Button onClick={handleCloseAddUserDialogSuccess}>Add</Button>
                </DialogActions>
            </Dialog>
            <Dialog open={success} onClose={handleCloseSuccessDialog} className='successmodal' style={{backgroundColor: 'transparent'}}>
                <DialogTitle>
                    <Typography variant="h6" sx={{color: "green"}} textAlign="center">User {userId} added to {tournamentName}</Typography>
                </DialogTitle>
                <DialogActions>
                    <Button onClick={handleCloseSuccessDialog}>Close</Button>
                </DialogActions>
            </Dialog>
        </React.Fragment>
    )
};
export default  AddUser;