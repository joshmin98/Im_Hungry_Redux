import React from "react";
import { withRouter } from "react-router-dom";
import { withStyles } from "@material-ui/core/styles";
import PropTypes from "prop-types";
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import Modal from "@material-ui/core/Modal";
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';


const styles = theme => ({
  root: {
    flexGrow: 1,
    marginLeft: 10
  },
  button: {
      width: '100%'
  },
  paper: {
    width: theme.spacing.unit * 50,
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[5],
    padding: theme.spacing.unit * 4,
    outline: 'none',
    width: '50%'
  },
  text: {
    textAlign: 'center',
    fontFamily: "'Arvo', serif",
    marginBottom: 40
  },
  textField: {
      width: '100%',
  },
  buttonModal: {
      width: '30%',
      marginLeft: '35%',
      marginTop: 20
  }
});

function getModalStyle() {
    return {
        top: '20%',
        left: '30%',
    }
}

class UserSignIn extends React.Component {
    state = {
        open: false,
        email: '',
        password: '',
        whichModal: '',
        emailLI: '',
        passwordLI: '',
        passwordAgainLI: ''
    };
    handleOpen = name => e => {
        this.setState({
            open: true,
            whichModal: name
        })
    };
    handleClose = () => {
        this.setState({
            open: false
        });
    };
    handleChange = name => e => {
        console.log(e.target);
        this.setState({
            [name]: e.target.value
        })
    }
    render() {
        const { classes } = this.props;
        return (
        <div className={classes.root}>
            <Grid container spacing={16}>
                <Grid item xs={6}>
                    <Button variant="contained" color="secondary" className={classes.button} onClick={this.handleOpen('login')}>
                        Log In
                    </Button> 
                </Grid>
                <Grid item xs={6}>
                    <Button variant="contained" color="secondary" className={classes.button} onClick={this.handleOpen('signUp')}>
                        Sign Up
                    </Button> 
                </Grid>
            </Grid>
            <Modal open={this.state.open} onClose={this.handleClose} style={getModalStyle()}>
                {this.state.whichModal === 'login' ? 
                    <div className={classes.paper}>
                        <Typography component="h3" variant="h2" className={classes.text}>
                            Log In
                        </Typography>
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
                            variant="outlined"
                            onChange={this.handleChange('password')}
                        />
                        <Button variant="contained" color="secondary" className={classes.buttonModal} onClick={this.handleClose}>
                            Log In
                        </Button> 
                    </div>
                :
                    <div className={classes.paper}>
                        <Typography component="h3" variant="h2" className={classes.text}>
                            Sign Up
                        </Typography>
                        <TextField
                            label="Email"
                            className={classes.textField}
                            margin="normal"
                            variant="outlined"
                            onChange={this.handleChange('emailLI')}
                        />
                        <TextField
                            label="Password"
                            className={classes.textField}
                            margin="normal"
                            variant="outlined"
                            onChange={this.handleChange('passwordLI')}
                        />
                        <TextField
                            label="Password Again"
                            className={classes.textField}
                            margin="normal"
                            variant="outlined"
                            onChange={this.handleChange('passwordAgainLI')}
                        />
                        <Button variant="contained" color="secondary" className={classes.buttonModal} onClick={this.handleClose}>
                            Sign Up
                        </Button> 
                    </div>
                }
            </Modal>
        </div>
        );
    }
}

UserSignIn.propTypes = {
  classes: PropTypes.object.isRequired
};
export default withStyles(styles)(withRouter(UserSignIn));