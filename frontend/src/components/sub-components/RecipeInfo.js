import React from "react";
import PropTypes from "prop-types";
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';

const styles = theme => ({
    root: {
      flexGrow: 1,
      paddingLeft: 30
    },
    header: {
        marginTop: 0,
        fontSize: 50
    },
    imgSize: {
        height: '30%',
        width: '30%'
    },
    containerGrid: {
        width: '50%'
    },
    textRight: {
        width: '40%',
        marginLeft: 'auto'
    }
});
class RecipeInfo extends React.Component {
    state = {
        name: 'Burger',
        img: 'https://www.epicurus.com/food/recipes/wp-content/uploads/2011/09/bbq-cheeseburger.jpg',
        preptime: '10 mins',
        cookTime: '20 mins',
        Ingredient: ['buns', 'beef patty', 'tomatoes', 'ketchup'],
        Instructions: ['step 1', 'step 2', 'step 3', 'step 4']
    };
    componentDidMount() {
        let info = JSON.parse(localStorage.getItem('recipes'));
        let ingredients = [];
        let instructions = [];
        info.results[this.props.id].analyzedInstructions[0].steps.forEach(el => {
            // parse ingredients
            el.ingredients.forEach( ingredient => {
                ingredients.push(ingredient.name);
            });

            // parse instructions
            instructions.push(el.step);
        });

        console.log(info.results[this.props.id]);

        this.setState({
            name: info.results[this.props.id].title,
            img: info.results[this.props.id].image,
            preptime: info.results[this.props.id].preparationMinutes.toString() + ' mins',
            cookTime: info.results[this.props.id].cookingMinutes.toString() + ' mins',
            Ingredient: ingredients,
            Instructions: instructions
        })
    }
    render() {
        const {classes} = this.props;
        return (
            <div className={classes.root}>
                <h1 className={classes.header}>{this.state.name}</h1>
                <img src={this.state.img} alt={this.state.name} className={classes.imgSize} />
                <p id="prepTime"><strong>Prep time: </strong> {this.state.preptime}</p>
                <p id="cookTime"><strong>Cook time: </strong> {this.state.cookTime}</p>
                <p><strong>Ingredients: </strong></p>
                <Grid container spacing={24} className={classes.containerGrid}>
                    <Grid item xs={6}>
                        <ul id="ingredientLeft">
                            {this.state.Ingredient.map((e,i) => {
                                if (i % 2 === 0) {
                                    return <li key={e}>{e}</li>
                                } else {
                                    return null
                                }
                            })}
                        </ul>
                    </Grid>
                    <Grid item xs={6}>
                        <ul id="ingredientRight">
                            {this.state.Ingredient.map((e,i) => {
                                if (i % 2 !== 0) {
                                    return <li className={classes.textRight} key={e}>{e}</li>
                                } else {
                                    return null
                                }
                            })}
                        </ul>
                    </Grid>
                </Grid>
                <p><strong>Instructions</strong></p>
                <ol id="instructions">
                    {this.state.Instructions.map((e) => {
                        return <li key={e}>{e}</li>
                    })}
                </ol>
            </div>
        ); 
    }
}

RecipeInfo.propTypes = {
    classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(RecipeInfo);
