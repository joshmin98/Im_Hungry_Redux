import React, { useGlobal, useEffect, useState } from 'reactn';
import { withRouter } from 'react-router-dom';
import { withStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';

import firebase from '../config/firebaseConfig';
import Login from './sub-components/Login.js';
import Signup from './sub-components/Signup.js';

const styles = {
  root: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    marginLeft: '5%',
    marginRight: '5%',
  },
  modal: {
    position: 'absolute',
    marginTop: '950px',
  },
};

const AuthPage = props => {
  const [user, setUser] = useGlobal('user');
  const [form, setForm] = useState('login');
  useEffect(() => {
    if (user !== null) {
      props.history.push('/homepage');
    }
  }, []);

  return (
    <div className={props.classes.root}>
      {form === 'login' ? (
        <div className={props.classes.modal}>
          <Login />
          <Button id="switch" onClick={() => setForm('signup')}>
            Sign Up
          </Button>
        </div>
      ) : (
        <div className={props.classes.modal}>
          <Signup />
          <Button id="switch" onClick={() => setForm('login')}>
            Login
          </Button>
        </div>
      )}
    </div>
  );
};

export default withStyles(styles)(withRouter(AuthPage));
