import React from 'react';
import './CSS/App.css';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Validation from "./components/Validation";
import Login from "./components/Login"
import Register from "./components/Register";




function App() {
  return (
    <div>
        <BrowserRouter>
            <div>
                <Switch>
                    <Route path="/" component={Login} exact/>
                    <Route path="/Login" component={Login}/>
                    <Route path="/Validation" component={Validation}/>
                    <Route path="/Register" component={Register}/>
                </Switch>
            </div>
        </BrowserRouter>
    </div>
  );
}


export default App;
