import React from 'react';
import './CSS/App.css';
import {Route, Switch } from "react-router-dom"
// noinspection SpellCheckingInspection
import Navbar from "./Navbar";
import App from './SwitchPageTest';
import Main from "./App"

const SwitchPageTest = () => (
    <main>
        <Navbar />
        <Switch>
            <Route path="/" Component={App} exact />
            <Route path="/Main" Component={Main} />
        </Switch>
    </main>
);



export default SwitchPageTest;