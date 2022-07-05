import React from "react";
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    MenuItem,
    SelectChangeEvent,
    TextField,
    Typography
} from "@mui/material";
import {LanguagesConstants} from "../../../constants/LanguagesConstants";
import AuthContext from "../../context/AuthContext";
import {UserGuessRequest} from "../../../request/UserGuessRequest";

const _incorrect_language_msg = 'Debe seleccionar un idioma';
const _incorrect_guesses_msg = 'Debe ingresar un numero entre 1 y 7';

const AddGuess = ({open, onClose}) => {
    const authContext = React.useContext(AuthContext);
    const [redirect, setRedirect] = React.useState(null);

    // const [open, setOpen] = React.useState(true);
    const [success, setSuccess] = React.useState(false);

    const [guess, setGuess] = React.useState();
    const [guessesValid, setGuessesValid] = React.useState(true);
    const [guessesHelper, setGuessesHelper] = React.useState('');

    const [language, setLanguage] = React.useState('');
    const [languageValid, setLanguageValid] = React.useState(true);
    const [languageHelper, setLanguageHelper] = React.useState('');

    // const onClose = () => {
    //     setOpen(false);
    // }

    const validateLanguage = (value) => {
        const isValid = !!LanguagesConstants[value];
        setLanguageValid(isValid);
        (!isValid) ? setLanguageHelper(_incorrect_language_msg) : setLanguageHelper('');
        return isValid;
    }

    const validateGuess = (value) => {
        const empty_re = new RegExp("^$");
        const correct_re = /^[1-7]$/;
        const isCorrect = correct_re.test(value);
        const isEmpty = empty_re.test(value);
        const isValid = (isCorrect && !isEmpty);

        setGuessesHelper('');
        if (!isCorrect || isEmpty) setGuessesHelper(_incorrect_guesses_msg);

        setGuessesValid(isValid);
        return isValid;
    }

    const handleLanguageOnChange = (e: SelectChangeEvent) => {
        const value = e.target.value;
        validateLanguage(value);
        setLanguage(value);
    };

    const handleGuessOnChange = (e) => {
        const value = e.target.value;
        validateGuess(value);
        setGuess(value);
    }

    const handleSuccess = () => {
        setSuccess(true);
        onClose();
    }

    const handleGuessPost = async () => {
        const body = JSON.stringify({
            results: [
                {
                    language: language,
                    guessesCount: guess
                }
            ]
        });
        const userGuessRequest = UserGuessRequest.from( body);
        return await userGuessRequest.fetch();
    }

    const allValidations = [
        {f: validateLanguage, v: language},
        {f: validateGuess, v: guess},
    ];

    const handleCloseAddUserDialogSuccess = () => {
        const isValid = allValidations.reduce((prev, validation) => validation.f(validation.v) && prev, true);
        if (!isValid) return;
        handleGuessPost()
            .then((r) => {if (r.status === 200) handleSuccess();})
            .catch(e => setRedirect(authContext.handleUnauthorized(e)));
    }

    const handleCloseSuccessDialog = () => {
        setSuccess(false);
    }

    return (
        <React.Fragment>
            {redirect}
            <Dialog open={open} onClose={onClose} className='addusermodal' fullWidth>
                <DialogTitle>
                    <Typography variant="h5" sx={{color: "green"}} textAlign="center">Add Guess</Typography>
                </DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="guesses"
                        label="Guesses"
                        type="number"
                        required={true}
                        fullWidth
                        variant="standard"
                        onChange={handleGuessOnChange}
                        helperText={guessesHelper}
                        error={!guessesValid}
                    />
                    <TextField
                        select
                        margin="dense"
                        id="select-language"
                        variant="standard"
                        label="Language"
                        value={language}
                        onChange={handleLanguageOnChange}
                        helperText={languageHelper}
                        error={!languageValid}
                        required={true}
                        sx={{width: 220}}
                    >
                        {Object.entries(LanguagesConstants).map(([key, value]) => (
                            <MenuItem key={key} value={key}>{value.label}</MenuItem>
                        ))}
                    </TextField>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose}>Cancel</Button>
                    <Button onClick={handleCloseAddUserDialogSuccess}>Add</Button>
                </DialogActions>
            </Dialog>
            <Dialog open={success} onClose={handleCloseSuccessDialog} className='successmodal' style={{backgroundColor: 'transparent'}}>
                <DialogTitle>
                    <Typography variant="h6" sx={{color: "green"}} textAlign="center">Success</Typography>
                </DialogTitle>
                <DialogActions>
                    <Button onClick={handleCloseSuccessDialog}>Close</Button>
                </DialogActions>
            </Dialog>
        </React.Fragment>
    )
}

export default  AddGuess;