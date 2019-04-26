import React from "react";
import { withRouter } from "react-router-dom";
import { withStyles } from "@material-ui/core/styles";
import PropTypes from "prop-types";
import RecipeInfo from "./sub-components/RecipeInfo";
import RestaurantInfo from "./sub-components/RestaurantInfo";

const styles = theme => ({
  root: {
    flexGrow: 1,
    marginTop: 10
  }
});

class PrintPage extends React.Component {
  render() {
    const { classes } = this.props;
    return (
      <div className={classes.root}>
        {this.props.match.params.name === "recipe" ? <RecipeInfo info={this.props.location.state.info}/> : <RestaurantInfo info={this.props.location.state.info}/>}
      </div>
    );
  }
}

PrintPage.propTypes = {
  classes: PropTypes.object.isRequired
};
export default withStyles(styles)(withRouter(PrintPage));