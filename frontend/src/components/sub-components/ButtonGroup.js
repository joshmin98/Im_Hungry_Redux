import React from "react";
import PropTypes from 'prop-types';
import Button from "@material-ui/core/Button"
import {withStyles} from "@material-ui/core/styles";
import FormControl from "@material-ui/core/FormControl";
import NativeSelect from "@material-ui/core/NativeSelect";
import axios from 'axios';

const styles = theme => ({
    root: {
        flexGrow: 1,
        paddingRight: 30
    },
    button: {
        marginBottom: 10,
        width: '100%'
    },
    dropdown: {
        marginBottom: 10,
        width: '100%'
    },
    form: {
        width: '100%'
    }
})

class ButtonGroup extends React.Component {
    state = {
        value: '',
        open: false
    };
    handleChange = e => {
        this.setState({
            value: e.target.value
        });
    };
    addToList = () => {
        axios.default
            .get('http://localhost:8338/list/add', {
                withCredentials: true,
                params: {
                 listName: this.state.value,
                 id: this.props.info.id,
                },
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin' : '*',
                    'Access-Control-Allow-Methods' : 'GET,PUT,POST,DELETE,PATCH,OPTIONS',
                }
            }).then(res => {
                console.log(res);
            });
    }
    handBack = () => {
        this.props.history.push("/search");
    }
    printDocument = () => {
        this.props.restaurant ? 
            this.props.history.push({
                pathname: "/print/restaurant/",
                state: { 
                  info: this.props.info
                }
            }) 
            : 
            this.props.history.push({
                pathname: "/print/recipe/",
                state: { 
                  info: this.props.info
                }
            }); 
    }
    render() {
        const {classes} = this.props;
        console.log(this.props.info);
        return (
            <div className={classes.root}>
                <div>
                    <Button 
                        className={classes.button} 
                        variant="contained"
                        onClick={this.printDocument}
                    >
                        Printable View
                    </Button>
                </div>
                <div><Button className={classes.button} variant="contained" onClick={this.handBack}>Back to Result</Button></div>
                <div>
                    <FormControl className={classes.form}>
                        <NativeSelect
                            value={this.state.value}
                            onChange={this.handleChange}
                            className={classes.dropdown}
                        >
                            <option value="" disabled>
                                
                            </option>
                            <option value={"Favorites"}>Favorites</option>
                            <option value={"To Explore"}>To Explore</option>
                            <option value={"Do Not Show"}>Do Not Show</option>
                        </NativeSelect>
                    </FormControl>
                </div>
                <div><Button className={classes.button} variant="contained" onClick={this.addToList}>Add to List</Button></div>
            </div>
        ); 
    }
}

ButtonGroup.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ButtonGroup);