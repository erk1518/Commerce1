import React, {Component} from 'react';
import './App.css';
import{ BrowserRouter,
      Switch,
      Route,
      Link
    } from "react-router-dom";
import Register from './Register';

function App()
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

           
            <Switch>
              <Route path="/" Component={App} exact />
              <Route path="/Register" Component={Register} />
              <Route component={Error}/>
            </Switch>
  */}

            <div>
              <Link to ="/">App </Link>
              <Link to ="/Register">Create Account</Link>
            </div>
       
          {
            // <Router>
            //     <div>
            //       <nav>
            //     <Link to={"/C:\Users\erk15\login\public\Register.js"}>Create Account</Link>
            //     </nav>
            //   {/* <a href = "src\Register.js">Create Account</a> */}
            //   </div>
            //   </Router>

            // <Router>
            //   <Link to = {"/Register"}>Create Account</Link>
            // </Router>

            // <Switch>
            //   <Route path = "/Register"></Route>
            // </Switch>
          }
             
             
              {/* <a href = "https://www.google.com">Create Account</a>  */}
             {/* <a href = "/Register.js">Create Account</a> */}
        
        
          </form>
      </body>
    </div>
  );
}

export default App;
