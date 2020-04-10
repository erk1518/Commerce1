import React from 'react';
import '../CSS/Style.css';
import './Register';
import './Validation';
import './ErrorDetails';
import{ Link } from "react-router-dom";

function Login ()
 {
         return (
             <div className="Login">

                 {/* Displays the header */}
                 <header className="App-header">
                     {
                         <div>
                             <label id="lblLogin">Sign In <br/>&nbsp;</label>
                         </div>
                     }
                 </header>

                 <body className="Body">
                 {/* ctrl + / for comments*/}
                 {/* form used for username and password method changed to GET for testing we will need to change it back to POST*/}

                 <form action={"/ErrorDetails"} method={"GET"} >
                     {/* Username input field */}
                     <br></br><br></br><br></br>
                     <label>Username</label>
                     <br></br>
                     <input type="text" name="userName"id="userName" required></input>
                     <br></br><br></br>

                     {/* Password input field */}
                     <label>Password</label>
                     <br></br>
                     <input type="password" id="password" required autoComplete="off"></input>

                     <br></br><br></br>

                     {/* login button */}
                     <button type={"submit"} variant="btnLogin">Login</button>
                     <br></br><br></br>

                     <Link to={"/Register"}>Create Account</Link>

                 </form>
                 </body>
             </div>
         );
}

export default Login;
