import React from "react";
import {DataGrid, GridColDef} from "@mui/x-data-grid";
import {NavLink} from 'react-router-dom';
import {QueryParams} from '../../../httpUtils/QueryParams'
import {InscriptionsRequest} from "../../../request/InscriptionsRequest";
import {TournamentsResponse} from "../../../response/TournamentsResponse";
import {EmojiEventsTwoTone} from "@material-ui/icons";
import IconButton from "@mui/material/IconButton";
import AuthContext from "../../context/AuthContext";

const MyTournaments = () => {
    const authContext = React.useContext(AuthContext);

    const [redirectUnauthorized, setRedirectUnauthorized] = React.useState(null);

    const [data, setData] = React.useState({
        loading: true,
        rows: [],
        totalRows: 0,
        pageSize: 2,
        page: 1,
        rowsPerPageOptions: [2, 5, 10, 20],
        sortBy: 'tournament.id',
        sortOrder:'ASCENDING',
    });

    function createData(
        inscription
    ) {
        return {
            ...inscription
        };
    }

    const updateData = (k, v) => setData((prev) => ({ ...prev, [k]: v }));

    const columns: GridColDef[] = [
        { field: 'id', headerName: 'ID', width: 70, sortable: false, },
        { field: 'name', headerName: 'Name', width: 200, sortable: false, },
        { field: 'language', headerName: 'Language', width: 130, sortable: false, },
        { field: 'beginDate', headerName: 'Begin date', width: 130, sortable: false, },
        { field: 'endDate', headerName: 'End date', width: 130, sortable: false, },
        { field: 'tournamentState', headerName: 'State', width: 130, sortable: false, },
        { field: 'positionURL', headerName: 'Positions', width: 130, sortable: false,
            renderCell:
                (positions) => (
                    <NavLink to={positions.row.positionURL} style={{textDecoration: 'none', color: 'yellow'}}>
                        <IconButton aria-label="Positions">
                            <EmojiEventsTwoTone />
                        </IconButton>
                    </NavLink>
                )},
    ];

    const handleGetInscriptions = async (inscriptionsRequest): Promise<TournamentsResponse> =>  {
        updateData("loading", true);
        return await inscriptionsRequest.fetchAsPaged();
    }

    const handleSetRows = (_rows) => {
        const rows = _rows.map(i => createData(i));
        updateData("rows", rows);
    }

    React.useEffect(() => {


        const inscriptionsRequest = InscriptionsRequest.from(
            new QueryParams({
                page: data.page,
                pageSize: data.pageSize,
                sortBy: data.sortBy,
                sortOrder: data.sortOrder
            })
        )

        handleGetInscriptions(inscriptionsRequest)
            .then(r => {
                updateData("totalRows", r.totalCount);
                handleSetRows(r.pageItems);
            })
            .catch(e => setRedirectUnauthorized(authContext.handleUnauthorized(e)))
            .finally( () => updateData("loading", false));


    }, [data.page, data.pageSize]);



    // const [template, setTemplate] = React.useState(a());

    return (
        <React.Fragment>
            {redirectUnauthorized}
            <div style={{ height: '60%', width: '100%' }}>
                <DataGrid
                    disableSelectionOnClick
                    disableColumnSelector={true}
                    disableDensitySelector={true}
                    dat
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

export default  MyTournaments;