import React from 'react';
import { withRouter } from 'react-router-dom';
import { withStyles } from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import Header from './sub-components/Header';
import PhotoCollage from './sub-components/PhotoCollage';
import Grid from '@material-ui/core/Grid';
import { Card, CardContent, CardActionArea, Button } from '@material-ui/core';
import Typography from '@material-ui/core/Typography';
import Pagination from 'material-ui-flat-pagination';
import axios from 'axios';

const styles = theme => ({
  root: {
    flexGrow: 1,
    marginRight: '5%',
    marginLeft: '5%',
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
    marginBottom: 12,
    height: '153px',
  },
  gray: {
    // backgroundColor: '#FFF275',
  },
  title: {
    textAlign: 'center',
    marginTop: 15,
    marginBottom: 15,
    fontSize: 70,
    fontWeight: 'bold',
    color: '#2B3252',
  },
  actionArea: {
    height: '100%',
  },
  photobox: {
    marginRight: '2%',
    marginLeft: '2%',
  },
  pagination: {
    marginTop: 10,
    marginBottom: 20,
  },
  pagButtonInactive: {
    marginTop: 13,
    color: 'darkgray',
  },
  pagButtonActive: {
    marginTop: 13,
    color: '#3f51b5',
  },
  groceryListBtn: {
    float: 'right',
    color: '#2B3252',
    backgroundColor: '#fad744',
    marginBottom: 10,
  },
  cardRecipe: {
    marginBottom: 10,
  },
});

class SearchPage extends React.Component {
  state = {
    offset: 0,
    restaurants: [],
    recipes: [],
    photos: [],
    size: 0,
  };
  componentDidMount() {
    var photos = [];
    this.props.location.state.restaurants.forEach(e => {
      photos.push({ src: e.image_url });
    });
    this.props.location.state.recipes.forEach(e => {
      photos.push({ src: e.image });
    });
    this.setState({
      restaurants: this.props.location.state.restaurants,
      recipes: this.props.location.state.recipes.sort((a, b) =>
        a.readyInMinutes > b.readyInMinutes
          ? 1
          : b.readyInMinutes > a.readyInMinutes
          ? -1
          : 0,
      ),
      photos: photos,
      size: this.props.location.state.restaurants.length,
    });
  }
  handleClick(offset) {
    this.setState({ offset });
  }
  handleClickRestaurant(element, event) {
    this.props.history.push({
      pathname: '/restaurant',
      state: {
        restaurant: element,
      },
    });
  }
  handleClickRecipe(element, event) {
    this.props.history.push({
      pathname: '/recipe',
      state: {
        recipe: element,
      },
    });
  }
  handleBackToFist() {
    this.setState({
      offset: 0,
    });
  }
  handleToLast() {
    this.setState({
      offset: this.state.size - 5,
    });
  }
  handleGrocery = async item => {
    const res = await axios.get(
      'http://localhost:8338/grocery/add',
      {
        withCredentials: true,
        params: {
          id: item,
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
  };
  render() {
    const { classes } = this.props;
    var pagRestaurant = this.state.restaurants.slice(
      this.state.offset,
      this.state.offset + 5,
    );
    var pagRecipe = this.state.recipes.slice(
      this.state.offset,
      this.state.offset + 5,
    );
    return (
      <div>
        <Header searchPage={true} />
        {this.state.restaurants.length === 0 &&
        this.state.recipes.length === 0 ? (
          <Typography
            component="h1"
            variant="h2"
            className={classes.title}
            id="header"
          >
            Error! No results for "
            {this.props.location.state !== undefined &&
              this.props.location.state.name}
            "
          </Typography>
        ) : (
          <Typography
            component="h1"
            variant="h2"
            className={classes.title}
            id="header"
          >
            Results for "
            {this.props.location.state !== undefined &&
              this.props.location.state.name}
            "
          </Typography>
        )}
        <div className={classes.photobox}>
          <PhotoCollage photos={this.state.photos} />
        </div>
        <div className={classes.root} id="searchPage">
          <Grid container spacing={16} className={classes.main}>
            <Grid item xs={6} id="restaurants">
              {pagRestaurant.map((e, i) => {
                return (
                  <Card
                    key={e.id}
                    // className={
                    //   i % 2 !== 0 ? [classes.card, classes.gray] : classes.card
                    // }
                    className={classes.card}
                  >
                    <CardActionArea
                      onClick={this.handleClickRestaurant.bind(this, e)}
                      className={classes.actionArea}
                      id={e.id}
                    >
                      <CardContent>
                        <Typography
                          gutterBottom
                          variant="h5"
                          component="h2"
                          className="restaurantName"
                        >
                          <b>{e.name}</b>
                        </Typography>
                        <Typography component="p" className="drivingTime">
                          <b>Estimate Driving Time: </b>
                          {Math.ceil(e.distance / 4.4704 / 60) + ' minutes'}
                        </Typography>
                        <Typography
                          component="p"
                          className="restaurantAddrress"
                        >
                          <b>Address: </b>
                          {e.location.address1 +
                            ', ' +
                            e.location.city +
                            ', ' +
                            e.location.state +
                            e.location.zip_code}
                        </Typography>
                        <Typography
                          component="p"
                          className="restaurantPhoneNumber"
                        >
                          <b>Phone: </b>
                          {e.phone}
                        </Typography>
                      </CardContent>
                    </CardActionArea>
                  </Card>
                );
              })}
            </Grid>
            <Grid item xs={6} id="recipes">
              {pagRecipe.map((e, i) => {
                return (
                  <Card key={e.id} className={classes.cardRecipe}>
                    <CardActionArea
                      onClick={this.handleClickRecipe.bind(this, e)}
                      className={classes.actionArea}
                      id={e.id}
                    >
                      <CardContent>
                        <Typography
                          gutterBottom
                          variant="h5"
                          component="h2"
                          className="recipeName"
                        >
                          <b>{e.title}</b>
                        </Typography>
                        <Typography component="p" className="cookTime">
                          <b>Cook Time: </b> {e.readyInMinutes}
                        </Typography>
                      </CardContent>
                    </CardActionArea>
                    <CardContent>
                      <Button
                        className={classes.groceryListBtn}
                        onClick={() => {
                          this.handleGrocery(e.id);
                        }}
                        id={'grocery-' + i}
                      >
                        Add To Grocery List
                      </Button>
                    </CardContent>
                  </Card>
                );
              })}
            </Grid>
          </Grid>
          <Grid container>
            <Grid item>
              <Button
                className={
                  this.state.offset !== 0
                    ? classes.pagButtonActive
                    : classes.pagButtonInactive
                }
                disabled={this.state.offset === 0 ? true : false}
                onClick={
                  this.state.offset !== 0 && this.handleBackToFist.bind(this)
                }
              >
                First
              </Button>
            </Grid>
            <Grid item>
              <div id="pagination">
                <Pagination
                  className={classes.pagination}
                  limit={5}
                  offset={this.state.offset}
                  total={this.state.size}
                  onClick={(e, offset) => this.handleClick(offset)}
                  size="large"
                  previousPageLabel="Previous"
                  nextPageLabel="next"
                />
              </div>
            </Grid>
            <Grid item>
              <Button
                className={
                  this.state.offset === 0
                    ? classes.pagButtonActive
                    : classes.pagButtonInactive
                }
                disabled={
                  this.state.offset === this.state.size - 5 ? true : false
                }
                onClick={
                  this.state.offset !== this.state.size - 5 &&
                  this.handleToLast.bind(this)
                }
              >
                Last
              </Button>
            </Grid>
          </Grid>
        </div>
      </div>
    );
  }
}

SearchPage.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(withRouter(SearchPage));
