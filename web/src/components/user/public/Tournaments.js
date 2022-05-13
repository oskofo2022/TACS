import * as React from 'react'
import {DataGrid, GridColDef} from '@mui/x-data-grid';
import {QueryParams} from "../../../httpUtils/QueryParams";
import {TournamentsRequest} from "../../../request/TournamentsRequest"
import {TournamentsResponse} from "../../../response/TournamentsResponse";

const Tournaments = () => {

    const [data, setData] = React.useState({
        loading: true,
        rows: [],
        totalRows: 0,
        pageSize: 2,
        page: 1,
        rowsPerPageOptions: [2, 5, 10, 20],
        sortBy: 'id',
        sortOrder:'ASCENDING',
      });
    
    const updateData = (k, v) => setData((prev) => ({ ...prev, [k]: v }));

    const columns: GridColDef[] = [
        { field: 'id', headerName: 'ID', width: 70, sortable: false, },
        { field: 'name', headerName: 'Name', width: 130, sortable: false, },
        { field: 'language', headerName: 'Language', width: 130, sortable: false, },
        { field: 'beginDate', headerName: 'Begin date', width: 130, sortable: false, },
        { field: 'endDate', headerName: 'End date', width: 130, sortable: false, },
        { field: 'tournamentState', headerName: 'State', width: 130, sortable: false, },
    ];

    const request = React.useRef(true);

     const handleGetPublicTournaments = async (tournamentRequest): Promise<TournamentsResponse> =>  {
        updateData("loading", true);
        return await tournamentRequest.fetchAsPaged();
    }


    React.useEffect(() => {

        const tournamentRequest = TournamentsRequest.from(
            new QueryParams({
                page: data.page,
                pageSize: data.pageSize,
                sortBy: data.sortBy,
                sortOrder: data.sortOrder
            })
        )

        if (request.current === true) {
            const response = handleGetPublicTournaments(tournamentRequest);
            response.then( r => {
                console.log(r);
                const rows = r.pageItems;
                const totalRows = r.totalCount;
                updateData("totalRows", totalRows);
                updateData("rows", rows);
                updateData("loading", false);

                request.current = false
            });
        }
    }, [data.page, data.pageSize]);


    return (
        <div style={{ height: '60%', width: '100%' }}>
            <DataGrid
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
                onPageChange={ (p) => {updateData("page", p + 1); request.current = true;} }
                onPageSizeChange={ (ps) => {updateData("page", 1); updateData("pageSize", ps); request.current = true;} }
            />
        </div>
    )
}
export default Tournaments;