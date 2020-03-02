import React, {Component} from 'react';
import './CSS/App.css';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
// noinspection SpellCheckingInspection
import Navbar from "./Navbar";
import test from "./SwitchPageTest"
import Main from "./App"

class SwitchPageTest extends Component {
    render() {
        return (
            <BrowserRouter>
                <div>
                    Working
                    <Navbar />
                    <Switch>
                        <Route path="/SwitchPageTest" component={test} exact/>
                        <Route path="/" component={Main} exact/>
                    </Switch>
                </div>
            </BrowserRouter>
        );
    }
}


export default SwitchPageTest;