import React from "react";
import AuthContext from "../../context/AuthContext";
import {DataGrid, GridColDef} from "@mui/x-data-grid";
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import IconButton from "@mui/material/IconButton";
import {TournamentsResponse} from "../../../response/TournamentsResponse";
import {QueryParams} from "../../../httpUtils/QueryParams";
import AddUser from "./AddUser";
import {MyTournamentsRequest} from "../../../request/MyTournamentsRequest";

const MyTournaments = () => {
    const authContext = React.useContext(AuthContext);

    const [redirectUnauthorized, setRedirectUnauthorized] = React.useState(null);

    const [addUserDialogOpen, setAddUserDialogOpen] = React.useState(false);
    const [tournamentId, setTournamentId] = React.useState(null);
    const [tournamentName, setTournamentName] = React.useState(null);

    const [data, setData] = React.useState({
       loading: true,
       rows: [],
       totalRows: 0,
       pageSize: 2,
       page: 1,
       rowsPerPageOptions: [2, 5, 10, 20],
       sortBy: 'name',
       sortOrder:'ASCENDING',
   });

    const updateData = (k, v) => setData((prev) => ({ ...prev, [k]: v }));

    const handleAddUserOnClick = (t) => () => {
        setTournamentId(t.id);
        setTournamentName(t.name);
        setAddUserDialogOpen(true);
    }

    const handleCloseAddUserDialog = () => {
        setAddUserDialogOpen(false);
    }

    const columns: GridColDef[] = [
        { field: 'id', headerName: 'ID', width: 70, sortable: false, },
        { field: 'name', headerName: 'Name', width: 200, sortable: false, },
        { field: 'language', headerName: 'Language', width: 130, sortable: false, },
        { field: 'beginDate', headerName: 'Begin date', width: 130, sortable: false, },
        { field: 'endDate', headerName: 'End date', width: 130, sortable: false, },
        { field: 'tournamentState', headerName: 'State', width: 130, sortable: false, },
        { field: 'addUser', headerName: 'Add User', width: 130, sortable: false,
            renderCell:
                (t) => (
                    <IconButton aria-label="Add User" onClick={handleAddUserOnClick(t.row)}>
                        <PersonAddIcon />
                    </IconButton>
                )},
    ];

    const handleGetTournaments = async (myTournamentsRequest): Promise<TournamentsResponse> =>  {
        updateData("loading", true);
        return await myTournamentsRequest.fetchAsPaged();
    }


    React.useEffect(() => {


        const myTournamentsRequest = MyTournamentsRequest.from(
            new QueryParams({
                page: data.page,
                pageSize: data.pageSize,
                sortBy: data.sortBy,
                sortOrder: data.sortOrder,
            })
        )

        handleGetTournaments(myTournamentsRequest)
            .then(r => {
                updateData("totalRows", r.totalCount);
                updateData("rows", r.pageItems);
            })
            .catch(e => setRedirectUnauthorized(authContext.handleUnauthorized(e)))
            .finally( () => updateData("loading", false));


    }, [data.page, data.pageSize]);

    return (
        <React.Fragment>
            {redirectUnauthorized}
            <AddUser open={addUserDialogOpen} tournamentId={tournamentId} tournamentName={tournamentName} onClose={handleCloseAddUserDialog}/>
            <div style={{ height: '60%', width: '100%' }}>
                <DataGrid
                    disableSelectionOnClick
                    disableColumnSelector={true}
                    disableDensitySelector={true}
                    density="compact"
                    autoHeight
                    pagination
                    loading={data.loading}
                    rows={data.rows}
                    columns={columns}
                    page={data.page-1}
                    pageSize={data.pageSize}
                    rowsPerPageOptions={data.rowsPerPageOptions}
                    rowCount={data.totalRows}
                    paginationMode='server'
                    // onCellClick={handleCellClick}
                    onPageChange={ (p) => {updateData("page", p + 1);} }
                    onPageSizeChange={ (ps) => {updateData("page", 1); updateData("pageSize", ps);} }
                />
            </div>
        </React.Fragment>
    );
};

export default MyTournaments;
