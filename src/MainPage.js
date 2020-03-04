import React, {Component} from "react";
import ReactDOM from 'react-dom';
import App from "./App"
import Test from "./SwitchPageTest"


class MainPage extends Component{
    constructor(props) {
        super(props);
        this.state = {
            page: [App, Test, 'take a nap'],
        }
    }
    render() {
        return(
            <div id="todo-list">
                {this.state.page[1]}
            </div>
        );
    };// render
};

export default MainPage;