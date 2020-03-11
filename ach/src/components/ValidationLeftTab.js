import React, {Component} from "react";
import {Link} from "react-router-dom";
import "../CSS/App.css";

class ValidationLeftTab extends Component{

    render() {

        const browse = <label id={"Browse"} htmlFor={"myFile"}>Browse<br/></label>
        const validation = <button type="summit" id="btnValidate" >Validate Files</button>
        const logout = <Link to="/Login">Logoff</Link>
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

export default ValidationLeftTab;