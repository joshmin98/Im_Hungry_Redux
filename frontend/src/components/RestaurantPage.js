import React from 'react';
import { withRouter } from 'react-router-dom';
import RestaurantInfo from './sub-components/RestaurantInfo';
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import PropTypes from 'prop-types';
import Header from './sub-components/Header';

const styles = theme => ({
  root: {
    flexGrow: 1,
    marginTop: 10,
  },
  left: {
    padding: theme.spacing.unit * 2,
  },
  right: {
    padding: theme.spacing.unit * 2,
  },
});

class RestaurantPage extends React.Component {
  render() {
    const { classes } = this.props;
    return (
      <div className={classes.root} id="restaurantPrint">
        <Header
          searchPage={false}
          restaurant={true}
          info={this.props.location.state.restaurant}
          history={this.props.history}
        />
        <Grid container spacing={24}>
          <Grid item xs={10} className={classes.left}>
            <RestaurantInfo info={this.props.location.state.restaurant} />
          </Grid>
          <Grid item xs={2} className={classes.right} />
        </Grid>
      </div>
    );
  }
}

RestaurantPage.propTypes = {
  classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(withRouter(RestaurantPage));
