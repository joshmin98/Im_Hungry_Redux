import React from "react";
import { withRouter } from "react-router-dom";
import { withStyles } from "@material-ui/core/styles";
import PropTypes from "prop-types";
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
import { ListItemText } from "@material-ui/core";

const styles = theme => ({
  root: {
    flexGrow: 1,
  },
  button: {
    position: 'absolute',
    right: 20
  }
});

class Header extends React.Component {
  state = {
    open: false
  }
  handleDrawerOpen = () => {
      this.setState({
          open: true
      })
  }
  handleDrawerClose = () => {
      this.setState({
          open: false
      })
  }
  render() {
    const { classes } = this.props;
    return (
      <div className={classes.root}>
        <AppBar position="static">
            <ToolBar>
                <Typography variant="h6" color="inherit">
                    I'm Hungry
                </Typography>
                <IconButton color="inherit" className={classes.button} onClick={this.handleDrawerOpen}>
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
                    <ListItem button>
                        <ListItemText primary="Printable View" />
                    </ListItem>
                    <ListItem button>
                        <ListItemText primary="Add To List" />
                    </ListItem>
                    <ListItem button>
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
  classes: PropTypes.object.isRequired
};
export default withStyles(styles)(withRouter(Header));