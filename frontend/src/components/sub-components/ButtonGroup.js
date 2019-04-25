import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import FormControl from '@material-ui/core/FormControl';
import axios from 'axios';
import { List, ListItemText, InputLabel, Select, MenuItem } from '@material-ui/core';
import ListItem from '@material-ui/core/ListItem';

const styles = theme => ({
  root: {
    flexGrow: 1,
  },
  button: {
    width: '100%',
  },
  dropdown: {
    width: '100%',
  },
  form: {
    width: '100%',
  },
  noPadding: {
    paddingTop: 0,
    paddingBottom: 0,
  },
  center: {
    paddingRight: 0,
    textAlign: 'center'
  }
});

class ButtonGroup extends React.Component {
  state = {
    value: '',
    open: false,
  };
  handleChange = e => {
    this.setState({
      value: e.target.value,
    });
  };
  addToList = () => {
    axios.default
      .get(
        'http://localhost:8338/list/add',
        {
          withCredentials: true,
          params: {
            listName: this.state.value,
            id: this.props.info.id,
          },
        },
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,PATCH,OPTIONS',
          },
        },
      )
      .then(res => {
        console.log(res);
      });
  };
  handBack = () => {
    this.props.history.push({
      pathname: '/search',
      state: {
        name: localStorage.getItem('name'),
        restaurants: JSON.parse(localStorage.getItem('restaurants')),
        recipes: JSON.parse(localStorage.getItem('recipes')),
      },
    });
  };
  printDocument = () => {
    this.props.restaurant
      ? this.props.history.push({
          pathname: '/print/restaurant/',
          state: {
            info: this.props.info,
          },
        })
      : this.props.history.push({
          pathname: '/print/recipe/',
          state: {
            info: this.props.info,
          },
        });
  };
  render() {
    const { classes } = this.props;
    // console.log(localStorage.getItem('name'));
    // console.log(JSON.parse(localStorage.getItem('restaurants')));
    // console.log(JSON.parse(localStorage.getItem('recipes')));
    return (
      <div className={classes.root}>
        <List>
          <ListItem className={classes.button} button>
            <ListItemText 
              primary="Printable View"
              onClick={this.printDocument}
              className={classes.center}
            />
          </ListItem>
          <ListItem className={classes.button} button>
            <ListItemText 
              primary="Back to Result"
              onClick={this.handBack}
              className={classes.center}
            />
          </ListItem>
          <ListItem className={classes.noPadding}>
            <FormControl className={classes.dropdown}>
              <InputLabel>List</InputLabel>
              <Select
                value={this.state.value}
                onChange={this.handleChange}
              >
                <MenuItem id="" value="">
                  <em>None</em>
                </MenuItem>
                <MenuItem id="Favorites" value={'Favorites'}>
                  Favorites
                </MenuItem>
                <MenuItem id="ToExplore" value={'To Explore'}>
                  To Explore
                </MenuItem>
                <MenuItem id="DoNotSow" value={'Do Not Show'}>
                  Do Not Show
                </MenuItem>
              </Select>
            </FormControl>
          </ListItem>
          <ListItem className={classes.button} button>
            <ListItemText 
              primary="Add to List"
              onClick={this.addToList}
              className={classes.center}
            />
          </ListItem>
        </List>
      </div>
    );
  }
}

ButtonGroup.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ButtonGroup);
