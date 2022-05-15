import React from "react";
import AuthContext from "../../context/AuthContext";
import {Button, Dialog, DialogActions, DialogContent, DialogTitle, Stack, TextField, Typography} from "@mui/material";

const NewTournament = () => {

    const authContext = React.useContext(AuthContext);
    const [redirectUnauthorized, setRedirectUnauthorized] = React.useState(null);
    const [signinOpen, setSigninOpen] = React.useState(true);
    const [value, setValue] = React.useState(new Date('2014-08-18T21:11:54'));

    // const handleSubmit = (e) => {
    //     e.preventDefault();
    //
    //
    //     const isValid = allValidations.every(validation => validation.f(validation.v));
    //     if(!isValid) {
    //         setLoading(false);
    //         return;
    //     }
    //
    //     handleGetMeanings()
    //         .then((r) => setmeanings(r.meanings))
    //         .then(() => setWordTitle(capitalizeFirstLetter(word)))
    //         .catch((e) => handleNoMeaningsFound(e))
    //         .finally(()=> setLoading(false));
    // }

    const handleOpenSigninDialog = () => {
        setSigninOpen(true);
    };

    const handleCloseSigninDialog = () => {
        // setIncorrectAccount(false);
        setSigninOpen(false);
    };

    return (
        <React.Fragment>
            {redirectUnauthorized}
            <Dialog open={signinOpen} onClose={handleCloseSigninDialog} className='signinmodal'>
                <DialogTitle>
                    <Typography sx={{color: "#BFE3B4"}} textAlign="center">Create Tournament</Typography>
                </DialogTitle>
                <DialogContent>
                    <Stack component="form" noValidate spacing={5}>
                        {/*<DialogContentText sx={{display: incorrectAccount ? 'block' : 'none', color:'red'}}>*/}
                        {/*    {dialogMessage}*/}
                        {/*</DialogContentText>*/}
                        <TextField
                            autoFocus
                            margin="dense"
                            id="name"
                            label="Tournament Name"
                            type="text"
                            fullWidth
                            variant="standard"
                            // onChange={handleUsernameOnChange}
                        />
                        <Stack direction="row" alignItems="center" spacing={2}>
                            <TextField
                                variant="standard"
                                id="beginDate"
                                label="Begin Date"
                                type="date"
                                defaultValue="2022-05-24"
                                sx={{width: 220}}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                            />
                            <TextField
                                variant="standard"
                                id="endDate"
                                label="End Date"
                                type="date"
                                defaultValue="2022-06-24"
                                sx={{ width: 220}}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                            />
                        </Stack>
                        <TextField
                            autoFocus
                            margin="dense"
                            id="name"
                            label="Name"
                            type="text"
                            fullWidth
                            variant="standard"
                            // onChange={handleUsernameOnChange}
                        />
                    </Stack>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseSigninDialog}>Cancel</Button>
                    <Button type='Submit'>Submit</Button>
                </DialogActions>
            </Dialog>
        </React.Fragment>
    );
};

export default NewTournament;