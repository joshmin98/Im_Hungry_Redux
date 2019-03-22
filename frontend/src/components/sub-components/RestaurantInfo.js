import React from "react";
import PropTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";

const styles = theme => ({
  root: {
    flexGrow: 1,
    paddingLeft: 30
  },
  header: {
    marginTop: 0,
    fontSize: 50
  },
  hoverText: {
    "&:hover": {
      cursor: "pointer"
    }
  }
});
class RestaurantInfo extends React.Component {
  state = {
    name: "Burger King",
    address: "1103 N Pass Ave, Burbank, CA 91505",
    phone: "818-445-7919",
    website: "facebook.com"
  };
  componentDidMount() {
    let info = JSON.parse(localStorage.getItem("restaurants"))[this.props.id];
    let address = "";
    info.location.display_address.forEach(el => {
      address += el + " ";
    });
    this.setState({
      name: info.name,
      address: address,
      phone: info.phone,
      website: info.url
    });
  }
  openGoogle = () => {
    let link =
      "http://maps.google.com/maps?q=" + encodeURIComponent(this.state.address);
    window.location.replace(link);
  };
  openWebsite = () => {
    window.location.replace(this.state.website);
  };
  render() {
    const { classes } = this.props;
    return (
      <div className={classes.root}>
        <h1 className={classes.header}>{this.state.name}</h1>
        <p id="address">
          <strong>Address:</strong>{" "}
          <button onClick={this.openGoogle} className={classes.hoverText}>
            {this.state.address}
          </button>
        </p>
        <p id="number">
          <strong>Phone Number: </strong>
          {this.state.phone}
        </p>
        <p id="website">
          <strong>Website: </strong>{" "}
          <button onClick={this.openWebsite} className={classes.hoverText}>
            {this.state.website}
          </button>
        </p>
      </div>
    );
  }
}

RestaurantInfo.propTypes = {
  classes: PropTypes.object.isRequired
};
export default withStyles(styles)(RestaurantInfo);
