import React, {useEffect, useState} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import Divider from '@material-ui/core/Divider';
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Avatar from '@material-ui/core/Avatar';
import Fab from '@material-ui/core/Fab';
import SendIcon from '@material-ui/icons/Send';
import moment from 'moment';

const useStyles = makeStyles({
    table: {
        minWidth: 650,
    },
    chatSection: {
        width: '100%',
        height: '80vh'
    },
    headBG: {
        backgroundColor: '#e0e0e0'
    },
    borderRight500: {
        borderRight: '1px solid #e0e0e0'
    },
    messageArea: {
        height: '70vh',
        overflowY: 'auto'
    }
});

const getAlignment = (message) => {
    if (window.googleUser == null) {
        return;
    }
    const id = window.googleUser.getBasicProfile().getId();
    return id === message.principal.userId ? 'left' : 'right'
}

const sendMessage = async (message) => {
    console.log("Message:", JSON.stringify(message))
    if (window.googleUser == null || message == null) {
        return
    }
    const idToken = window.googleUser.getAuthResponse().id_token;
    await fetch("http://www.ricardoschuller.com/api/post-message",
        {
            method: 'post',
            headers: new Headers({
                'Authorization': 'Bearer ' + idToken
            }),
            body: message
        })
        .then(data => {
            console.log("Posted message:" + message)

        })
}

const fetchMessages = async (state, setState) => {
    if (window.googleUser == null) {
        return;
    }

    const idToken = window.googleUser.getAuthResponse().id_token;
    await fetch("http://www.ricardoschuller.com/api/get-messages",
        {
            method: 'get',
            headers: new Headers({
                'Authorization': 'Bearer ' + idToken
            })
        })
        .then(response => response.json())
        .then(data => {
            console.log("DATA " + JSON.stringify(data))
            if (data) {
                setState({messages:data});
            }
        })
}

const getName = () => "Name";
const getImageUrl = () => "N/A";

const getBasicProfile = () => {
    return {getName, getImageUrl};
}


const ChatWindow = () => {
    const classes = useStyles();

    const [state, setState] = useState({messages:[]});
    const [message, setMessage] = useState('');

    useEffect(() => {
        const interval = setInterval(() => fetchMessages(state, setState), 5000)
        return () => clearInterval(interval)
    })
    const googleUser = window.googleUser || {getBasicProfile};
    return (
        <div>
            <h2>Welcome to the Matchesfashion OAuth Demo</h2>;

            <Grid container>
                <Grid item xs={12} >
                    <Typography variant="h5" className="header-message">Chat</Typography>
                </Grid>
            </Grid>
            <Grid container component={Paper} className={classes.chatSection}>
                <Grid item xs={3} className={classes.borderRight500}>
                    <List>
                        <ListItem button key={googleUser.getBasicProfile().getName()}>
                            <ListItemIcon>
                                <Avatar alt={googleUser.getBasicProfile().getName()} src={googleUser.getBasicProfile().getImageUrl()} />
                            </ListItemIcon>
                            <ListItemText primary={googleUser.getBasicProfile().getName()}></ListItemText>
                        </ListItem>
                    </List>
                    <Divider />
                    <Grid item xs={12} style={{padding: '10px'}}>
                        <TextField id="outlined-basic-email" label="Search" variant="outlined" fullWidth />
                    </Grid>
                    <Divider />
                    <List>
                        {state.messages && state.messages.filter((value, index, self) => {
                            return self.findIndex(v => v.principal.userId === value.principal.userId) === index;
                        }).map((chatMessage) => (
                            <ListItem button key={chatMessage.principal.name}>
                                <ListItemIcon>
                                    <Avatar alt={chatMessage.principal.name} src={googleUser.getBasicProfile().getImageUrl()}/>
                                </ListItemIcon>
                                <ListItemText primary={chatMessage.principal.name}></ListItemText>
                            </ListItem>
                        ))}
                    </List>
                </Grid>
                <Grid item xs={9}>
                    <List className={classes.messageArea}>
                            {state.messages.map((chatMessage, index) => (
                                <ListItem key={index}>

                                <Grid container>
                                    <Grid item xs={8}>
                                        <ListItemText align={getAlignment(chatMessage)} secondary={chatMessage.principal.name}></ListItemText>
                                    </Grid>
                                    <Grid item xs={12}>
                                        <ListItemText align={getAlignment(chatMessage)} primary={chatMessage.message}></ListItemText>
                                    </Grid>
                                    <Grid item xs={12}>
                                        <ListItemText align={getAlignment(chatMessage)} secondary={moment(chatMessage.localDateTime).format("hh:mm")}></ListItemText>
                                    </Grid>
                                </Grid>
                                </ListItem>
                            ))}
                    </List>

                    <Divider />

                    <form id="input-form" onSubmit={(event) => {
                        event.preventDefault();
                        sendMessage(message)
                            .then(() => fetchMessages(state, setState))
                    }}>
                        <Grid container style={{padding: '20px'}}>

                            <Grid item xs={11}>
                                <TextField id="outlined-basic-email" label="Type Something" fullWidth onInputCapture={(e) => setMessage(e.target.value)}/>
                            </Grid>
                            <Grid xs={1} align="right">
                                <Fab color="primary" aria-label="add"><SendIcon /></Fab>
                            </Grid>
                        </Grid>
                    </form>
                </Grid>
            </Grid>
        </div>
    );
}

export default ChatWindow;