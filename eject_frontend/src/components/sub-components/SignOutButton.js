import React, { useGlobal } from 'reactn';
import { withRouter } from 'react-router-dom';
import { withStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import firebase from '../../config/firebaseConfig';
import axios from 'axios';

const styles = {
  buttonModal: {
    backgroundColor: '#fad744',
    margin: '5px',
    color: '#2B3252',
    fontSize: '15px',
    fontWeight: 'bold',
  },
};

const SignOutButton = props => {
  const [user, setUser] = useGlobal('user');

  const handleClick = () => {
    firebase
      .auth()
      .signOut()
      .then(resp => {
        setUser(null);
        axios.get('http://localhost:8338/logout').then(resp => {
          console.log(resp);
          props.history.push('/');
        });
      })
      .catch(err => {
        console.log(err);
      });
  };

  return (
    <Button
      id="logout"
      onClick={() => handleClick()}
      className={props.classes.buttonModal}
    >
      Logout
    </Button>
  );
};

export default withStyles(styles)(withRouter(SignOutButton));
