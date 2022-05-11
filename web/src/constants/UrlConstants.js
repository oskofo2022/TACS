
export const UrlConstants = Object.freeze({
    API_DOMAIN: 'localhost:8080',
    API_URL: `http://${(this.API_DOMAIN)}/api`,
    LOGIN: `${(this.API_URL)}/logins`,
    PUBLIC_TOURNAMENTS: `${(this.API_URL)}/tournaments/public`,
    INSCRIPTIONS: `${(this.API_URL)}/users/myself/inscriptions/tournaments`,
    POSITIONS: `${(this.API_URL)}/users/myself/inscriptions/tournaments/positions`,
    DICTIONARIES: `${(this.API_URL)}/dictionaries`,
    ENGLISH_DICTIONARY: `${(this.DICTIONARIES)}/ENGLISH/words/{word}`,
    SPANISH_DICTIONARY: `${(this.DICTIONARIES)}/SPANISH/words/{word}`
})