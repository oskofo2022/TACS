import React from 'react'
import {Button, Divider, List, ListItem, ListItemText, MenuItem, SelectChangeEvent, TextField} from "@mui/material";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import SearchIcon from '@mui/icons-material/Search';
import {LanguagesConstants} from "../../../constants/LanguagesConstants";

const Dictionaries = () => {
    const noMeanings=[];
    //TODO: input validation.
    const [language, setLanguage] = React.useState('');
    const [word, setWord] = React.useState('');
    const [meanings, setmeanings] = React.useState(noMeanings);
    const [wordTitle, setWordTitle] = React.useState('');
    const [pathParam, setPathParam] = React.useState({name:'word', value: word});

    const updateWordParam = (newWord) => {
        setPathParam(
            {name:'word', value: newWord.toLowerCase()}
        )
    }

    const handleLanguageOnChange = (event: SelectChangeEvent) => {
        setLanguage(event.target.value);
    };

    const capitalizeFirstLetter = (string) => (string.charAt(0).toUpperCase() + string.slice(1).toLowerCase());

    const handleWordOnChange = (e) => {
        setWord(e.target.value);
        updateWordParam(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const dictionaryRequest = LanguagesConstants[language].request.from(pathParam);
        const responseJson = await dictionaryRequest.fetchAsJSON();

        //TODO: response validation. Show some error message if server fails.
        if (dictionaryRequest.response.status === 200 && responseJson["meanings"].length > 0) {
            setmeanings(responseJson["meanings"]);
            setWordTitle(capitalizeFirstLetter(word));
        } else {
            setmeanings(noMeanings);
            setWordTitle('');
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
                    helperText="Please insert a word"
                    onChange={handleWordOnChange}
                />
                <TextField
                    id="outlined-select-language"
                    select
                    label="Language"
                    value={language}
                    onChange={handleLanguageOnChange}
                    helperText="Please select the language"
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
            <Typography
                variant="h3"
                noWrap
                component="div"
                sx={{flexGrow: 1, color: 'black'}}
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