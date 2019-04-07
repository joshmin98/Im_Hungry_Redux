import React from 'react';
import { withRouter } from 'react-router-dom';
import { withStyles } from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import Header from './sub-components/Header';
import PhotoCollage from './sub-components/PhotoCollage';
import Grid from '@material-ui/core/Grid';
import { Card, CardContent, CardActionArea } from '@material-ui/core';
import Typography from '@material-ui/core/Typography';
import Pagination from 'material-ui-flat-pagination';

const styles = theme => ({
  root: {
    flexGrow: 1,
    width: '100%'
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
  gray: {
    backgroundColor: 'lightgray'
  },
  title: {
    textAlign: 'center',
    marginTop: 10,
    marginBottom: 10,
    fontSize: 70
  }
});

class SearchPage extends React.Component {
  state = {
    offset: 0,
    restaurants: [],
    recipes: [],
    photos: []
  }
  componentDidMount() {
    var photos = [];
    this.props.location.state.restaurants.forEach(e => {
      photos.push({src: e.image_url})
    })
    this.props.location.state.recipes.forEach(e => {
      photos.push({src: e.image})
    })
    this.setState({
      restaurants: this.props.location.state.restaurants,
      recipes: this.props.location.state.recipes.sort((a,b) => (a.readyInMinutes > b.readyInMinutes) ? 1 : ((b.readyInMinutes > a.readyInMinutes)? -1 : 0)),
      photos: photos
    })
  }
  handleClick(offset) {
    this.setState({ offset });
  }
  handleClickRestaurant(element,event) {
    console.log(element);
    this.props.history.push({
      pathname: '/restaurant',
      state: { 
        restaurant: element
    }})
  }
  handleClickRecipe(element,event) {
    console.log(element);
    this.props.history.push({
      pathname: '/recipe',
      state: { 
        recipe: element
    }})
  }
  render() {
    console.log(this.props.location);
    const { classes } = this.props;
    var pagRestaurant = this.state.restaurants.slice(this.state.offset,this.state.offset+5);
    var pagRecipe = this.state.recipes.slice(this.state.offset,this.state.offset+5);

    return (
      <div className={classes.root} id="searchPage">
        <Header />
        <Typography component="h1" variant="h2" className={classes.title} id="headerTitle">
          
          Results For: {this.props.location.state.name}
          
        </Typography>
        <PhotoCollage photos={this.state.photos} />
        <Grid container spacing={16} className={classes.main}>
          <Grid item xs={6}>
            {pagRestaurant.map((e,i) => {
              return (
                <Card key={e.id} className={i%2 !== 0 ? [classes.card, classes.gray] : classes.card}>
                  <CardActionArea onClick={this.handleClickRestaurant.bind(this, e)}>
                  <CardContent>
                    <Typography gutterBottom variant="h5" component="h2">
                      <b>{e.name}</b>
                    </Typography>
                    <Typography component="p"><b>Estimate Driving Time: </b>{Math.ceil((e.distance / 4.4704) / 60) + " minutes"}</Typography>
                    <Typography component="p"><b>Address: </b>{e.location.address1 + ", " + e.location.city + ", " + e.location.state + e.location.zip_code}</Typography>
                    <Typography component="p"><b>Phone: </b>{e.phone}</Typography>
                  </CardContent>
                  </CardActionArea>
                </Card>
              );
            })}
          </Grid>
          <Grid item xs={6}>
            {pagRecipe.map((e,i) => {
              return (
                <Card key={e.id} className={i%2 !== 0 ? [classes.card, classes.gray] : classes.card}>
                  <CardActionArea onClick={this.handleClickRecipe.bind(this, e)}>
                  <CardContent>
                    <Typography gutterBottom variant="h5" component="h2">
                      <b>{e.title}</b>
                    </Typography>
                    <Typography component="p"><b>Cook Time: </b> {e.readyInMinutes}</Typography>
                  </CardContent>
                  </CardActionArea>
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
