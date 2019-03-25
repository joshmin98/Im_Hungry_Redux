import React from "react";
import { withRouter } from "react-router-dom";
import { withStyles } from "@material-ui/core/styles";
import PropTypes from "prop-types";
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';

const styles = theme => ({
  root: {
    flexGrow: 1,
    marginTop: 10,
  },
  gridList: {
    width: '100%',
    height: '40%',
  },
});

const photos = [
    {
      src: "https://s3-media3.fl.yelpcdn.com/bphoto/WPuB3CelYgclJqbgUEHJPA/o.jpg"
    },
    {
      src: "https://s3-media3.fl.yelpcdn.com/bphoto/uDA2-u7bb4ch83GioHcm1Q/o.jpg"
    },
    {
      src: "https://s3-media2.fl.yelpcdn.com/bphoto/IE5gIT5gXcS82yJAyxnG6g/o.jpg"
    },
    {
      src: "https://s3-media3.fl.yelpcdn.com/bphoto/zgZb9sXd2mxgr8HeM2K8pA/o.jpg"
    },
    {
      src: "https://spoonacular.com/recipeImages/715415-312x231.jpg"
    },
    {
      src: "https://s3-media2.fl.yelpcdn.com/bphoto/XlPaZEMXRPS3Vvn5kT7Y7w/o.jpg"
    },
  ];

class PhotoCollage extends React.Component {
  render() {
    const { classes } = this.props;
    return (
      <div className={classes.root}>
        <GridList cellHeight={160} className={classes.gridList} cols={3}>
            {photos.map(tile => (
            <GridListTile key={tile.src}>
                <img src={tile.src} />
            </GridListTile>
            ))}
        </GridList>
      </div>
    );
  }
}

PhotoCollage.propTypes = {
  classes: PropTypes.object.isRequired
};
export default withStyles(styles)(withRouter(PhotoCollage));