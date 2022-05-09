import React from 'react'
import {Button, Divider, List, ListItem, ListItemText, MenuItem, SelectChangeEvent, TextField} from "@mui/material";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import * as URL from "../../../constants/wordleURLs";
import {Request} from "../../../httpUtils/Request";
import Typography from "@mui/material/Typography";
import SearchIcon from '@mui/icons-material/Search';


const Dictionaries = () => {
    const noMeanings=[];
    //TODO: input validation.
    const [language, setLanguage] = React.useState('');
    const [word, setWord] = React.useState('');
    const [meanings, setmeanings] = React.useState(noMeanings);
    const [wordTitle, setWordTitle] = React.useState('');

    const handleLanguageOnChange = (event: SelectChangeEvent) => {
        setLanguage(event.target.value);
    };

    const capitalizeFirstLetter = (string) => (string.charAt(0).toUpperCase() + string.slice(1));

    const handleWordOnChange = (e) => setWord(e.target.value);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const url = language === 'SPANISH' ? URL.SPANISH_DICTIONARY : URL.ENGLISH_DICTIONARY;

        const response = await fetch(url.replace('{word}', word), Request.get());
        const responseJson = await response.json();
        //TODO: response validation. Show some error message if server fails.
        if (response.status === 200 && responseJson["meanings"].length > 0) {
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
                    <MenuItem value={'SPANISH'}>Spanish</MenuItem>
                    <MenuItem value={'ENGLISH'}>English</MenuItem>
                </TextField>
                <Button type='Submit' variant="contained" size="medium" endIcon={<SearchIcon fontSize='large'/>} sx={[
                    {backgroundColor:'#BFE3B4', height:'50px', top:'2px'},
                    {'&:hover': {color:'gray', backgroundColor:'white', fontWeight: '700'}} //, textShadow: '2px 2px 4px #FF0000'
                ]}>Search</Button>
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
                    <React.Fragment>
                        <ListItem key={index} alignItems="flex-start">
                            <Typography
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