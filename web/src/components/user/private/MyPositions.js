import React from "react";
import { useParams } from 'react-router-dom'
import {DataGrid, GridColDef} from "@mui/x-data-grid";
import {QueryParams} from '../../../httpUtils/QueryParams'
import {TournamentsResponse} from "../../../response/TournamentsResponse";
import AuthContext from "../../context/AuthContext";
import {PositionsRequest} from "../../../request/PositionsRequest";

const MyPositions = () => {
    const authContext = React.useContext(AuthContext);
    const tournamentName = useParams();
    // debugger;
    const [redirectUnauthorized, setRedirectUnauthorized] = React.useState(null);
    const [data, setData] = React.useState({
        loading: true,
        rows: [],
        totalRows: 0,
        pageSize: 2,
        page: 1,
        rowsPerPageOptions: [2, 5, 10, 20],
        sortOrder:'ASCENDING',
    });

    function createData(
        positions
    ) {
        return {
            ...positions
        };
    }
    const updateData = (k, v) => setData((prev) => ({ ...prev, [k]: v }));

    const columns: GridColDef[] = [
        { field: 'position', headerName: 'Position', width: 70, sortable: false, },
        { field: 'name', headerName: 'Name', width: 200, sortable: false, },
        { field: 'points', headerName: 'Points', width: 130, sortable: false, },
    ];
    const handleGetPositions = async (positionsRequest) : Promise<TournamentsResponse> => {
        updateData("loading", true);
        return await positionsRequest.fetchAsPaged();
    }
    const handleSetRows = (_rows) => {
        const rows = _rows.map(i => createData(i));
        updateData("rows", rows);
    }

    React.useEffect(() => {
        const positionsRequest = PositionsRequest.from(
            new QueryParams({
                page: data.page,
                pageSize: data.pageSize,
                sortOrder: data.sortOrder,
                tournament: tournamentName.torneoName,
            })
        )
        handleGetPositions(positionsRequest)
            .then(r => {
                console.log("llegue");
                updateData("totalRows", r.totalCount);
                handleSetRows(r.pageItems);
            })
            .catch(e => setRedirectUnauthorized(authContext.handleUnauthorized(e)))
            .finally( () => updateData("loading", false));
    }, [data.page, data.pageSize]);

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
                    getRowId={(row) => row.id}
                    pagination
                    loading={data.loading}
                    rows={data.rows}
                    columns={columns}
                    page={data.page-1}
                    pageSize={data.pageSize}
                    rowsPerPageOptions={data.rowsPerPageOptions}
                    rowCount={data.totalRows}
                    paginationMode='server'
                    onPageChange={ (p) => {updateData("page", p + 1);} }
                    onPageSizeChange={ (ps) => {updateData("page", 1); updateData("pageSize", ps);} }
                />
            </div>
        </React.Fragment>
    );
};
export default  MyPositions;