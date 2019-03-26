import React from "react";
import { withRouter } from "react-router-dom";
import { withStyles } from "@material-ui/core/styles";
import PropTypes from "prop-types";
import Header from './sub-components/Header';
import PhotoCollage from './sub-components/PhotoCollage';
import recipe from '../recipes.json';
import restaurant from '../restaurants.json';
import Grid from '@material-ui/core/Grid';
import { Card, CardContent } from "@material-ui/core";

const styles = theme => ({
  root: {
    flexGrow: 1,
  },  
  drawerHeader: {
    display: 'flex',
    alignItems: 'center',
    padding: '0 8px',
    ...theme.mixins.toolbar,
    justifyContent: 'flex-start',
  },
  main: {
    marginTop: 10
  },
  card: {
    marginBottom: 10
  }
});

class SearchPage extends React.Component {
  render() {
    console.log(restaurant);
    const { classes } = this.props;
    return (
      <div className={classes.root}>
        <Header />
        <PhotoCollage />
        <Grid container spacing={16} className={classes.main}>
          <Grid item xs={6}>
            {restaurant.businesses.map(e => {
              return ( <Card key={e.name} className={classes.card}>
                <CardContent>
                  <strong>{e.name}</strong>
                </CardContent>
                <CardContent>
                  {e.location.address1}
                </CardContent>
              </Card>)
            })}          
          </Grid>
          <Grid item xs={6}>
          </Grid>
        </Grid>
      </div>
    );
  }
}

SearchPage.propTypes = {
  classes: PropTypes.object.isRequired
};
export default withStyles(styles)(withRouter(SearchPage));