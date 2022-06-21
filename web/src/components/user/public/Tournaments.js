import * as React from 'react'
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { QueryParams } from "../../../httpUtils/QueryParams";
import { TournamentsRequest } from "../../../request/TournamentsRequest";
import { InscriptionsRequest } from "../../../request/InscriptionsRequest";
import { TournamentsResponse } from "../../../response/TournamentsResponse";
import IconButton from "@mui/material/IconButton";
import LoginIcon from '@mui/icons-material/Login';
import HowToRegIcon from '@mui/icons-material/HowToReg';
import { InscriptMyselfRequest } from "../../../request/InscriptMyselfRequest";
import AuthContext from "../../context/AuthContext";
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, Stack, Typography } from "@mui/material";
import ReactNbsp from 'react-nbsp'
import StatusCodeHandler from 'errors/StatusCodeHandler';

const Tournaments = () => {

    const authContext = React.useContext(AuthContext);
    const [redirect, setRedirect] = React.useState(null);
    const [inscriptionSuccessDialogOpen, setInscriptionSuccessDialogOpen] = React.useState(false);
    const [inscriptionFailedDialogOpen, setInscriptionFailedDialogOpen] = React.useState(false);
    const [tournamentName, setTournamentName] = React.useState('');
    const [tournamentLanguage, setTournamentLanguage] = React.useState('');
    const [tournamentBeginDate, setTournamentBeginDate] = React.useState('');
    const [tournamentEndDate, setTournamentEndDate] = React.useState('');

    const [data, setData] = React.useState({
        loading: true,
        rows: [],
        totalRows: 0,
        pageSize: 2,
        page: 1,
        rowsPerPageOptions: [2, 5, 10, 20],
        sortBy: 'id',
        sortOrder: 'ASCENDING',
    });

    const updateData = (k, v) => setData((prev) => ({ ...prev, [k]: v }));

    const columns: GridColDef[] = [
        { field: 'name', headerName: 'Name', width: 200, sortable: false, },
        { field: 'language', headerName: 'Language', width: 130, sortable: false, },
        { field: 'beginDate', headerName: 'Begin date', width: 130, sortable: false, },
        { field: 'endDate', headerName: 'End date', width: 130, sortable: false, },
        { field: 'state', headerName: 'State', width: 130, sortable: false, },
        {
            field: 'inscription', headerName: 'Inscription', width: 130, sortable: false, hide: !authContext.authenticated, renderCell:
                (t) => {
                    const row = t.row
                    const inscripted = !!row.inscripted
                    // console.log({inscripted});
                    // if(!inscripted) console.log(JSON.stringify(row))
                    return  (
                            <React.Fragment>
                                {inscripted ? (
                                <IconButton
                                    key={'tournamentInscripted'}
                                    size="large"
                                    aria-label="Inscripted"
                                    title="Inscripted"
                                    onClick={e => e.preventDefault()}
                                    color='info'
                                    disableRipple={true}
                                    sx={{ cursor: 'default' }}
                                >
                                    <HowToRegIcon />
                                </IconButton>
                                ) :
                                (<IconButton
                                    key={'tournamentInscriptButton'}
                                    size="large"
                                    aria-label="Inscript to tournament"
                                    title="Press for sign to the tournament"
                                    onClick={handleTournamentInscription(t.row)}
                                    color="inherit"
                                >
                                    <LoginIcon />
                                </IconButton>
                                )}
                            </React.Fragment>
                        )
                }
        },
    ];

    const handleCloseSuccessDialog = () => {
        setInscriptionSuccessDialogOpen(false);
    };
    const handleCloseFailedDialog = () => {
        setInscriptionFailedDialogOpen(false);
    };

    const handlePostInscription = (tournamentId) => {
        const pathParams = { name: 'tournamentId', value: tournamentId }
        const inscriptMyselfRequest = InscriptMyselfRequest.from(pathParams);
        return inscriptMyselfRequest.fetch();
    }

    const inscriptTournament = (tournament) => {
        tournament.inscripted = true
        return tournament
    }

    const handleTournamentInscription = (tournament) => () => {
        setTournamentName(tournament.name);
        setTournamentLanguage(tournament.language);
        setTournamentBeginDate(tournament.beginDate);
        setTournamentEndDate(tournament.endDate);
        updateData("rows", data.rows.map(t => (t.id === tournament.id)? inscriptTournament(t):t))
        handlePostInscription(tournament.id)
            .then(StatusCodeHandler)
            .then(_ => setInscriptionSuccessDialogOpen(true))
            .catch(e => setRedirect(authContext.handleUnauthorized(e)))
            .catch(e => setInscriptionFailedDialogOpen(true))
    }

    const request = React.useRef(true);

    const handleGetPublicTournaments = async (tournamentRequest): Promise<TournamentsResponse> => {
        updateData("loading", true);
        return await tournamentRequest.fetchAsPaged();
    }

    const handleGetPublicInscriptions = async (inscriptionRequest) => {
        updateData("loading", true);
        return await inscriptionRequest.fetchAsPaged();
    }

    const updateRowsWithInscriptions = (rows) => {
        const inscriptionsRequest = InscriptionsRequest.from(
            new QueryParams({
                sortBy: 'name',
                sortOrder: data.sortOrder,
                tournamentIds: rows.map(r => r.id),
            })
        )
        handleGetPublicInscriptions(inscriptionsRequest)
            .then(r => {
                const inscriptionIds = r.pageItems.map(t => t.id);
                const rowsWithInscription = rows.map(t => { (inscriptionIds.includes(t.id)) ? t.inscripted = true : t.inscripted = false; return t; })
                updateData("rows", rowsWithInscription);                        
            })
    }

    React.useEffect(() => {
        // console.log({auth: authContext.authenticated, current: request.current})
        // if(authContext.authenticated) columns.push(inscriptionColumn);

        const tournamentRequest = TournamentsRequest.from(
            new QueryParams({
                page: data.page,
                pageSize: data.pageSize,
                sortBy: data.sortBy,
                sortOrder: data.sortOrder
            })
        )


        const response = handleGetPublicTournaments(tournamentRequest);
        response.then(r => {
            const rows = r.pageItems
            updateData("totalRows", r.totalCount);
            (!!authContext.authenticated) ? updateRowsWithInscriptions(rows) : updateData("rows", rows);
            updateData("loading", false);          
        });
    }, [authContext.authenticated, data.page, data.pageSize]);

    const tournamentLabel = (tournamentLabel, tournamentValue) => (
        <Typography
            variant="h6"
            noWrap
            component="div"
            sx={{ flexGrow: 1, color: 'black' }}
        >
            {tournamentLabel}:
            <ReactNbsp />
            <Typography
                variant="h6"
                noWrap
                component="div"
                sx={{ flexGrow: 1, color: 'gray', display: 'inline' }}
            >
                {tournamentValue}
            </Typography>
        </Typography>
    )

    const DialogContentLabels = () => (
        <Stack noValidate spacing={2}>
            {tournamentLabel('Tournament name', tournamentName)}
            {tournamentLabel('Language', tournamentLanguage)}
            {tournamentLabel('Begin date', tournamentBeginDate)}
            {tournamentLabel('End date', tournamentEndDate)}
        </Stack>
    );

    return (
        <React.Fragment>
            {redirect}
            <Dialog open={inscriptionSuccessDialogOpen} onClose={handleCloseSuccessDialog} className='signinmodal' fullWidth>
                <DialogTitle>
                    <Typography variant="h5" sx={{ color: "green" }} textAlign="center">Inscription Success</Typography>
                </DialogTitle>
                <DialogContent>
                    {DialogContentLabels()}
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseSuccessDialog}>Close</Button>
                </DialogActions>
            </Dialog>
            <Dialog open={inscriptionFailedDialogOpen} onClose={handleCloseFailedDialog} className='signinmodal' fullWidth>
                <DialogTitle>
                    <Typography variant="h5" sx={{ color: "red" }} textAlign="center">Inscription Failed</Typography>
                </DialogTitle>
                <DialogActions>
                    <Button onClick={handleCloseFailedDialog}>Close</Button>
                </DialogActions>
            </Dialog>
            <div style={{ height: '60%', width: '100%' }}>
                <DataGrid
                    density="compact"
                    autoHeight
                    pagination
                    loading={data.loading}
                    rows={data.rows}
                    columns={columns}
                    page={data.page - 1}
                    pageSize={data.pageSize}
                    rowsPerPageOptions={data.rowsPerPageOptions}
                    rowCount={data.totalRows}
                    paginationMode='server'
                    onPageChange={(p) => { updateData("page", p + 1); request.current = true; }}
                    onPageSizeChange={(ps) => { updateData("page", 1); updateData("pageSize", ps); request.current = true; }}
                />
            </div>
        </React.Fragment>
    )
}
export default Tournaments;