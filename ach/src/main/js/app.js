import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import Register from "./components/Register";
import Login from "./components/Login";
const ReactDOM = require('react-dom');

function App() {
    return (
        <div className="container">
            <Router>
                <div className="col-md-6">
                    <h1 className="text-center" style={style}>React Car Application</h1>
                    <Switch>
                        <Route path="/" exact component={Login} />
                        <Route path="/Register"  component={Register} />
                    </Switch>
                </div>
            </Router>
        </div>
    );
}

const style = {
    color: 'red',
    margin: '10px'
}

export default App;

ReactDOM.render(
    <App />,
    document.getElementById('react')
)