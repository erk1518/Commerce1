import React, {Component} from 'react';
import './CSS/App.css';
import DataTable from './DataTable';
import Logo from "./JS/download.jpg"

class TableSet extends Component{
    render() {
        const heading =[["ID"],["Found Error"],["FileName"],["SendCompany"],["RecCompany"]]
        const rows =[
            [[1],["True"],["Testfile.txt"],["Rosebud"],["Bank"]]

        ]
        return (
            <div >
                <DataTable headings = {heading} rows={rows}/>
            </div>
        );
    }
}

class Header extends Component{
    render() {
        const userName = "User"

        return (
            <div id={"BodyHeader"}>
                <div id={"CompanyLogo"}>
                    <img src={Logo} alt={"Website Logo"} hidden={true}/>
                </div>
                <div id={"GreetingUser"}>
                    <label>Welcome, {userName}</label>
                </div>
            </div>
        );
    }
}

class LTab extends Component{
    render() {
        const browse = <button onclick={browseFiles}>Browse<br/></button>
        const validation = <button type="summit" id="btnValidate" >Validate Files</button>
        return (
            <div id={"TabButtons"}>
                <form>
                    <br/>{browse}<br/><br/>{validation}
                </form>
            </div>
        );

    }
}

class Body extends Component {
    render() {
        return (
            <div>
                <div >
                    <Header/>
                </div>
                <div>
                    <div id={"MainBody"}>
                        <div id={"LeftSidedTab"}>
                            <LTab/>
                        </div>
                        <div id={"RightSideTable"}>
                            <br/><br/>
                            <TableSet/>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

function App() {
  return (
    <div>
        <input id="myFile" type="file" onChange={handleChange} hidden={true} multiple />
        <Body />
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

function browseFiles() {
    alert("This is Called");
}


export default App;
