import React, { useGlobal, useState } from 'reactn';
import { withRouter } from 'react-router-dom';
import { withStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';

import firebase from '../../config/firebaseConfig';
import axios from 'axios';

const styles = theme => ({
  button: {
    width: '100%',
    backgroundColor: '#fad744',
    color: '#2B3252',
    fontSize: '15px',
    fontWeight: 'bold',
  },
  paper: {
    //width: theme.spacing.unit * 50,
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[5],
    padding: theme.spacing.unit * 4,
    outline: 'none',
    width: '50%',
  },
  text: {
    textAlign: 'center',
    fontFamily: "'Arvo', serif",
    marginBottom: 40,
  },
  textField: {
    width: '100%',
  },
  buttonModal: {
    width: '30%',
    marginLeft: '35%',
    marginTop: 20,
    backgroundColor: '#fad744',
    color: '#2B3252',
    fontSize: '15px',
    fontWeight: 'bold',
  },
  form: {
    margin: '10px',
  },
});

const Login = props => {
  const [user, setUser] = useGlobal('user');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const login = (email, password) => {
    const resp = firebase
      .auth()
      .signInWithEmailAndPassword(email, password)
      .then(resp => {
        setUser(email);
        console.log(email);
        axios
          .get(`http://localhost:8338/login?email=${email}`)
          .then(resp => {
            console.log(resp);
            props.history.push('/homepage');
          })
          .catch(err => {
            console.log(err);
          });
      })
      .catch(error => {
        alert(error.message);
      });
  };

  return (
    <Paper>
      <Typography component="h3" variant="h2" className={props.classes.text}>
        Log In
      </Typography>

      <form className={props.classes.form} onSubmit={null}>
        <TextField
          label="Email"
          className={props.classes.textField}
          margin="normal"
          type="email"
          id="email"
          variant="outlined"
          onChange={event => setEmail(event.target.value)}
        />
        <TextField
          id="password"
          label="Password"
          className={props.classes.textField}
          type="password"
          margin="normal"
          variant="outlined"
          onChange={event => {
            setPassword(event.target.value);
          }}
        />
        <Button
          variant="contained"
          color="secondary"
          type="submit"
          id="login"
          className={props.classes.buttonModal}
          onClick={event => {
            event.preventDefault();
            login(email, password);
          }}
        >
          Log In
        </Button>
      </form>
    </Paper>
  );
};

export default withStyles(styles)(withRouter(Login));
