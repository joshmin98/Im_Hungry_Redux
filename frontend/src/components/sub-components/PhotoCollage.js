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


class PhotoCollage extends React.Component {
  render() {
    const { classes } = this.props;
    const images = this.props.photos.slice(0,10);
    return (
      <div className={classes.root}>
        <GridList cellHeight={160} className={classes.gridList} cols={5} id="images">
            {images.map(tile => (
            <GridListTile key={tile.src}>
                <img src={tile.src} alt="photos" />
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