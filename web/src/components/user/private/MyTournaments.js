import React from "react";
import {DataGrid, GridColDef} from "@mui/x-data-grid";
import {Navigate} from 'react-router-dom';
import {QueryParams} from '../../../httpUtils/QueryParams'
import {InscriptionsRequest} from "../../../request/InscriptionsRequest";
import {TournamentsResponse} from "../../../response/TournamentsResponse";
import {MeaningNotFoundException} from "../../../errors/MeaningNotFoundException";
import {UnauthorizedException} from "../../../errors/UnauthorizedException";

const MyTournaments = () => {
    const [unauthorized, setUnauthorized] = React.useState(false);
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
        id: number,
        name: string,
        language: string,
        beginDate: string,
        endDate: string,
        state: string,
    ) {
        return { id, name, language, beginDate, endDate, state };
    }

    const updateData = (k, v) => setData((prev) => ({ ...prev, [k]: v }));

    const columns: GridColDef[] = [
        { field: 'id', headerName: 'ID', width: 70, sortable: false, },
        { field: 'name', headerName: 'Name', width: 130, sortable: false, },
        { field: 'language', headerName: 'Language', width: 130, sortable: false, },
        { field: 'beginDate', headerName: 'Begin date', width: 130, sortable: false, },
        { field: 'endDate', headerName: 'End date', width: 130, sortable: false, },
        { field: 'tournamentState', headerName: 'State', width: 130, sortable: false, },
    ];

    const handleGetInscriptions = async (inscriptionsRequest): Promise<TournamentsResponse> =>  {
        updateData("loading", true);
        return await inscriptionsRequest.fetchAsPaged();
    }

    const handleUnauthorized = (e) => {
        if(!e instanceof UnauthorizedException) throw e;
        setUnauthorized(true);
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
                updateData("rows", r.pageItems)
            })
            .catch(e => handleUnauthorized(e))
            .finally( () => updateData("loading", false));


    }, [data.page, data.pageSize]);



    // const [template, setTemplate] = React.useState(a());

    return (
        <React.Fragment>
            {unauthorized && <Navigate to={'/logout'}/>}
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
                    onPageChange={ (p) => {updateData("page", p + 1);} }
                    onPageSizeChange={ (ps) => {updateData("page", 1); updateData("pageSize", ps);} }
                />
            </div>
        </React.Fragment>
    );
};

export default  MyTournaments;