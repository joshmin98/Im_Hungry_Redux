import React from 'react';
import { withRouter } from 'react-router-dom';
import { withStyles } from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import Header from './sub-components/Header';
import PhotoCollage from './sub-components/PhotoCollage';
import recipe from '../recipes.json';
import restaurant from '../restaurants.json';
import Grid from '@material-ui/core/Grid';
import { Card, CardContent } from '@material-ui/core';
import Typography from '@material-ui/core/Typography';
import Pagination from 'material-ui-flat-pagination';

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
    marginTop: 10,
  },
  card: {
    marginBottom: 10,
    height: "140px"
  },
});

class SearchPage extends React.Component {
  state = {
    offset: 0,
    restaurants: this.props.location.state.restaurants,
    recipes: this.props.location.state.recipes.sort((a,b) => (a.readyInMinutes > b.readyInMinutes) ? 1 : ((b.readyInMinutes > a.readyInMinutes)? -1 : 0))
  }
  handleClick(offset) {
    this.setState({ offset });
  }
  render() {
    console.log(this.state.offset);
    const { classes } = this.props;
    var pagRestaurant = this.state.restaurants.slice(this.state.offset,this.state.offset+5);
    var pagRecipe = this.state.recipes.slice(this.state.offset,this.state.offset+5);
    console.log(pagRestaurant);
    console.log(pagRecipe);
    return (
      <div className={classes.root}>
        <Header />
        <PhotoCollage />
        <Grid container spacing={16} className={classes.main}>
          <Grid item xs={6}>
            {pagRestaurant.map(e => {
              return (
                <Card key={e.id} className={classes.card}>
                  <CardContent>
                    <Typography gutterBottom variant="h5" component="h2">
                      <b>{e.name}</b>
                    </Typography>
                    <Typography component="p"><b>Estimate Driving Time: </b>{Math.ceil((e.distance / 4.4704) / 60) + " minutes"}</Typography>
                    <Typography component="p"><b>Address: </b>{e.location.address1 + ", " + e.location.city + ", " + e.location.state + e.location.zip_code}</Typography>
                    <Typography component="p"><b>Phone: </b>{e.phone}</Typography>
                  </CardContent>
                </Card>
              );
            })}
          </Grid>
          <Grid item xs={6}>
            {pagRecipe.map(e => {
              return (
                <Card key={e.id} className={classes.card}>
                  <CardContent>
                    <Typography gutterBottom variant="h5" component="h2">
                      <b>{e.title}</b>
                    </Typography>
                    <Typography component="p"><b>Cook Time: </b> {e.readyInMinutes}</Typography>
                  </CardContent>
                </Card>
              );
            })}
          </Grid>
        </Grid>
        <Pagination
        limit={5}
        offset={this.state.offset}
        total={10}
        onClick={(e, offset) => this.handleClick(offset)}
        />
      </div>
    );
  }
}

SearchPage.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(withRouter(SearchPage));
