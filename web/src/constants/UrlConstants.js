const API_DOMAIN = 'localhost:8080';
const API_URL = `http://${API_DOMAIN}/api`;

const LOGIN = `${API_URL}/logins`;

const PUBLIC_TOURNAMENTS = `${API_URL}/tournaments/public`;
const INSCRIPTIONS = `${API_URL}/users/myself/inscriptions/tournaments`;
const POSITIONS = `${API_URL}/users/myself/inscriptions/tournaments/positions`;

const DICTIONARIES = `${API_URL}/dictionaries`;
const ENGLISH_DICTIONARY = `${DICTIONARIES}/ENGLISH/words/{word}`;
const SPANISH_DICTIONARY = `${DICTIONARIES}/SPANISH/words/{word}`;

export const UrlConstants = Object.freeze({
    API_DOMAIN: API_DOMAIN,
    API_URL: API_URL,
    LOGIN: LOGIN,
    PUBLIC_TOURNAMENTS: PUBLIC_TOURNAMENTS,
    INSCRIPTIONS: INSCRIPTIONS,
    POSITIONS: POSITIONS,
    DICTIONARIES: DICTIONARIES,
    ENGLISH_DICTIONARY: ENGLISH_DICTIONARY,
    SPANISH_DICTIONARY: SPANISH_DICTIONARY,
});
