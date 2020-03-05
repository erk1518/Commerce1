import React, {Component} from 'react';
import './CSS/App.css';
import DataTable from './DataTable';
import Logo from "./JS/download.jpg";
import  {Link} from "react-router-dom";
// import {BrowserRouter, Route, Switch} from "react-router-dom";
//import Navbar from "./Navbar";
//import Test from "./SwitchPageTest";
//import Main from "./App"

let rowsString = [[""],[""],[""],[""],[""],[""],[""]];

// class SwitchPage extends Component {
//     render() {
//         return (
//             <BrowserRouter>
//                 <div>
//                     {/*<Navbar />*/}
//                     <Switch>
//                         <Route path="/" component={Main} exact/>
//                         <Route path="/Test" component={Test} exact/>
//                     </Switch>
//                 </div>
//             </BrowserRouter>
//         );
//     }
// }

class TableSet extends Component{

    render() {

        const heading =[["ID"],["File Name"],["Sending Company"],["Receiving Company"],["Date"],["Time"],["Found Error"]]
        let rows =[rowsString]
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
                    <label id={"UserLabel"}>Welcome, {userName}</label>
                </div>
            </div>
        );
    }
}
//https://stackoverflow.com/questions/49649767/reactjs-how-do-you-switch-between-pages-in-react
class LTab extends Component{
    render() {

        const browse = <label id={"Browse"} htmlFor={"myFile"}>Browse<br/></label>
        const validation = <button type="summit" id="btnValidate" >Validate Files</button>
        const logout = <Link to="/Test">Logoff</Link>
        return (
            <div id={"TabButtons"}>
                {/*action= is were the file(s) are going */}
                <form action={"./"} method={"POST"}>
                    <br/>{browse}<br/><br/>{validation}
                </form>
                    <br/>{logout}
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
        <input id="myFile" type="file" onChange={handleChange} hidden={false} multiple />
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

export default App;
