import './App.css';
import ChatWindow from "./ChatWindow";
import React from "react";
import Typography from "@material-ui/core/Typography";
import Link from "@material-ui/core/Link";


function Copyright() {
    return (
        <Typography variant="body2" color="textSecondary" align="center">
            {'Copyright © '}
            <Link color="inherit" href="https://material-ui.com/">
                ricardoschuller.com
            </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

function App() {
  return (
    <div className="App">
        <div className="g-signin2" data-onsuccess="onSignIn"></div>
        <ChatWindow/>
    </div>
  );
}

export default App;
