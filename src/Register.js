import React from 'react';
import './App.css';

function Register() {
    return (
      <div className="App">
         <header className="App-header">
         {
             <label id = "lblRegister">Create Account</label>
         }
        </header>

        <body className = "Body">
          {/* ctrl + / for comments*/}
          {/* form used to create account */}
          <form>
            <br></br><br></br><br></br>
            <label>Name</label>
            <br></br>
            <input type = "text" id = "name"></input>
            <br></br><br></br>

            <label>Username</label>
            <br></br>
            <input type = "text" id = "createUserName"></input>
            <br></br><br></br>

            <label>Email Address</label>
            <br></br>
            <input type = "text" id = "createEmail"></input>
            <br></br><br></br>

            <label>Password</label>
            <br></br>
            <input type = "text" id = "createPassword"></input>
            <br></br><br></br>

            <label>Confirm Password</label>
            <br></br>
            <input type = "text" id = "createConfirmPassword"></input>
            <br></br><br></br>

            <button variant = "btnSignUP">Sign Up</button>
            <br></br><br></br>
            
            
             <a href = "https://www.google.com">Sign in</a> 
            {/* <a href = "src\Register.js">Create Account</a> */}
          </form>
      </body>
      </div>
    );
  }
  
  export default Register;
