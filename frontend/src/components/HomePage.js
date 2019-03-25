import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";
import styled from "styled-components";
import { withStyles } from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import Button from "@material-ui/core/Button";
import UserSignIn from './sub-components/UserSignIn';

import { HungryProvider, HungryConsumer } from "./Context";

const url_prefix = "http://localhost:8080";

const styles = theme => ({
  root: {
    flexGrow: 1,
    marginTop: 10,
  },
  main: {
    marginTop: 150
  },
  alignCenter: {
    textAlign: 'center',
    fontFamily: "'Arvo', serif"
  },
  textField: {
    width: "100%",
  },
  form: {
    width: "80%",
    marginLeft: 'auto',
    marginRight: 'auto',
    marginTop: 10
  },
  button: {
    width: '100%',
    height: '45px',
    marginTop: '20px'
  },
  nav: {
    width: '20%',
    position: 'absolute',
    top: 10,
    right: 10
  }
});

class HomePage extends React.Component {
  state = {
    searchVal: '',
    distance: 5
  }
  handleSearchChange = (e) => {
    this.setState({
      searchVal: e.target.value
    });
  }
  handleDistanceChange = (e) => {
    this.setState({
      distance: parseInt(e.target.value)
    })
  }
  render() {
      const {classes} = this.props;
      return (
          <div className={classes.root}>
            <div className={classes.nav}>
              <UserSignIn />
            </div>
            <div className={classes.main}>
              <Typography component="h2" variant="h1" className={classes.alignCenter}>
                I'm Hungry
              </Typography>
            </div>
            <Grid container spacing={16} className={classes.form}>
              <Grid item xs={7} >
                <TextField
                  label="Seach for food"
                  placeholder="Placeholder"
                  className={classes.textField}
                  margin="normal"
                  variant="outlined"
                  onChange={this.handleSearchChange}
                />
              </Grid>
              <Grid item xs={3} >
                <TextField
                    label="Distance"
                    placeholder="Placeholder"
                    className={classes.textField}
                    margin="normal"
                    variant="outlined"
                    onChange={this.handleDistanceChange}
                />
              </Grid>
              <Grid item xs={2} >
                <Button variant="contained" color="secondary" className={classes.button}>
                  Search
                </Button> 
              </Grid>
            </Grid>
          </div>
      ); 
  }
}

HomePage.propTypes = {
  classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(withRouter(HomePage));