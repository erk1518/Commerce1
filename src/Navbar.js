import React from "react";
import {Link} from "react-router-dom";

function Navbar() {
    return (
        <div>
            <Link to="/">Home </Link>
            <Link to="/Main">Test Me </Link>

        </div>
    );
};

export default Navbar;