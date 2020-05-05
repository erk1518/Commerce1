import React from 'react';
import '../CSS/Style.css';
import './Login';
import ErrorTableHeaderRecord from "./ErrorTableHeaderRecord";
import ErrorTableBatchRecord from "./ErrorTableBatchRecord";
import ErrorTableEntryData from "./ErrorTableEntryData";
import ErrorTableAddendaRecord from "./ErrorTableAddendaRecord";
import ErrorTableBatchControl from "./ErrorTableBatchControl";
import ErrorTableHeaderControl from "./ErrorTableHeaderControl";

class ErrorDetails extends React.Component{

    render() {
        return (
            <div className="Validation">
                {/**/}
                <header className="Validation-header">
                    {
                        <div>
                            {/*<div className="Picture">*/}
                            {/*    /!*<label id="lblLogo">Company Logo</label>*!/*/}
                            {/*</div>*/}
                            <div className="GreetLabel">
                                <label id="lblValidation">Welcome,<br/> Joshua</label>
                            </div>
                        </div>
                    }
                </header>

                <body className="Body">
                {/* ctrl + / for comments*/}
                {/* form used to submit file */}
                {
                    <div className="ErrorTable">
                        <div className="ErrorBodyHolder">
                            <div >
                                <ErrorTableHeaderRecord/>
                                <ErrorTableBatchRecord/>
                                <ErrorTableEntryData/>
                                <ErrorTableAddendaRecord/>
                                <ErrorTableBatchControl/>
                                <ErrorTableHeaderControl/>
                            </div>
                        </div>
                    </div>
                }
                </body>
            </div>
        );
    }
}

export default ErrorDetails;

