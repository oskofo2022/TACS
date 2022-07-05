import {
    Alert,
    Button,
    Checkbox,
    Collapse,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    List,
    ListItem,
    ListItemButton,
    ListItemIcon,
    ListItemText,
    ListSubheader,
    Typography
} from "@mui/material";
import React from "react";
import {InscriptUserToTournamentRequest} from "../../../request/InscriptUserToTournamentRequest";
import {debounce} from "lodash";
import WordleTextField from "../WordleInput";
import {validateTextAlpha} from "components/formValidations/formValidations";
import {ExpandLess, ExpandMore} from "@material-ui/icons";
import {UsersRequest} from "request/UsersRequest";
import {QueryParams} from "httpUtils/QueryParams";
import AuthContext from "components/context/AuthContext";

const _user_not_exists = 'El usuario no existe.'
const _incorrect_text_msg = 'Sólo se admiten numeros';
const _empty_msg = 'Debe ingresar una palabra.';

const errorMsg = 'Uno de los usuarios ya se encuentra inscripto!'
const successMsg = 'Se agregaron todos los usuarios al torneo.'

const searchUserByName = debounce(
    (name, currentUserName) => {
        const userRequest = UsersRequest.from(
            new QueryParams({
                page: 1,
                pageSize: 5,
                sortOrder: "ASCENDING",
                sortBy: "name",
                name: name
            })
        )
        if(name.trim() === '') return;
        return userRequest.fetchAsPaged().then(r => r.pageItems.filter(u => u.name !== currentUserName))
}, 150)

const AddUser = ({ open, tournamentId, tournamentName, onClose }) => {
    const authContext = React.useContext(AuthContext);
    
    const [userAddSuccess, setUserAddSuccess] = React.useState(false);
    const [userAddError, setUserAddError] = React.useState(false);

    const [userName, setUserName] = React.useState('');
    const [success, setSuccess] = React.useState(false);
    const [userList, setUserList] = React.useState([]);
    const [usersToInscript, setStickyUserSelected] = React.useState([]);
    const [openSelectedUsers, setOpenSelectedUsers] = React.useState(true);

    const handleClick = () => {
        setOpenSelectedUsers(!openSelectedUsers);
    };

    const handleUserAddSuccess = () => {
        setUserAddError(false)
        setUserAddSuccess(true)
    }

    const handleUserAddError = () => {
        setUserAddSuccess(false)
        setUserAddError(true)
    }

    const handleAddUserPost = async () => {
        return Promise.all(
            usersToInscript.map( async (user) => {
                const body = JSON.stringify({ userId: user.id });
                const pathParam = { name: 'tournamentId', value: tournamentId }
                const inscriptUserToTournament = InscriptUserToTournamentRequest.from(pathParam, body);
                return await inscriptUserToTournament.fetch();
            })
        )
        
        // const body = JSON.stringify({ userId: userName });
        // const pathParam = { name: 'tournamentId', value: tournamentId }
        // const inscriptUserToTournament = InscriptUserToTournamentRequest.from(pathParam, body);
        // return await inscriptUserToTournament.fetch();
    }

    const handleCloseAddUserDialogSuccess = () => {
        handleAddUserPost()
            .then(allresponses => allresponses.map(r => (r.ok) ? handleUserAddSuccess() : handleUserAddError()))
            // .then(r => (r.status === 201) ? handleUserAddSuccess() : handleUserAddError())
        // .catch(e => setRedirect(authContext.handleUnauthorized(e)));
    }

    const handleUserNameOnChange = (e) => {
        const value = e.target.value.trim();
        setUserList([]);
        setUserAddSuccess(false);
        setUserAddError(false);
        searchUserByName(value, authContext.name).then(users => setUserList(users))
    }

    const handleCloseSuccessDialog = () => {
        setSuccess(false);
    }

    const handleToggle = (user) => () => {
        const currentIndex = usersToInscript.findIndex(u => u.id === user.id);
        const userSelected = [...usersToInscript];
        console.log('handleToggle', currentIndex)
        if (currentIndex === -1) {
            userSelected.push(user);
        } else {
            userSelected.splice(currentIndex, 1);
        }

        setStickyUserSelected(userSelected);
    };

    

    return (
        <React.Fragment>
            <Dialog open={open} onClose={onClose} className='addusermodal' fullWidth>
                <DialogTitle>
                    <Typography variant="h5" sx={{ color: "green" }} textAlign="center">Add User</Typography>
                </DialogTitle>
                <Collapse in={userAddSuccess} sx={{ minWidth: 'inherit', maxWidth: 'inherit' }} >
                    <Alert variant="filled" sx={{ mb: 2, minWidth: 'inherit', maxWidth: 'inherit' }} severity="success">
                        {successMsg}
                    </Alert>
                </Collapse>
                <Collapse in={userAddError} sx={{ minWidth: 'inherit', maxWidth: 'inherit' }} >
                    <Alert variant="filled" sx={{ mb: 2, minWidth: 'inherit', maxWidth: 'inherit' }} severity="error">
                        {errorMsg}
                    </Alert>
                </Collapse>
                <DialogContent>
                    <WordleTextField
                        id="userName"
                        label="Search user by name"
                        type="text"
                        required={false}
                        validation={validateTextAlpha}
                        onChange={handleUserNameOnChange}
                    />
                    <List
                        sx={{
                            width: '100%',
                            maxWidth: 360,
                            bgcolor: 'background.paper',
                            position: 'relative',
                            overflow: 'auto',
                            maxHeight: 300,
                            '& ul': { padding: 0 },
                        }}
                        subheader={<li />}
                    >

                        <ListItemButton onClick={handleClick}>
                            <ListItemText primary="Selected Users" />
                            {openSelectedUsers ? <ExpandLess /> : <ExpandMore />}
                        </ListItemButton>
                        {usersToInscript.map((user) => (
                            <Collapse in={openSelectedUsers} timeout="auto" unmountOnExit>
                                <List component="div" disablePadding>
                                    <ListItem
                                        key={`item-${user.id}`}
                                        disablePadding
                                    >
                                        <ListItemButton role={undefined} onClick={handleToggle(user)} dense>
                                            <ListItemIcon>
                                                <Checkbox
                                                    edge="start"
                                                    checked={true}
                                                    tabIndex={-1}
                                                    disableRipple
                                                />
                                            </ListItemIcon>
                                            <ListItemText primary={`${user.name}`} />
                                        </ListItemButton>
                                    </ListItem>
                                </List>
                            </Collapse>

                        ))}
                        <ListSubheader>{`Users`}</ListSubheader>
                        {userList.map((user) => {
                            const labelId = `checkbox-list-label-${user.id}`
                            return (
                                <ListItem
                                    key={`item-${user.id}`}
                                    disablePadding
                                >
                                    <ListItemButton role={undefined} onClick={handleToggle(user)} dense>
                                        <ListItemIcon>
                                            <Checkbox
                                                edge="start"
                                                checked={usersToInscript.findIndex(u => u.id === user.id) !== -1}
                                                tabIndex={-1}
                                                disableRipple
                                                inputProps={{ 'aria-labelledby': labelId }}
                                            />
                                        </ListItemIcon>
                                        <ListItemText id={labelId} primary={`${user.name}`} />
                                    </ListItemButton>
                                </ListItem>
                            )
                        })}

                    </List>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose}>Cancel</Button>
                    <Button onClick={handleCloseAddUserDialogSuccess}>Add</Button>
                </DialogActions>
            </Dialog>
            <Dialog open={success} onClose={handleCloseSuccessDialog} className='successmodal' style={{ backgroundColor: 'transparent' }}>
                <DialogTitle>
                    <Typography variant="h6" sx={{ color: "green" }} textAlign="center">User {userName} added to {tournamentName}</Typography>
                </DialogTitle>
                <DialogActions>
                    <Button onClick={handleCloseSuccessDialog}>Close</Button>
                </DialogActions>
            </Dialog>
        </React.Fragment>
    )
};
export default AddUser;