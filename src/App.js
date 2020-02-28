import React from 'react';
// import logo from './logo.svg';
import './App.css';
import{ BrowserRouter as Router,
      Switch,
      Route,
      Link
    } from "react-router-dom";


function App() {
  return (
    <div className="App">

       
      <header className="App-header">
       {
         <label id = "lblLogin">Sign In</label>
       }
      </header>

      <body className = "Body">
          {/* ctrl + / for comments*/}
          {/* form used for username and password */}
          <form>
            {/* <label id = "lblLogin">Login</label> */}

            <br></br><br></br><br></br>
            <label>Username</label>
            <br></br>
            <input type = "text" id = "userName"></input>
            <br></br><br></br>
            <label>Password</label>
            <br></br>
            <input type = "text" id = "password"></input>
            <br></br><br></br>
            {/* <input type = "button" id = "btnLogin" value = "Login" size = "sm"></input> */}
            <button variant = "btnLogin">Login</button>
            <br></br><br></br>

          {
            <Router>
                <div>
                  <nav>
                <Link to={"/Register"}>Create Account</Link>
                </nav>
              {/* <a href = "src\Register.js">Create Account</a> */}
              </div>
              </Router>

          }
              {/* <a href = "https://www.google.com">Create Account</a>  */}
             {/* <a href = "src\Register.js">Create Account</a> */}
          
          </form>

      </body>

    </div>
    
  );
}

export default App;
