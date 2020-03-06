import React, {Component} from 'react';
import './App.css';
import{ BrowserRouter as Router,
      Switch,
      Route,
      Link
    } from "react-router-dom";
import Register from "./component/Register";
import Login from "./component/Login";

function App()
 {
  return (
    <div className="App">
        <Router>
             <Switch>
              <Route path="/" exact component={Login} />
              <Route path="/Register" component={Register} />
              <Route component={Error}/>
            </Switch>
            </Router>
    </div>
  );
}

export default App;
