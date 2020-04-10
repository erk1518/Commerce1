import React from 'react';
import '../CSS/Style.css';
import './Login';
import StickyHeadTable from "./StickyHeadTable";
import ErrorDetails from "./ErrorDetails";

//Works only for GET method
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const name = urlParams.get('userName');

class Validation extends React.Component{

    render() {
      return (
          <div className="Validation">
                {/**/}
                <header className="Validation-header">
                  {
                      <div>
                          <div className="Picture">
                              <label id="lblLogo">Company Logo</label>
                          </div>
                          <div className="GreetLabel">
                              <label id="lblValidation">Welcome,<br/> {name}</label>
                          </div>
                      </div>
                  }
                </header>

                <body className="Body">
                {/* ctrl + / for comments*/}
                {/* form used to submit file */}
                {
                 <div className="ValidationTable">
                     {/*<div className="ValidationBodyHolder">*/}
                         <div >
                             <StickyHeadTable/>
                         </div>
                     </div>
                 // </div>
                }
                </body>
          </div>
      );
    }
  }
  
  export default Validation;

