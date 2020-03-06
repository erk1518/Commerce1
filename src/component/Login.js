import React, {Component} from 'react';
import './Register';
import{ BrowserRouter as Router,
      Switch,
      Route,
      Link
    } from "react-router-dom";

function Login()
 {
  return (
    <div className="App">

      {/* Displays the header */}
      <header className="App-header">
       {
         <label id = "lblLogin">Sign In</label>
       }
      </header>

      <body className = "Body">
          {/* ctrl + / for comments*/}
          {/* form used for username and password */}
          <form>
            {/* Username input field */}
            <br></br><br></br><br></br>
            <label>Username</label>
            <br></br>
            <input type = "text" id = "userName" required></input>
            <br></br><br></br>

            {/* Password input field */}
            <label>Password</label>
            <br></br>
            <input type = "password" id = "password" required autoComplete="off"></input> 

            <br></br><br></br>

            {/* login button */}
            <button variant = "btnLogin">Login</button>
            <br></br><br></br>

            <Link to = {"/Register"}>Create Account</Link>
        
          </form>
      </body>
    </div>
  );
}

export default Login;
