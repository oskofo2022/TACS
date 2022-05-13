import React from 'react'
import {Button, Divider, List, ListItem, ListItemText, MenuItem, SelectChangeEvent, TextField} from "@mui/material";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import SearchIcon from '@mui/icons-material/Search';
import {LanguagesConstants} from "../../../constants/LanguagesConstants";

const _incorrect_word_msg = 'Sólo se admiten letras y espacios';
const _empty_word_msg = 'Debe ingresar una palabra.';
const _incorrect_language_msg = 'Debe seleccionar un idioma';

const Dictionaries = () => {
    const noMeanings=[];

    const [language, setLanguage] = React.useState('');
    const [languageValid, setLanguageValid] = React.useState(true);
    const [languageHelper, setLanguageHelper] = React.useState('');

    const [word, setWord] = React.useState('');
    const [wordValid, setWordValid] = React.useState(true);
    const [wordHelper, setWordHelper] = React.useState('');

    const [meanings, setmeanings] = React.useState(noMeanings);
    const [wordTitle, setWordTitle] = React.useState('');
    const [pathParam, setPathParam] = React.useState({name:'word', value: word});
    const [notFound, setNotFound] = React.useState(false);
    const [notFoundedWord, setNotFoundedWord] = React.useState('');


    const updateWordParam = (_word) =>
        setPathParam({
            name:'word',
            value: _word.toLowerCase()
        });

    const capitalizeFirstLetter = (string) => (string.charAt(0).toUpperCase() + string.slice(1).toLowerCase());

    const validateLanguage = (value) => {
        const isValid = !!LanguagesConstants[value];
        setLanguageValid(isValid);
        (!isValid) ? setLanguageHelper(_incorrect_language_msg) : setLanguageHelper('');
        return isValid;
    }

    const validateWord = (value) => {
        const empty_re = new RegExp("^$");
        const correct_re = new RegExp("^[a-z A-Z]+$");
        const isCorrect = correct_re.test(value);
        const isEmpty = empty_re.test(value);
        const isValid = (isCorrect && !isEmpty);

        setWordHelper('');
        if(!isCorrect) setWordHelper(_incorrect_word_msg);
        if(isEmpty) setWordHelper(_empty_word_msg);

        setWordValid(isValid);
        return isValid;
    }

    const allValidations = [{f:validateLanguage, v:language}, {f:validateWord, v:word}];

    const handleLanguageOnChange = (e: SelectChangeEvent) => {
        const value = e.target.value;
        validateLanguage(value);
        setLanguage(value);
    };

    const handleWordOnChange = (e) => {
        const value = e.target.value;
        validateWord(value);
        setWord(value);
        updateWordParam(value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const isValid = allValidations.reduce((prev, validation) => validation.f(validation.v) && prev, true)
        if(!isValid) return;

        const dictionaryRequest = LanguagesConstants[language].getRequest(pathParam);
        const responseJson = await dictionaryRequest.fetchAsJSON();

        //TODO: response validation. Show some error message if server fails.
        if (dictionaryRequest.response.status === 200 && responseJson["meanings"].length > 0) {
            setNotFound(false);
            setmeanings(responseJson["meanings"]);
            setWordTitle(capitalizeFirstLetter(word));
        } else {
            setmeanings(noMeanings);
            setWordTitle('');
            setNotFound(true);
            setNotFoundedWord(word);
        }
    }

    return (
        <Container sx={{marginTop:'5%'}}>
            <Box onSubmit={handleSubmit}
                fullWidth
                component="form"
                sx={{
                    '& > :not(style)': {m: 1, width: '25ch'},
                }}
                noValidate>
                <TextField
                    fullWidth
                    id="word"
                    label="Search definition"
                    variant="outlined"
                    helperText={wordHelper}
                    onChange={handleWordOnChange}
                    error={!wordValid}
                    required={true}
                />
                <TextField
                    id="outlined-select-language"
                    select
                    label="Language"
                    value={language}
                    onChange={handleLanguageOnChange}
                    helperText={languageHelper}
                    error={!languageValid}
                    required={true}
                >
                    {Object.entries(LanguagesConstants).map(([key, value])=>(
                        <MenuItem key={key} value={key}>{value.label}</MenuItem>
                    ))}
                </TextField>
                <Button
                    type='Submit'
                    variant="contained"
                    size="medium"
                    endIcon={<SearchIcon fontSize='large'/>}
                    sx={[
                        {backgroundColor:'#BFE3B4', height:'50px', top:'2px'},
                        {'&:hover': {color:'gray', backgroundColor:'white', fontWeight: '700'}} //, textShadow: '2px 2px 4px #FF0000'
                    ]}
                >
                    Search
                </Button>
            </Box>
            {!!notFound && <Typography
                variant="h5"
                noWrap
                component="div"
                sx={{flexGrow: 1, color: 'black', marginTop: '8%', marginLeft: '5%'}}
            >
                No se encontraron resultados para la palabra {notFoundedWord}
            </Typography>}
            <Typography
                variant="h3"
                noWrap
                component="div"
                sx={{flexGrow: 1, color: 'black',  marginTop: '5%'}}
            >
                {wordTitle}
            </Typography>
            <List sx={{ width: '100%', maxWidth: 360 }}>
                {!!meanings.length && <Divider variant="inset" component="li"/>}
                {meanings.map( (meaning, index) => (
                    <React.Fragment key={index}>
                        <ListItem key={index} alignItems="flex-start">
                            <Typography
                                key={index}
                                sx={{ display: "inline", margin: "10%"}}
                                component="span"
                                variant="body2"
                                color="text.primary"
                                fontSize="1.5rem"
                            >
                                {index+1}.
                            </Typography>
                            <ListItemText
                                primary={capitalizeFirstLetter(meaning.type)}
                                secondary={meaning.definition}
                            />
                        </ListItem>
                        <Divider variant="inset" component="li" />
                    </React.Fragment>
                    ))
                }

            </List>
        </Container>
    );

}

export default Dictionaries;