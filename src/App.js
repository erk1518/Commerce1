import React, {Component} from 'react';
import DataTable from './DataTable';
import './CSS/App.css';

class Body extends Component {
    render() {
        return (
            <p>
                <input id="myFile" type="file" onChange={handleChange} hidden={true} multiple={false} />
                <table id="tblButtons">
                    <th><label id="btnBrowse" htmlFor="myFile" >Browse</label></th>
                    <th><button type="button" id="btnCancel" onClick="clearLoadedFile()">Cancel</button></th>
                    <th><button type="button" id="btnValidate" onClick="validate()">Validate File</button></th>
                </table>
            </p>
        );
    }
}

class Header extends Component{
    render() {
        const heading =[[""],[""]];
        const rows = [
            [1,"5252525252525252525252525252525252525252525252525252525252525252525252525252525252525252525984"],
            [2,"5252525984525252598452525259845252525984525252598452525259845252525984525252598452525259846668"],
            [3,"5252525984525252598452525259845252525984525252598452525259845252525984525252598452525259846668"],
            [4,"5252525984525252598452525259845252525984525252598452525259845252525984525252598452525259846668"],
            [5,"5252525984525252598452525259845252525984525252598452525259845252525984525252598452525259846668"],
            [6,"5252525984525252598452525259845252525984525252598452525259845252525984525252598452525259846668"]
        ];
        return (
            <p>
                <div align = "center">
                           <DataTable headings = {heading}rows={rows}/>
                </div>
            </p>
        );
    }
}

class Report extends Component{
    render(){
        const Error =
            <p>ERROR one word red, large, and needs to be centered</p>;
        const ErrorReason =
            <p>The reason for the error goes here autosize based on the size of this field with a minimum size need to be worked in and red color</p>;

        return (
            <div align={"center"}>
                <div id={"Header"}>
                    <p id={"Text"}>Messages</p>
                </div>
                <div>
                    <div id={"ErrorReport"}>
                        <div id = "isError">
                            {Error}
                        </div>
                        <div id = "whatError">
                            {ErrorReason}
                        </div>
                    </div>
                </div>
                <div id={"Header"}>
                    <p id={"Text"}>Company Specification / File Details</p>
                </div>
                <div>
                    <div id={"ErrorDetails"}>
                        <table id = {"ErrorLayout"}>
                            <tr>
                                <td colSpan={3}>
                                    <div id={"FileHeaders"}>
                                        <b>File Header Record(1)</b>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <table id={"InnerTable"}>
                                        <tr>
                                            <td>
                                                <div id={"SubHeaders"}>
                                                    <b>Immediate<br/>Destination</b>
                                                </div>
                                            </td>
                                            <td>
                                                <div id={"FileData"}>
                                                    101000019
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>
                                    <table id={"InnerTable"}>
                                        <tr>
                                            <td>
                                                <div id={"SubHeaders"}>
                                                    <b>Immediate<br/>Origin</b>
                                                </div>
                                            </td>
                                            <td>
                                                <div id={"FileData"}>
                                                    741258964
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>
                                    <table id={"InnerTable"}>
                                        <tr>
                                            <td>
                                                <div id={"SubHeaders"}>
                                                    <b>Immediate<br/>Origin Name</b>
                                                </div>
                                            </td>
                                            <td>
                                                <div id={"FileData"}>
                                                    The Fab Four Corp
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>

                            <tr>
                                <td colSpan={3}>
                                    <div id={"FileHeaders"}>
                                        <b>Batch Header Record (5)</b>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <table id={"InnerTable"}>
                                        <tr>
                                            <td>
                                                <div id={"SubHeaders"}>
                                                    <b>Company<br/>Name</b>
                                                </div>
                                            </td>
                                            <td>
                                                <div id={"FileData"}>
                                                    STRAWBERRYFIELDS
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>
                                    <table id={"InnerTable"}>
                                        <tr>
                                            <td>
                                                <div id={"SubHeaders"}>
                                                    <b>Company<br/>ID</b>
                                                </div>
                                            </td>
                                            <td>
                                                <div id={"FileData"}>
                                                    741258964
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>
                                    <table id={"InnerTable"}>
                                        <tr>
                                            <td>
                                                <div id={"SubHeaders"}>
                                                    <b>Effective<br/>Date</b>
                                                </div>
                                            </td>
                                            <td>
                                                <div id={"FileData"}>
                                                    10/31/2019
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colSpan={3}>
                                    <div id={"FileHeaders"}>
                                        <b>BATCH CONTROL RECORD (8)</b>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <table id={"InnerTable"}>
                                        <tr>
                                            <td>
                                                <div id={"SubHeaders"}>
                                                    <b>Entry/Addenda<br/>Count</b>
                                                </div>
                                            </td>
                                            <td>
                                                <div id={"FileData"}>
                                                    18
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>
                                    <table id={"InnerTable"}>
                                        <tr>
                                            <td>
                                                <div id={"SubHeaders"}>
                                                    <b>Entry Hash</b>
                                                </div>
                                            </td>
                                            <td>
                                                <div id={"FileData"}>
                                                    0181800018
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <table id={"InnerTable"}>
                                        <tr>
                                            <td>
                                                <div id={"SubHeaders"}>
                                                    <b>TTL Debit Entry<br/>$-Amount</b>
                                                </div>
                                            </td>
                                            <td>
                                                <div id={"FileData"}>
                                                    $0.00
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>
                                    <table id={"InnerTable"}>
                                        <tr>
                                            <td>
                                                <div id={"SubHeaders"}>
                                                    <b>TTL Credit<br/>Entry $-Amount</b>
                                                </div>
                                            </td>
                                            <td>
                                                <div id={"FileData"}>
                                                    $0.00
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>
                                    <table id={"InnerTable"}>
                                        <tr>
                                            <td>
                                                <div id={"SubHeaders"}>
                                                    <b>Company<br/>ID</b>
                                                </div>
                                            </td>
                                            <td>
                                                <div id={"FileData"}>
                                                    741258964
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colSpan={3}>
                                    <div id={"FileHeaders"}>
                                        <b>FILE CONTROL RECORD (9)</b>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <table id={"InnerTable"}>
                                        <tr>
                                            <td>
                                                <div id={"SubHeaders"}>
                                                    <b>Entry/Addenda<br/>Count</b>
                                                </div>
                                            </td>
                                            <td>
                                                <div id={"FileData"}>
                                                    18
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>
                                    <table id={"InnerTable"}>
                                        <tr>
                                            <td>
                                                <div id={"SubHeaders"}>
                                                    <b>Entry Hash</b>
                                                </div>
                                            </td>
                                            <td>
                                                <div id={"FileData"}>
                                                    0181800018
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <table id={"InnerTable"}>
                                        <tr>
                                            <td>
                                                <div id={"SubHeaders"}>
                                                    <b>TTL Debit Entry<br/>$-Amount</b>
                                                </div>
                                            </td>
                                            <td>
                                                <div id={"FileData"}>
                                                    $0.00
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>
                                    <table id={"InnerTable"}>
                                        <tr>
                                            <td>
                                                <div id={"SubHeaders"}>
                                                    <b>TTL Credit Entry<br/>$-Amount</b>
                                                </div>
                                            </td>
                                            <td>
                                                <div id={"FileData"}>
                                                    $0.83
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>

        );
    }
}

function App() {
  return (
    <div>
        <Header />
        <Body />
        <Report />
    </div>
  );
}

function handleChange(event) {
    //if Empty
    if (!event.target.files[0]) {
        return
    }
    else
    {

    }
}


export default App;
