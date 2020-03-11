import React, {Component} from "react";
import Header from "./ValidationHeader";
import Tab from "./ValidationLeftTab";
import Table from "./ValidationTable"
import "../CSS/App.css";

class ValidationBody extends Component {

    render() {
        return (
            <div>
                <header>
                    <Header/>
                </header>
                <div>
                    <div id={"MainBody"}>
                        <div id={"LeftSidedTab"}>
                            <Tab/>
                        </div>
                        <div id={"RightSideTable"}>
                            <br/><br/>
                            <Table/>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default ValidationBody;