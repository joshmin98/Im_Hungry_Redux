import React from 'react';
import { withRouter } from 'react-router-dom';
import { withStyles } from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import UserSignIn from './sub-components/UserSignIn';
import axios from 'axios';

const url_restaurants = 'http://localhost:8338/restaurants';
const url_recipes = 'http://localhost:8338/recipes';

const styles = theme => ({
  root: {
    flexGrow: 1,
    marginTop: 10,
  },
  main: {
    marginTop: 150,
  },
  alignCenter: {
    textAlign: 'center',
    fontFamily: "'Arvo', serif",
  },
  textField: {
    width: '100%',
  },
  form: {
    width: '80%',
    marginLeft: 'auto',
    marginRight: 'auto',
    marginTop: 10,
  },
  button: {
    width: '100%',
    height: '45px',
    marginTop: '20px',
  },
  nav: {
    width: '20%',
    position: 'absolute',
    top: 10,
    right: 10,
  },
});

class HomePage extends React.Component {
  state = {
    searchVal: '',
    distance: 8000,
    limit: 5,
    restaurants: [],
    recipes: [],
  };
  handleSearchChange = e => {
    this.setState({
      searchVal: e.target.value,
    });
  };
  handleDistanceChange = e => {
    this.setState({
      distance: parseInt(e.target.value),
    });
  };
  handleLimit = e => {
    this.setState({
      limit: parseInt(e.target.value),
    });
  };
  handleClick = async () => {
    const response = await axios.get(url_restaurants, {
        params: {
          query: this.state.searchVal,
          radius: this.state.distance,
          limit: this.state.limit
        }
      }, {
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin' : '*',
        'Access-Control-Allow-Methods' : 'GET,PUT,POST,DELETE,PATCH,OPTIONS',
      }
     })
    this.setState({
      restaurants: response.data
    });

    const res = await axios.get(url_recipes, {
      params: {
        query: this.state.searchVal,
        radius: this.state.distance,
        limit: this.state.limit
      }
    }, {
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin' : '*',
        'Access-Control-Allow-Methods' : 'GET,PUT,POST,DELETE,PATCH,OPTIONS',
      }
    })
    this.setState({
      recipes: res.data
    })
    this.props.history.push({
      pathname: '/search',
      state: { 
        name: this.state.searchVal,
        restaurants: this.state.restaurants,
        recipes: this.state.recipes
      }})
  };

  render() {
    const { classes } = this.props;
    return (
      <div className={classes.root}>
        <div className={classes.nav}>
          <UserSignIn />
        </div>
        <div className={classes.main}>
          <Typography
            component="h2"
            variant="h1"
            className={classes.alignCenter}
          >
            I'm Hungry
          </Typography>
        </div>
        <Grid container spacing={16} className={classes.form}>
          <Grid item xs={6}>
            <TextField
              label="Enter Food"
              id="food"
              className={classes.textField}
              margin="normal"
              variant="outlined"
              onChange={this.handleSearchChange}
            />
          </Grid>
          <Grid item xs={2}>
            <TextField
              label="Distance"
              id="distance"
              className={classes.textField}
              margin="normal"
              variant="outlined"
              onChange={this.handleDistanceChange}
            />
          </Grid>
          <Grid item xs={2}>
            <TextField
              label="Limit"
              id="limit"
              className={classes.textField}
              margin="normal"
              variant="outlined"
              onChange={this.handleLimit}
            />
          </Grid>
          <Grid item xs={2}>
            <Button
              id="feedME"
              variant="contained"
              color="secondary"
              className={classes.button}
              onClick={this.handleClick}
            >
              Feed Me!
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
