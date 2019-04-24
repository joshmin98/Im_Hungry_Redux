import React from "react";
import { withRouter } from "react-router-dom";
import { withStyles } from "@material-ui/core/styles";
import PropTypes from "prop-types";
import axios from 'axios';
import AppBar from '@material-ui/core/AppBar';
import ToolBar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import 

const styles = theme => ({
  root: {
    flexGrow: 1,
    marginTop: 10
  },
  Header: {
    backgroundColor: '#E6FAFC',
    color: '#2B3252',
    boxShadow: 'none',
  },
  fontCss: {
    fontWeight: 'bold',
  },
  title: {
    textAlign: 'center',
    marginTop: 15,
    marginBottom: 15,
    fontSize: 70,
    fontWeight: 'bold',
    color: '#2B3252',
  },
});

class Grocery extends React.Component {
  state = {
      item: []
  }  
  componentDidMount() {
    axios.get(
        "http://localhost:8338/grocery",
        {
          withCredentials: true,
        },
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,PATCH,OPTIONS',
          },
        },
      ).then(res => {
          console.log(res)
      })
    
  }
  render() {
    const { classes } = this.props;
    return (
      <div className={classes.root}>
        <AppBar position="static" className={classes.Header}>
            <ToolBar>
                <Typography
                    variant="h6"
                    color="inherit"
                    className={classes.fontCss}
                >
                    I'm Hungry 🍽
                </Typography>
            </ToolBar>
        </AppBar>
        <div>
            <Typography
                component="h1"
                variant="h2"
                className={classes.title}
                id="header"
            >
                Grocery Lists
            </Typography>
        </div>
      </div>
    );
  }
}

Grocery.propTypes = {
  classes: PropTypes.object.isRequired
};
export default withStyles(styles)(withRouter(Grocery));