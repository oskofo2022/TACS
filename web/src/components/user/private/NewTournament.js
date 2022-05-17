import React from "react";
import {Navigate} from 'react-router-dom'
import AuthContext from "../../context/AuthContext";
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    MenuItem,
    SelectChangeEvent,
    Stack,
    TextField,
    Typography
} from "@mui/material";
import {LanguagesConstants} from "../../../constants/LanguagesConstants";
import {NewTournamentRequest} from "../../../request/NewTournamentRequest";

const _incorrect_text_msg = 'Sólo se admiten letras y espacios';
const _empty_msg = 'Debe ingresar una palabra.';
const _incorrect_language_msg = 'Debe seleccionar un idioma';
const _incorrect_visibility_msg = 'Debe seleccionar la visibilidad';
const _incorrect_date_msg = 'Ingrese una fecha correcta';

const today = new Date().toISOString().substring(0, 10);
const minDate = "2022-01-01";
const maxDate = "2032-12-31";
const inputDateProps = {min: minDate, max: maxDate}

const NewTournament = () => {

    const authContext = React.useContext(AuthContext);
    const [redirect, setRedirect] = React.useState(null);

    const [signinOpen, setSigninOpen] = React.useState(true);

    /** form fields */
    const [tournamentName, setTournamentName] = React.useState('');
    const [tournamentNameValid, setTournamentNameValid] = React.useState(true);
    const [tournamentNameHelper, setTournamentNameHelper] = React.useState('');

    const [language, setLanguage] = React.useState('');
    const [languageValid, setLanguageValid] = React.useState(true);
    const [languageHelper, setLanguageHelper] = React.useState('');

    const [beginDate, setBeginDate] = React.useState(today);
    const [beginDateValid, setBeginDateValid] = React.useState(true);
    const [beginDateHelper, setBeginDateHelper] = React.useState('');

    const [endDate, setEndDate] = React.useState(today);
    const [endDateValid, setEndDateValid] = React.useState(true);
    const [endDateHelper, setEndDateHelper] = React.useState('');

    const [visibility, setVisibility] = React.useState('');
    const [visibilityValid, setVisibilityValid] = React.useState(true);
    const [visibilityHelper, setVisibilityHelper] = React.useState('');
    /** end form fields */


    const handleOpenSigninDialog = () => {
        setSigninOpen(true);
    };

    const handleCloseNewTournamentDialog = () => {
        // setIncorrectAccount(false);
        setSigninOpen(false);
        setRedirect(<Navigate to={'/'}/>);
    };

    /** Validations */
    const validateLanguage = (value) => {
        const isValid = !!LanguagesConstants[value];
        setLanguageValid(isValid);
        (!isValid) ? setLanguageHelper(_incorrect_language_msg) : setLanguageHelper('');
        return isValid;
    }

    const validateTournamentName = (value) => {
        const empty_re = new RegExp("^$");
        const correct_re = new RegExp("^[a-z A-Z]+$");
        const isCorrect = correct_re.test(value);
        const isEmpty = empty_re.test(value);
        const isValid = (isCorrect && !isEmpty);

        setTournamentName('');
        if (!isCorrect) setTournamentNameHelper(_incorrect_text_msg);
        if (isEmpty) setTournamentNameHelper(_empty_msg);

        setTournamentNameValid(isValid);
        return isValid;
    };

    const validateDate = (value) => {
        const date = new Date(value);
        const _minDate = new Date(minDate);
        const _maxDate = new Date(maxDate);

        return (_minDate <= date && date <= _maxDate);
    }

    const validateBeginDate = (value) => {
        const isValid = validateDate(value);
        setBeginDateValid(isValid);
        (!isValid) ? setBeginDateHelper(_incorrect_date_msg) : setBeginDateHelper('');
        return isValid;
    };

    const validateEndDate = (value) => {
        const isValid = validateDate(value);
        setEndDateValid(isValid);
        (!isValid) ? setEndDateHelper(_incorrect_date_msg) : setEndDateHelper('');
        return isValid;
    };

    const validateVisibility = (value) => {
        const isValid = ["PUBLIC", "PRIVATE"].includes(value);
        setVisibilityValid(isValid);
        (!isValid) ? setVisibilityHelper(_incorrect_visibility_msg) : setVisibilityHelper('');
        return isValid;
    };

    const allValidations = [
        {f: validateLanguage, v: language},
        {f: validateTournamentName, v: tournamentName},
        {f: validateBeginDate, v: beginDate},
        {f: validateEndDate, v: endDate},
        {f: validateVisibility, v: visibility},
    ];
    /** end Validations */

    /** fields onchange */
    const handleLanguageOnChange = (e: SelectChangeEvent) => {
        const value = e.target.value;
        validateLanguage(value);
        setLanguage(value);
    };

    const handleTournamentNameOnChange = (e: SelectChangeEvent) => {
        const value = e.target.value;
        validateTournamentName(value);
        setTournamentName(value);
    };

    const handleVisibilityOnChange = (e: SelectChangeEvent) => {
        const value = e.target.value;
        validateVisibility(value);
        setVisibility(value);
    };

    const handleBeginDateOnChange = (e) => {
        const value = e.target.value;
        validateBeginDate(value);
        setBeginDate(value);
    };

    const handleEndDateOnChange = (e) => {
        const value = e.target.value;
        validateEndDate(value);
        setEndDate(value);
    };
    /** end fields onchange */

    const handlePostTournament = () => {
        const body = JSON.stringify({
            name: tournamentName,
            language: language,
            visibility: visibility,
            startDate: beginDate,
            endDate: endDate
        });
        const newTournamentRequest = NewTournamentRequest.from(body)
        return newTournamentRequest.fetch();
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        const isValid = allValidations.reduce((prev, validation) => validation.f(validation.v) && prev, true);
        if (!isValid) return;

        handlePostTournament()
            .then(() => handleCloseNewTournamentDialog())
            .catch(e => setRedirect(authContext.handleUnauthorized(e)));
    }

    return (
        <React.Fragment>
            {redirect}

            <Dialog open={signinOpen} onClose={handleCloseNewTournamentDialog} className='newtournamentmodal'>
                <form onSubmit={handleSubmit}>
                    <DialogTitle>
                        <Typography sx={{color: "#BFE3B4"}} textAlign="center">Create Tournament</Typography>
                    </DialogTitle>
                    <DialogContent onSubmit={handleSubmit}>
                        <Stack noValidate spacing={5}>
                            {/*<DialogContentText sx={{display: incorrectAccount ? 'block' : 'none', color:'red'}}>*/}
                            {/*    {dialogMessage}*/}
                            {/*</DialogContentText>*/}
                            <TextField
                                autoFocus
                                margin="dense"
                                id="name"
                                label="Tournament Name"
                                type="text"
                                required={true}
                                fullWidth
                                variant="standard"
                                onChange={handleTournamentNameOnChange}
                                helperText={tournamentNameHelper}
                                error={!tournamentNameValid}
                            />
                            <Stack direction="row" alignItems="center" spacing={2}>
                                <TextField
                                    variant="standard"
                                    id="beginDate"
                                    label="Begin Date"
                                    type="date"
                                    required={true}
                                    defaultValue={today}
                                    onChange={handleBeginDateOnChange}
                                    helperText={beginDateHelper}
                                    error={!beginDateValid}
                                    sx={{width: 220}}
                                    inputProps={inputDateProps}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    // onChange={handleUsernameOnChange}
                                />
                                <TextField
                                    variant="standard"
                                    id="endDate"
                                    label="End Date"
                                    type="date"
                                    required={true}
                                    defaultValue={today}
                                    onChange={handleEndDateOnChange}
                                    helperText={endDateHelper}
                                    error={!endDateValid}
                                    sx={{width: 220}}
                                    inputProps={inputDateProps}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    // onChange={handleUsernameOnChange}
                                />
                            </Stack>
                            <Stack direction="row" alignItems="center" spacing={2}>
                                <TextField
                                    select
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
                                <TextField
                                    select
                                    variant="standard"
                                    id="select-visibility"
                                    label="Visibility"
                                    value={visibility}
                                    onChange={handleVisibilityOnChange}
                                    helperText={visibilityHelper}
                                    error={!visibilityValid}
                                    required={true}
                                    sx={{width: 220}}
                                >
                                    <MenuItem value={'PRIVATE'}>Private</MenuItem>
                                    <MenuItem value={'PUBLIC'}>Public</MenuItem>
                                </TextField>
                            </Stack>
                        </Stack>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={handleCloseNewTournamentDialog}>Cancel</Button>
                        <Button type='Submit'>Create</Button>
                    </DialogActions>
                </form>
            </Dialog>
        </React.Fragment>
    );
};

export default NewTournament;