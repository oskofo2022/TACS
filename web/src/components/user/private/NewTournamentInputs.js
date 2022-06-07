import React from "react";
import {
    maxDate,
    minDate,
    validateDate,
    validateLanguage,
    validateTextAlpha,
    validateVisibility
} from "../../formValidations/formValidations";
import {MenuItem, Stack} from "@mui/material";
import {LanguagesConstants} from "../../../constants/LanguagesConstants";
import WordleTextField from "../WordleInput";

const today = new Date().toISOString().substring(0, 10);

const inputDateProps = {min: minDate, max: maxDate}

const NewTournamentInputs = ({formData: formData}) => {

    const setValue = (propName) => (value) => {
        formData.set(propName, value)
    }

    return (
        <React.Fragment>
            <Stack noValidate spacing={5}>
                {/*<DialogContentText sx={{display: incorrectAccount ? 'block' : 'none', color:'red'}}>*/}
                {/*    {dialogMessage}*/}
                {/*</DialogContentText>*/}
                <WordleTextField
                    autoFocus
                    margin="dense"
                    id="name"
                    label="Tournament Name"
                    type="text"
                    required={true}
                    fullWidth
                    validation={validateTextAlpha}
                    valueSetter={setValue('name')}
                />                
                <Stack direction="row" alignItems="center" spacing={2}>
                    <WordleTextField
                        id="beginDate"
                        label="Begin Date"
                        type="date"
                        required={true}
                        defaultValue={today}
                        validation={validateDate}
                        valueSetter={setValue('beginDate')}
                        sx={{width: 220}}
                        inputProps={inputDateProps}
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />   
                    <WordleTextField
                        id="endDate"
                        label="End Date"
                        type="date"
                        required={true}
                        defaultValue={today}
                        validation={validateDate}
                        valueSetter={setValue('endDate')}
                        sx={{width: 220}}
                        inputProps={inputDateProps}
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />
                </Stack>
                <Stack direction="row" alignItems="center" spacing={2}>
                    <WordleTextField
                        select
                        id="language"
                        variant="standard"
                        label="Language"
                        required={true}
                        defaultValue={''}
                        validation={validateLanguage}
                        valueSetter={setValue('language')}
                        sx={{width: 220}}
                    >
                        {Object.entries(LanguagesConstants).map(([key, value]) => (
                            <MenuItem key={key} value={key}>{value.label}</MenuItem>
                        ))}
                    </WordleTextField>
                    <WordleTextField
                        select
                        variant="standard"
                        id="visibility"
                        label="Visibility"
                        required={true}
                        defaultValue={''}
                        validation={validateVisibility}
                        valueSetter={setValue('visibility')}
                        sx={{width: 220}}
                    >
                        <MenuItem value={'PRIVATE'}>Private</MenuItem>
                        <MenuItem value={'PUBLIC'}>Public</MenuItem>
                    </WordleTextField>
                </Stack>
            </Stack>
        </React.Fragment>
    )
}
export default NewTournamentInputs