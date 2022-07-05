import { Button, styled, Typography } from "@mui/material";
import React from "react";

const WBox = styled('div')(({ theme }) => ({
    display: 'flex',
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'left',
    alignItems: 'center',
    maxWidth: '500px',
    padding: theme.spacing(2),
    backgroundColor: 'white',
    borderRadius: '25px',
    borderColor: '#e0c8ac',
    borderStyle: 'solid',

    '& .MuiTextField-root': {
        margin: theme.spacing(1),
        // width: '3000px',
    },
    '& .MuiButtonBase-root': {
        margin: theme.spacing(2),
    },
    marginLeft: '1em',
    marginTop: '2em',
    // margin: '50em',
}));

const WordBox = styled('div')(({ theme }) => ({
    flex: 0,
    position: 'relative',
    justifyContent: 'center',
    alignItems: 'center',
    padding: theme.spacing(2),
    backgroundColor: '#E3E3E3',
    borderRadius: '6px',
    margin: '3px',
}));

const WordList = ({ words, disabledMoreButton, moreWords }) => {
    return (
        <WBox>
            {words.map(i =>
                <WordBox>
                    <Typography>{i}</Typography>
                </WordBox>
            )}
            <Button
                variant="contained"
                size="medium"
                disabled={disabledMoreButton}
                onClick={moreWords}
                sx={[
                    { backgroundColor: '#BFE3B4', height: '50px', top: '2px', flexBasis: '100%' },
                    { '&:hover': { color: 'gray', backgroundColor: 'white', fontWeight: '700' } } //, textShadow: '2px 2px 4px #FF0000'
                ]}
            >
                More
            </Button>
        </WBox>
    )
}

export default WordList;