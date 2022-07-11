import React from "react";
import AuthContext from "components/context/AuthContext";
import { Container } from "@mui/material";
import { QueryParams } from "httpUtils/QueryParams";
import { MyGamesRequest } from "request/MyGamesRequest";
import { DataGrid } from "@mui/x-data-grid/DataGrid";


const MyGames = () => {
    const authContext = React.useContext(AuthContext);

    const [redirectUnauthorized, setRedirectUnauthorized] = React.useState(null);

    const [data, setData] = React.useState({
        loading: true,
        rows: [],
        totalRows: 0,
        pageSize: 5,
        page: 1,
        rowsPerPageOptions: [5, 10, 15, 20],
        sortBy: 'date',
        sortOrder:'DESCENDING',
    });

    const updateData = (k, v) => setData((prev) => ({ ...prev, [k]: v }));

    const columns: GridColDef[] = [
        { field: 'date', headerName: 'Date', width: 200, sortable: false, },
        { field: 'language', headerName: 'Language', width: 130, sortable: false, },
        { field: 'guessesCount', headerName: 'Guesses', width: 130, sortable: false, }
    ];

    const handleGetMyGames = async (myGamesRequest) =>  {
        updateData("loading", true);
        return await myGamesRequest.fetchAsPaged();
    }

    React.useEffect(() => {


        const myGamesRequest = MyGamesRequest.from(
            new QueryParams({
                page: data.page,
                pageSize: data.pageSize,
                sortBy: data.sortBy,
                sortOrder: data.sortOrder,
            })
        )

        handleGetMyGames(myGamesRequest)
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
    )
}

export default MyGames;