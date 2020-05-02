import React from 'react';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Validation from "./components/Validation";
import ErrorDetails from "./components/ErrorDetails";
import Login from "./components/Login"
import Register from "./components/Register";
import ReactDOM from "react-dom";

function App() {
  return (
    <div>
        <BrowserRouter>
            <div>
                <Switch>
                    <Route path="/" component={Login} exact/>
                    <Route path="/Login" component={Login}/>
                    <Route path="/Validation" component={Validation}/>
                    <Route path="/ErrorDetails" component={ErrorDetails}/>
                    <Route path="/Register" component={Register}/>
                </Switch>
            </div>
        </BrowserRouter>
    </div>
  );
}

/*
class App extends Component {
 
    state = {};
 
    componentDidMount() {
        setInterval(this.hello, 250);
    }
 
    hello = () => {
        fetch('/api/hello')
            .then(response => response.text())
            .then(message => {
                this.setState({message: message});
            });
    };
 
    render() {
        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <h1 className="App-title">{this.state.message}</h1>
                </header>
                <p className="App-intro">
                    To get started, edit <code>src/App.js</code> and save to reload.
                </p>
            </div>
        );
    }
}
*/

export default App;

ReactDOM.render(
    <BrowserRouter>
        <App />
    </BrowserRouter>
    ,document.getElementById('root'));
