import React from 'reactn';
import { withRouter } from 'react-router-dom';
import { withStyles } from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import Modal from '@material-ui/core/Modal';
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';
import firebase from '../../config/firebaseConfig';

const styles = theme => ({
  root: {
    flexGrow: 1,
    marginLeft: 10,
  },
  button: {
    width: '100%',
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
  },
});

function getModalStyle() {
  return {
    top: '20%',
    left: '30%',
  };
}

class UserSignIn extends React.Component {
  state = {
    open: false,
    email: '',
    password: '',
    whichModal: '',
    emailLI: '',
    passwordLI: '',
    passwordAgainLI: '',
    loading: false,
  };
  handleOpen = name => e => {
    this.setState({
      open: true,
      whichModal: name,
    });
  };
  handleClose = () => {
    this.setState({
      open: false,
    });
  };
  handleChange = name => e => {
    this.setState({
      [name]: e.target.value,
    });
    // console.log(this.state);
  };

  handleSubmit = event => {
    event.preventDefault();
    if (this.state.whichModal === 'login') {
      this.setState({ loading: true });
      firebase
        .auth()
        .signInWithEmailAndPassword(this.state.email, this.state.password)
        .then(resp => {
          this.setGlobal({ user: resp });
          this.setState({ open: false });
          console.log('LOGGED IN');
        })
        .catch(error => {
          console.log(error);
        });
    } else {
      if (this.state.password !== this.state.passwordAgainLI) {
        alert('Passwords must match!');
        return;
      }
      this.setState({ loading: true });
      firebase
        .auth()
        .createUserWithEmailAndPassword(
          this.state.email.toString().trim(),
          this.state.password,
        )
        .then(resp => {
          this.setGlobal({ user: resp });
          this.setState({ open: false });
          console.log('CREATED USER');
        })
        .catch(error => {
          console.log(this.state.email);
          console.log(error);
          alert('User already registered');
        });
    }
  };

  render() {
    const { classes } = this.props;
    return (
      <div className={classes.root}>
        <Grid container spacing={16}>
          <Grid item xs={6}>
            <Button
              variant="contained"
              color="secondary"
              className={classes.button}
              onClick={this.handleOpen('login')}
            >
              Log In
            </Button>
          </Grid>
          <Grid item xs={6}>
            <Button
              variant="contained"
              color="secondary"
              className={classes.button}
              onClick={this.handleOpen('signUp')}
            >
              Sign Up
            </Button>
          </Grid>
        </Grid>
        <Modal
          open={this.state.open}
          onClose={this.handleClose}
          style={getModalStyle()}
        >
          {this.state.whichModal === 'login' ? (
            <div className={classes.paper}>
              {this.state.loading ? (
                <Typography>Loading...</Typography>
              ) : (
                <>
                  <Typography
                    component="h3"
                    variant="h2"
                    className={classes.text}
                  >
                    Log In
                  </Typography>

                  <form onSubmit={this.handleSubmit}>
                    <TextField
                      label="Email"
                      className={classes.textField}
                      margin="normal"
                      type="email"
                      variant="outlined"
                      onChange={this.handleChange('email')}
                    />
                    <TextField
                      label="Password"
                      className={classes.textField}
                      type="password"
                      margin="normal"
                      variant="outlined"
                      onChange={this.handleChange('password')}
                    />
                    <Button
                      variant="contained"
                      color="secondary"
                      type="submit"
                      className={classes.buttonModal}
                      onClick={this.handleSubmit}
                    >
                      Log In
                    </Button>
                  </form>
                </>
              )}
            </div>
          ) : (
            <div className={classes.paper}>
              {this.state.loading ? (
                <Typography>Loading...</Typography>
              ) : (
                <div>
                  <Typography
                    component="h3"
                    variant="h2"
                    className={classes.text}
                  >
                    Sign Up
                  </Typography>
                  <form onSubmit={this.handleSubmit}>
                    <TextField
                      label="Email"
                      className={classes.textField}
                      margin="normal"
                      variant="outlined"
                      onChange={this.handleChange('email')}
                    />
                    <TextField
                      label="Password"
                      className={classes.textField}
                      margin="normal"
                      type="password"
                      variant="outlined"
                      onChange={this.handleChange('password')}
                    />
                    <TextField
                      label="Password Again"
                      className={classes.textField}
                      margin="normal"
                      type="password"
                      variant="outlined"
                      onChange={this.handleChange('passwordAgainLI')}
                    />
                    <Button
                      variant="contained"
                      color="secondary"
                      type="submit"
                      className={classes.buttonModal}
                      onClick={this.handleSubmit}
                    >
                      Sign Up
                    </Button>
                  </form>
                </div>
              )}
            </div>
          )}
        </Modal>
      </div>
    );
  }
}

UserSignIn.propTypes = {
  classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(withRouter(UserSignIn));
