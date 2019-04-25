import React from 'reactn';
import { withRouter } from 'react-router-dom';
import { withStyles } from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import UserSignIn from './sub-components/UserSignIn';
import axios from 'axios';
import firebase from '../config/firebaseConfig.js';

import SignOutButton from './sub-components/SignOutButton';

const url_restaurants = 'http://localhost:8338/restaurants';
const url_recipes = 'http://localhost:8338/recipes';

const styles = theme => ({
  root: {
    flexGrow: 1,
    marginTop: 10,
  },
  main: {
    marginTop: 200,
  },
  alignCenter: {
    textAlign: 'center',
    color: '#2B3252',
    fontWeight: 'bold',
  },
  textField: {
    width: '100%',
    backgroundColor: '#E6FAFC',
    //borderRadius: '5px'
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
    backgroundColor: '#fad744',
    color: '#2B3252',
    fontSize: '15px',
    fontWeight: 'bold',
  },
  smallerDiv: {
    width: '30%',
    marginLeft: 'auto',
    marginRight: 'auto',
  },
  nav: {
    width: '20%',
    position: 'absolute',
    top: 10,
    right: 10,
  },
  flexEnd: {
    display: 'flex',
    alignItems: 'flex-end',
    flexDirection: 'row-reverse',
  },
});

class HomePage extends React.Component {
  state = {
    searchVal: '',
    distance: 0,
    limit: 0,
    restaurants: [],
    recipes: [],
    loading: false,
  };
  componentDidMount() {
    console.log(this.props.location);
    this.props.location.state !== undefined &&
      this.setState({
        searchVal: this.props.location.state.term,
      });
  }
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
    this.setState({ loading: true });
    if (this.state.searchVal === '') {
      alert('Please enter a Food or Restaurant name!!!');
      this.setState({ loading: false });
      return;
    } else if (this.state.distance === 0 || isNaN(this.state.distance)) {
      alert('Please enter a Distance that is greater than 0!!');
      this.setState({ loading: false });
      return;
    } else if (this.state.limit === 0 || isNaN(this.state.limit)) {
      alert('Please enter a Limit that is greater than 0!!');
      this.setState({ loading: false });
      return;
    }
    const response = await axios.get(
      url_restaurants,
      {
        withCredentials: true,
        params: {
          query: this.state.searchVal,
          radius: this.state.distance,
          limit: this.state.limit,
        },
      },
      {
        headers: {
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,PATCH,OPTIONS',
        },
      },
    );
    this.setState({
      restaurants: response.data,
    });

    const res = await axios.get(
      url_recipes,
      {
        withCredentials: true,
        params: {
          query: this.state.searchVal,
          radius: this.state.distance,
          limit: this.state.limit,
        },
      },
      {
        headers: {
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,PATCH,OPTIONS',
        },
      },
    );
    this.setState({
      recipes: res.data,
    });
    // if (
    //   this.state.restaurants.length === 0 ||
    //   this.state.recipes.length === 0
    // ) {
    //   alert(
    //     "Sorry, We can't find any restaurants or recipes for " +
    //       this.state.searchVal,
    //   );
    //   this.setState({ loading: false });
    //   return;
    // }
    this.setState({ loading: false });
    localStorage.setItem('name', this.state.searchVal);
    localStorage.setItem('restaurants', JSON.stringify(this.state.restaurants));
    localStorage.setItem('recipes', JSON.stringify(this.state.recipes));
    this.props.history.push({
      pathname: '/search',
      state: {
        name: this.state.searchVal,
        restaurants: this.state.restaurants,
        recipes: this.state.recipes,
      },
    });
  };

  render() {
    const { classes } = this.props;
    return (
      <>
        <div className={classes.flexEnd}>
          <SignOutButton />
        </div>
        <div className={classes.root}>
          <div className={classes.nav} />
          <div>
            <div className={classes.main}>
              <Typography
                component="h2"
                variant="h1"
                className={classes.alignCenter}
                id="header"
              >
                I'm Hungry 🍽
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
                  required={true}
                  value={this.state.searchVal}
                />
              </Grid>
              <Grid item xs={3}>
                <TextField
                  label="Distance"
                  id="distance"
                  className={classes.textField}
                  margin="normal"
                  variant="outlined"
                  onChange={this.handleDistanceChange}
                  required={true}
                />
              </Grid>
              <Grid item xs={3}>
                <TextField
                  label="Limit"
                  id="limit"
                  className={classes.textField}
                  margin="normal"
                  variant="outlined"
                  onChange={this.handleLimit}
                  required={true}
                />
              </Grid>
            </Grid>
            <Grid className={classes.smallerDiv}>
              {this.state.loading ? (
                <Button
                  id="feedME"
                  variant="contained"
                  color="secondary"
                  className={classes.button}
                  disabled={true}
                >
                  Loading...
                </Button>
              ) : (
                <Button
                  id="feedME"
                  variant="contained"
                  color="secondary"
                  className={classes.button}
                  onClick={this.handleClick}
                >
                  Feed Me!
                </Button>
              )}
            </Grid>
          </div>
        </div>
      </>
    );
  }
}

HomePage.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(withRouter(HomePage));
