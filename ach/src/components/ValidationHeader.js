import React, {Component} from "react";
import Logo from "../NotNeeded/download.jpg";
import "../CSS/App.css";

class ValidationHeader extends Component{

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

export default ValidationHeader;