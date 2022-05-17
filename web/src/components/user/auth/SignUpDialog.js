import React from "react";
import {Dialog} from "@mui/material";
import SignUpForm from "./SignUpForm";

function SignUpDialog({ open, handleClose }) {


    return (
        <Dialog open={open} onClose={handleClose}>
          <SignUpForm closeFunc={handleClose} />
        </Dialog>
    );
}

export default SignUpDialog;
