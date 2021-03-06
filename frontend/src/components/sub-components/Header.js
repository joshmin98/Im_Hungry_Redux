import React from 'reactn';
import { withRouter } from 'react-router-dom';
import { withStyles } from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import AppBar from '@material-ui/core/AppBar';
import ToolBar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import MenuIcon from '@material-ui/icons/Menu';
import IconButton from '@material-ui/core/IconButton';
import Drawer from '@material-ui/core/Drawer';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import Divider from '@material-ui/core/Divider';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ButtonGroup from './ButtonGroup';
import axios from 'axios';
import {
  ListItemText,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Input,
} from '@material-ui/core';

import firebase from '../../config/firebaseConfig.js';

const url = 'http://localhost:8338/searches';

const styles = theme => ({
  root: {
    flexGrow: 1,
  },
  button: {
    position: 'absolute',
    right: 20,
  },
  Header: {
    backgroundColor: '#E6FAFC',
    color: '#2B3252',
    boxShadow: 'none',
  },
  fontCss: {
    fontWeight: 'bold',
  },
  dropdown: {
    width: '100%',
  },
  noPadding: {
    paddingTop: 0,
    paddingBottom: 0,
  },
  marginTop: {
    //paddingTop: 10,
  },
  drawer: {
    //backgroundColor: '#2b3152',
    height: '100%',
  },
  drawerSize: {
    width: 200,
  },
  sideBarButton: {
    marginTop: 10,
    borderRadius: '10px',
    width: '95%',
    marginLeft: 'auto',
    marginRight: 'auto',
    backgroundColor: '#fad744',
    color: '#2B3252',
  },
  center: {
    paddingRight: 0,
    textAlign: 'center',
  },
});

class Header extends React.Component {
  state = {
    open: false,
    list: '',
    terms: '',
    searchTerm: [],
  };
  componentDidMount() {
    const res = axios
      .get(
        url,
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
      )
      .then(res => {
        //console.log(res);
        this.setState({ searchTerm: res.data });
      });
    //console.log(res);
  }
  handleDrawerOpen = () => {
    this.setState({
      open: true,
    });
  };

  handleDrawerClose = () => {
    this.setState({
      open: false,
    });
  };

  handleChange = e => {
    this.setState({
      list: e.target.value,
    });
  };

  handleList = () => {
    if (this.state.list !== '') {
      this.props.history.push('/lists/' + this.state.list);
    }
  };

  handleBack = () => {
    this.props.history.push('/');
  };

  handleTermChange = e => {
    //console.log(e.target.value);
    this.props.history.push({
      pathname: '/homepage',
      state: {
        term: e.target.value,
      },
    });
  };

  handleGrocery = () => {
    this.props.history.push('/grocery');
  };

  handleLogout = () => {
    firebase
      .auth()
      .signOut()
      .then(resp => {
        axios.get('http://localhost:8338/logout').then(resp => {
          console.log(resp);
          this.props.history.push('/');
        });
      })
      .catch(err => {
        console.log(err);
      });
  };

  render() {
    const { classes } = this.props;
    //console.log(this.state.searchTerm);
    let prevSearchQuery = new Set();
    this.state.searchTerm !== [] &&
      this.state.searchTerm.forEach(el => {
        //console.log(el.query);
        prevSearchQuery.add(el.query);
      });
    // console.log(prevSearchQuery);
    let myArr = Array.from(prevSearchQuery);
    //console.log(myArr);
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
            <IconButton
              color="inherit"
              className={classes.button}
              onClick={this.handleDrawerOpen}
              id="menuBtn"
            >
              <MenuIcon />
            </IconButton>
          </ToolBar>
        </AppBar>
        <Drawer
          variant="persistent"
          anchor="right"
          open={this.state.open}
          className={classes.drawerSize}
        >
          <div className={classes.drawer}>
            <IconButton onClick={this.handleDrawerClose}>
              <ChevronRightIcon />
            </IconButton>
            <Divider />
            {this.props.searchPage ? (
              <List className={classes.drawerSize}>
                <ListItem className={classes.noPadding}>
                  <FormControl className={classes.dropdown}>
                    <InputLabel>Search Terms</InputLabel>
                    <Select
                      value={this.state.terms}
                      onChange={this.handleTermChange}
                      input={<Input name="terms" id="terms" />}
                      id="termDropdown"
                      variant="outlined"
                    >
                      <MenuItem id="" value="">
                        <em>None</em>
                      </MenuItem>
                      {myArr.map(el => {
                        return (
                          <MenuItem id={el} value={el} key={el}>
                            {el}
                          </MenuItem>
                        );
                      })}
                    </Select>
                  </FormControl>
                </ListItem>
                <ListItem className={classes.noPadding}>
                  <FormControl className={classes.dropdown}>
                    <InputLabel>List</InputLabel>
                    <Select
                      value={this.state.list}
                      onChange={this.handleChange}
                      input={<Input name="list" id="list" />}
                      id="listDropdown"
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
                <ListItem button className={classes.sideBarButton}>
                  <ListItemText
                    primary="Manage Lists"
                    onClick={this.handleList}
                    className={classes.center}
                    id="Manage Lists"
                  />
                </ListItem>
                <ListItem button className={classes.sideBarButton}>
                  <ListItemText
                    primary="Grocery Lists"
                    onClick={this.handleGrocery}
                    id="Grocery-Lists"
                    className={classes.center}
                  />
                </ListItem>
                <ListItem button className={classes.sideBarButton}>
                  <ListItemText
                    className={classes.center}
                    primary="Return to Search"
                    onClick={this.handleBack}
                    id="Return to Search"
                  />
                </ListItem>
                <ListItem
                  id="logout"
                  button
                  onClick={this.handleLogout}
                  className={classes.sideBarButton}
                >
                  <ListItemText primary="Log Out" className={classes.center} />
                </ListItem>
              </List>
            ) : (
              <ButtonGroup
                restaurant={this.props.restaurant}
                info={this.props.info}
                history={this.props.history}
              />
            )}
          </div>
        </Drawer>
      </div>
    );
  }
}

Header.propTypes = {
  classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(withRouter(Header));
