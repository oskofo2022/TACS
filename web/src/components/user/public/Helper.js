import React from "react";
import ReactCodeInput from "react-code-input/ReactCodeInput";
import WordleTextField from "../WordleInput";
import LoadingButton from '@mui/lab/LoadingButton';
import SearchIcon from '@mui/icons-material/Search';
import {MenuItem, styled} from "@mui/material";
import {validateLanguage, validateNoSpacedText} from "components/formValidations/formValidations";
import {LanguagesConstants} from "constants/LanguagesConstants";
import {HelpRequest} from "request/HelpRequest";
import {QueryParams} from "httpUtils/QueryParams";
import WordList from "./WordList";

const HelperDiv = styled('div')(({theme})=> ({
    display: 'flex',
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'center',
    alignItems: 'flex-start',
}))

const HelperFormStyle = styled('form')(({theme}) => ({
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

const WORD_LEN = 5
const pageSize = 20
const defaultFirstPage = 1


const Helper = () => {
    const [loading, setLoading] = React.useState(false);
    const [words, setWords] = React.useState([]);
    const [page, setPage] = React.useState(defaultFirstPage);
    const [disabledMoreButton, setDisabledMoreButton] = React.useState(false);
    const [formData, setFormData] = React.useState(new Map([
        ['goodLetters', {value: '', isValid: false}],
        ['badLetters', {value: '', isValid: false}],
        ['greenLetters', {value: '', isValid: false}],
        ['endDate', {value: '', isValid: false}],
        ['language', {value: '', isValid: false}]
    ]))

    const setValue = (propName) => (value) => setFormData(formData.set(propName, value))

    const handleGetHelp = (_page) => {// _page fix a bug in case page state does not update immediatly
        console.log(_page, page)
        const queryParams = new QueryParams({
            page: _page || page,
            pageSize: pageSize,
            sortBy: 'word',
            sortOrder: 'ASCENDING',
            goodLetters: formData.get('goodLetters').value,
            badLetters: formData.get('badLetters').value,
            greenLetters: formData.get('greenLetters').value,
        })
        delete queryParams.sortBy;
        const helpRequest = HelpRequest.from(
            {name: 'language', value: formData.get('language').value},
            queryParams
        );
        return helpRequest.fetchAsJSON();
    }

    const handleGreenLetters_onchange = (val) => {
        const inputsValues = val.map(v => (v === "") ? '-' : v)
            .join('')
            .toLowerCase();
        console.log(inputsValues);
        setValue('greenLetters')({value: inputsValues, isValid: true});
    }

    const handleFormSubmit = (e) => {
        e.preventDefault();
        setLoading(true);
        setPage(defaultFirstPage);
        setDisabledMoreButton(false);
        handleGetHelp(defaultFirstPage)
            .then(r => setWords(r.pageItems.map(i => i.word)))
            .then(_ => setLoading(false))

    }

    const handleMoreWords = (e) => {
        e.preventDefault();
        setLoading(true);
        setPage(page+1);
        handleGetHelp(page+1)
            .then(r => {
                if(r.pageCount === page) setDisabledMoreButton(true);
                return r
            })
            .then(r => setWords(words.concat(r.pageItems.map(i => i.word))))
            .then(_ => setLoading(false))
    }

    return (
        <HelperDiv>
            <HelperFormStyle onSubmit={handleFormSubmit}>
                <WordleTextField
                    id="select-language"
                    select
                    label="Language"
                    defaultValue={''}
                    valueSetter={setValue('language')}
                    validation={validateLanguage}
                    required={true}
                >
                    {Object.entries(LanguagesConstants).map(([key, value]) => (
                        <MenuItem key={key} value={key}>{value.label}</MenuItem>
                    ))}
                </WordleTextField>
                <WordleTextField
                    autoFocus
                    margin="dense"
                    id="good-letters"
                    label="Good Letters"
                    type="text"
                    fullWidth
                    required={false}
                    validation={validateNoSpacedText}
                    valueSetter={setValue('goodLetters')}
                    inputProps={{maxLength: WORD_LEN}}
                />
                <WordleTextField
                    margin="dense"
                    id="bad-letters"
                    label="Bad Letters"
                    type="text"
                    fullWidth
                    required={false}
                    validation={validateNoSpacedText}
                    valueSetter={setValue('badLetters')}
                />
                <ReactCodeInput
                    type='text'
                    fields={WORD_LEN}
                    onChange={handleGreenLetters_onchange}
                    forceUppercase={true}
                />
                <LoadingButton
                    type='submit'
                    variant="contained"
                    size="medium"
                    endIcon={<SearchIcon fontSize='large'/>}
                    loading={loading}
                    sx={[
                        {backgroundColor: '#BFE3B4', height: '50px', top: '2px'},
                        {'&:hover': {color: 'gray', backgroundColor: 'white', fontWeight: '700'}} //, textShadow: '2px 2px 4px #FF0000'
                    ]}
                >
                    Search
                </LoadingButton>
            </HelperFormStyle>
            {words.length > 0 && <WordList words={words} disabledMoreButton={disabledMoreButton} moreWords={handleMoreWords}/>}
        </HelperDiv>


    )
}

export default Helper;