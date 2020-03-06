import React from 'react';
import './App.css';

class Register extends React.Component
{
  render()
  {
    return <h1>Register</h1>
  }
}

export default Register

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
            <input type = "text" id = "name" required></input>
            <br></br><br></br>

            <label>Username</label>
            <br></br>
            <input type = "text" id = "createUserName" required></input>
            <br></br><br></br>

            <label>Email Address</label>
            <br></br>
            <input type = "text" id = "createEmail" required></input>
            <br></br><br></br>

            <label>Password</label>
            <br></br>
            <input type = "password" id = "createPassword" required></input>
            <br></br><br></br>

            <label>Confirm Password</label>
            <br></br>
            <input type = "password" id = "createConfirmPassword" required></input>
            <br></br><br></br>

            <button variant = "btnSignUP">Sign Up</button>
            <br></br><br></br>
            
            
             <a href = "/App.js">Sign in</a> 
            {/* <a href = "/App.js">Create Account</a> */}
          </form>
      </body>
      </div>
    );
  }
  
  export default Register;
