import React, {useState} from 'react';
import Button from '@material-ui/core/Button';
import {makeStyles} from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';


const useStyles = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
}));


function getAuthCode(e, state, setState) {
    e.preventDefault();
    fetch("http://localhost:8080/authorizationcode")
        .then(response => {
            console.log(response)
            return response.json();
        })
        .then(data => {
            console.log("Code:" + JSON.stringify(data));
            setState({...state, code: data.code});
        })
}

function exchangeAuthCodeForToken(e, state, setState) {
    e.preventDefault();
    fetch("http://localhost:8080/exchange?code=" + state.code)
        .then(response => {
            console.log(response)
            return response.json();
        })
        .then(data => {
            console.log("Code:" + JSON.stringify(data))
            setState({...state, token: data.token})
        })
}



export default function GetAuthCode() {
    const classes = useStyles();
    const [state, setState] = useState({code: 'N/A', token: 'N/A'});

    return (
        <Container component="main" maxWidth="xs">
            Click this button to get an authorization code from Github
            <Button
                fullWidth
                variant="contained"
                color="primary"
                className={classes.submit}
                onClick={(e) => getAuthCode(e, state, setState)}>Get Auth Code</Button>
            Code: {state.code}
            <br/>
            Click this button to exhange the auth code for an access token
            <Button
                fullWidth
                variant="contained"
                color="primary"
                className={classes.submit}
                onClick={(e) => exchangeAuthCodeForToken(e, state, setState)}>Exchange Auth code</Button>
            Token: {state.token}

            <br/>

        </Container>
    );
}