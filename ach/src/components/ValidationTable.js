import React, {Component} from "react";
import DataTable from "./DataTable";
import "../CSS/App.css";


let rowsString = [[""],[""],[""],[""],[""],[""],[""]];

class ValidationTable extends Component{

    render() {

        const heading =[["ID"],["File Name"],["Sending Company"],["Receiving Company"],["Date"],["Time"],["Found Error"]]
        let rows =[rowsString]
        return (
            <div >
                <input id="myFile" type="file" onChange={handleChange} hidden={true} multiple />
                <DataTable headings = {heading} rows={rows}/>
            </div>
        );
    }
}

function handleChange(event) {
    //if Empty
    if (!event.target.files[0]) {
        return
    }
    else
    {
        const fileList = event.target.files; /* now you can work with the file list */
        rowsString = ""

        for (let i = 0, numFiles = fileList.length; i < numFiles; i++) {
            let file = fileList[i];
            let id = i+1
            let fileName = file.name
            let company = "Rosebud;!;!;!;!"
            let sendTo = "Bank"
            let date = new Date().toLocaleDateString()
            let time = new Date().toLocaleTimeString()
            let error = "No Error Found"
            rowsString = [[id],[fileName],[company],[sendTo],[date],[time],[error]]
        }
        //uncomment line below to see error
        //UpdateTable()
        //look up React States: https://upmostly.com/tutorials/how-to-refresh-a-page-or-component-in-react
    }
}

export default ValidationTable;