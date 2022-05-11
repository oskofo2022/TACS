export const API_DOMAIN='localhost:8080'
export const API_URL=`http://${API_DOMAIN}/api`

export const LOGIN=`${API_URL}/logins`
export const PUBLIC_TOURNAMENTS=`${API_URL}/tournaments/public`

export const INSCRIPTIONS=`${API_URL}/users/myself/inscriptions/tournaments`
export const POSITIONS=`${API_URL}/users/myself/inscriptions/tournaments/positions`

export const DICTIONARIES=`${API_URL}/dictionaries`

export const ENGLISH_DICTIONARY = `${DICTIONARIES}/ENGLISH/words/{word}`
export const SPANISH_DICTIONARY = `${DICTIONARIES}/SPANISH/words/{word}`
