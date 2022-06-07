import React from "react";
import {TextField} from "@mui/material";

const WordleTextField = ({validation, valueSetter=()=>{}, ...props}) => {
    const [isValid, setIsValid] = React.useState(true)
    const [helperText, setHelperText] = React.useState('')


    const onchange = (newVal) => {       
        validation({value: newVal, setHelper: (_text)=>setHelperText(_text), setValid: (_isValid)=>setIsValid(_isValid)})
        valueSetter({value: newVal, isValid: isValid})
    }

    React.useEffect(()=>{
        if(props.defaultValue) onchange(props.defaultValue);
    })

    return (
        <TextField
            variant="standard"
            required={true}
            error={!isValid}
            helperText={helperText}
            onChange={(e) => onchange(e.target.value)}
            onInput={(e) => onchange(e.target.value)}
            {...props}
        />
    )
}

export default WordleTextField;