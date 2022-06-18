import {LanguagesConstants} from "../../constants/LanguagesConstants";
import {MSG_CONSTANTS} from "./errorMsg"

export const maxDate = "2032-12-31";
export const minDate = "2022-01-01";

/** Regular expressions */
const empty_re = /^$/;
const at_least_number = /[0-9]{1}/
const at_least_symbol = /[!@#$%^&*]{1}/
const at_least_uppercase = /[A-Z]{1}/
const pwd_re = /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{4,60}$/
const alphabet_re = /^[a-z A-Z]+$/;
const nospacedalphabet_re = /^[a-zA-Z]*$/;
const email_re = /^[a-zA-Z0-9\+\._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/

export const validateLanguage = (formInput) => {
    const isValid = !!LanguagesConstants[formInput.value];
    formInput.setValid(isValid);
    (!isValid) ? formInput.setHelper(MSG_CONSTANTS._incorrect_language_msg) : formInput.setHelper('');
    return isValid;
}

export const validateNoSpacedText = (formInput) => {
    const isValid = nospacedalphabet_re.test(formInput.value);

    formInput.setHelper('');
    if (!isValid) formInput.setHelper(MSG_CONSTANTS._incorrect_nospacedtext_msg);

    formInput.setValid(isValid);
    return isValid;
}

export const validateTextAlpha = (formInput) => {
    const isCorrect = alphabet_re.test(formInput.value);
    const isEmpty = empty_re.test(formInput.value);
    const isValid = (isCorrect && !isEmpty);

    formInput.setHelper('');
    if (!isCorrect) formInput.setHelper(MSG_CONSTANTS._incorrect_text_msg);
    if (isEmpty) formInput.setHelper(MSG_CONSTANTS._empty_msg);

    formInput.setValid(isValid);
    return isValid;
};

export const isDateBetween = ({value, minDate, maxDate}) => {
    const date = new Date(value);
    const _minDate = new Date(minDate);
    const _maxDate = new Date(maxDate);

    return (_minDate <= date && date <= _maxDate);
}

export const validateDate = (formInput) => {
    const isValid = isDateBetween({value: formInput.value, minDate: minDate, maxDate: maxDate});
    formInput.setValid(isValid);
    (!isValid) ? formInput.setHelper(MSG_CONSTANTS._incorrect_date_msg) : formInput.setHelper('');
    return isValid;
};


export const validateVisibility = (formInput) => {
    const isValid = ["PUBLIC", "PRIVATE"].includes(formInput.value);
    formInput.setValid(isValid);
    (!isValid) ? formInput.setHelper(MSG_CONSTANTS._incorrect_visibility_msg) : formInput.setHelper('');
    return isValid;
};

export const validatePassword = (formInput) => {
    const isCorrect = pwd_re.test(formInput.value)
            && at_least_number.test(formInput.value)
            && at_least_symbol.test(formInput.value)
            && at_least_uppercase.test(formInput.value);
    const isEmpty = empty_re.test(formInput.value);
    const isValid = (isCorrect && !isEmpty);

    formInput.setHelper('');
    if (!isCorrect) formInput.setHelper(MSG_CONSTANTS._incorrect_pwd_msg);
    if (isEmpty) formInput.setHelper(MSG_CONSTANTS._empty_pwd_msg);

    formInput.setValid(isValid);
    return isValid;
}

export const validateEmail = (formInput) => {
    const isCorrect = email_re.test(formInput.value);
    const isEmpty = empty_re.test(formInput.value);
    const isValid = (isCorrect && !isEmpty);

    formInput.setHelper('');
    if (!isCorrect) formInput.setHelper(MSG_CONSTANTS._incorrect_email_msg);
    if (isEmpty) formInput.setHelper(MSG_CONSTANTS._empty_email_msg);

    formInput.setValid(isValid);
    return isValid;
}