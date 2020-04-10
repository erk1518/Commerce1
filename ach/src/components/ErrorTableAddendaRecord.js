import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TablePagination from '@material-ui/core/TablePagination';
import TableRow from '@material-ui/core/TableRow';

const columns = [
    { id: 'isError', label: '', minWidth: 5 },
    { id: 'recordType', label: 'Record Type', minWidth: 5 },
    { id: 'addendaTypeCode', label: 'Addenda\u00a0Type Code', minWidth: 5, align: 'right',},
    { id: 'paymentInfo', label: 'Payment\u00a0Related Info', minWidth: 90, align: 'right',},
    { id: 'addendaSeqNum', label: 'Addenda\u00a0Sequence Number', minWidth: 5, align: 'right',},
    { id: 'entryDetailSeqNum', label: 'Entry Detail\u00a0Sequence Number', minWidth: 10, align: 'right',},

    // {
    //     id: 'population',
    //     label: 'Population',
    //     minWidth: 20,
    //     align: 'right',
    //     format: (value) => value.toLocaleString(),
    // },

];

function createData(name, size) {
    // const density = population / size;
    return { name, size };
}

function filesToValidate(event) {
    //if Empty
    if (!event.target.files[0]) {
        return
    }
    else
    {
        const fileList = event.target.files; /* now you can work with the file list */
        for (let i = 0, numFiles = fileList.length; i < numFiles; i++) {
            let file = fileList[i];
            rows.push(createData(file.name, file.size));
        }
        //This cause auto submit
        //document.getElementById("btnSubmit").click();
    }
}

function selectFiles() {
    document.getElementById("myFile").click();
}

const rows = [];

const useStyles = makeStyles({
    root: {
        width: '100%',
    },
    container: {
        maxHeight: 440,
    },
});

export default function StickyHeadTable() {

    const classes = useStyles();
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);
    // eslint-disable-next-line no-unused-vars
    const [refreshPage, setRefresh] = React.useState(0);
    //1,2,2,3,2,2,3, why this pattern, this also messes with refresh
    let count = 1;

    const handlePageUpdate = (event) => {
        filesToValidate(event);
        //alert(count);
        setRefresh(count);
        setPage(0);
        count = count + 1;
        //alert(count);
    }

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    return (
        <div>
            <br/>

            <Paper className="TableHolder">
                {/*action=will send the data to address*/}
                <label id={"tableHeader"}>Here</label>
                <TableContainer className={classes.container}>
                    <Table stickyHeader aria-label="sticky table">
                        <TableHead>
                            <TableRow>
                                {columns.map((column) => (
                                    <TableCell
                                        key={column.id}
                                        align={column.align}
                                        style={{ minWidth: column.minWidth }}
                                    >
                                        {column.label}
                                    </TableCell>
                                ))}
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {rows.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((row) => {
                                return (
                                    <TableRow hover role="button" tabIndex={-1} key={row.code}>
                                        {columns.map((column) => {
                                            const value = row[column.id];
                                            return (
                                                <TableCell key={column.id} align={column.align}>
                                                    {column.format && typeof value === 'number' ? column.format(value) : value}
                                                </TableCell>
                                            );
                                        })}
                                    </TableRow>
                                );
                            })}
                        </TableBody>
                    </Table>
                </TableContainer>
                <TablePagination
                    rowsPerPageOptions={[10, 25, 100]}
                    component="div"
                    count={rows.length}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onChangePage={handleChangePage}
                    onChangeRowsPerPage={handleChangeRowsPerPage}
                />
            </Paper>
        </div>
    );
}