import React from 'react';
import '../CSS/Style.css';
import './Login';
import{ Link } from "react-router-dom";

function Register()
{
      return (
          <div className="Register">
            <header className="App-header">
              {
                <label id="lblRegister">Create Account<br/>&nbsp;</label>
              }
            </header>

            <body className="Body">
            {/* ctrl + / for comments*/}
            {/* form used to create account */}
            <form>
              <br></br><br></br><br></br>
              <label>Name</label>
              <br></br>
              <input type="text" id="name" required></input>
              <br></br><br></br>

              <label>Username</label>
              <br></br>
              <input type="text" id="createUserName" required></input>
              <br></br><br></br>

              <label>Email Address</label>
              <br></br>
              <input type="text" id="createEmail" required></input>
              <br></br><br></br>

              <label>Password</label>
              <br></br>
              <input type="password" id="createPassword" required></input>
              <br></br><br></br>

              <label>Confirm Password</label>
              <br></br>
              <input type="password" id="createConfirmPassword" required></input>
              <br></br><br></br>

              <button variant="btnSignUP">Sign Up</button>
              <br></br><br></br>


              <Link to={"/"}>Sign In</Link>
            </form>
            </body>
          </div>
      );
  }
  
  export default Register;
