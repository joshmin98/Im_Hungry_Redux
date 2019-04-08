import React from 'react';
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
import {
  ListItemText,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Input,
} from '@material-ui/core';

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
    boxShadow: 'none'
  },
  fontCss: {
    fontWeight: 'bold'
  }
});

class Header extends React.Component {
  state = {
    open: false,
    list: '',
  };

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
    this.props.history.push('/lists/' + this.state.list);
  };

  handleBack = () => {
    this.props.history.push('/');
  };

  handleLogout = () => {};
  render() {
    const { classes } = this.props;
    return (
      <div className={classes.root}>
        <AppBar position="static" className={classes.Header}>
            <ToolBar>
                <Typography variant="h6" color="inherit" className={classes.fontCss}>
                    I'm Hungry
                </Typography>
                <IconButton color="inherit" className={classes.button} onClick={this.handleDrawerOpen} id="menuBtn">
                    <MenuIcon />
                </IconButton>
            </ToolBar>
        </AppBar>
        <Drawer variant="persistent" anchor="right" open={this.state.open}>
          <div>
            <IconButton onClick={this.handleDrawerClose}>
              <ChevronRightIcon />
            </IconButton>
            <Divider />
            <List>
              <ListItem>
                <FormControl>
                  <InputLabel>List</InputLabel>
                  <Select
                    value={this.state.list}
                    onChange={this.handleChange}
                    input={<Input name="list" id="list" />}
                    id="dropdown"
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
              <ListItem button>
                <ListItemText primary="Manage List" onClick={this.handleList} />
              </ListItem>
              <ListItem button>
                <ListItemText
                  primary="Return to Search"
                  onClick={this.handleBack}
                />
              </ListItem>
              <ListItem button onClick={this.handleLogout}>
                <ListItemText primary="Log Out" />
              </ListItem>
            </List>
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
