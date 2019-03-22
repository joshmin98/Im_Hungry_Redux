import React, { useState, useEffect } from "react";
import { withRouter, Link } from "react-router-dom";
import styled from "styled-components";

/* Start page styling */
let Container = styled.div`
  padding-left: 10px;
  padding-right: 10px;
`;

const HeadingLayout = styled.div`
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  justify-content: space-between;
`;

const NavLayout = styled.div`
  width: 200px;
  display: flex;
  flex-direction: column;
`;

const NavSelect = styled.select`
  background-color: white;
  margin: 5px;
`;

const NavButton = styled.button`
  background-color: white;
  margin: 5px;
`;

const ItemLayout = styled.div`
  display: flex;
  flex-direction: row;
  padding: 10px;
  align-items: flex-start;
  justify-content: space-between;
  background-color: ${props => (props.dark ? "grey" : "#f5f5f5")};
`;

const RestaurantItemLayout = styled.div`
  display: flex;
  flex-direction: column;
`;
const RecipeItemLayout = styled.div`
  display: flex;
  flex-direction: column;
`;
/* End page styling */

let ListManagementPage = props => {
  /* Initialize page state */
  const [loading, setLoading] = useState(true);
  const [selectedList, setSelectedList] = useState("");
  const [recipes, setRecipes] = useState({ results: [{}] });
  const [restaurants, setRestaurants] = useState([]);

  /* Load data from localStorage */
  useEffect(() => {
    let localStorageRecipes = JSON.parse(
      localStorage.getItem(props.match.params.list)
    );
    console.log(localStorageRecipes);
    localStorageRecipes = localStorageRecipes.recipes;
    setRecipes(localStorageRecipes);

    let localStorageRestaurants = JSON.parse(
      localStorage.getItem(props.match.params.list)
    );
    localStorageRestaurants = localStorageRestaurants.restaurants;
    setRestaurants(localStorageRestaurants);

    setLoading(false);
  }, []);

  let results = loading ? (
    <p>Loading...</p>
  ) : (
    <Container>
      <HeadingLayout>
        <h1>{props.match.params.list}</h1>
        <NavLayout>
          <NavSelect
            onChange={event => setSelectedList(event.target.value)}
            defaultValue=""
          >
            <option value="" hidden="hidden" />
            <option>Favorites</option>
            <option>To Explore</option>
            <option>Do Not Show</option>
          </NavSelect>
          <NavButton
            onClick={() => {
              if (selectedList !== "") {
                props.history.push(`/lists/${selectedList}`);
              }
            }}
          >
            Manage Lists
          </NavButton>
          <NavButton onClick={() => props.history.push("/search")}>
            Return to Results
          </NavButton>
          <NavButton
            onClick={() => {
              props.history.push("/");
            }}
          >
            Return to Search
          </NavButton>
        </NavLayout>
      </HeadingLayout>

      {recipes.map((recipe, idx) => {
        let allRecipes = JSON.parse(localStorage.getItem("recipes"));
        allRecipes = allRecipes.results;
        let item = allRecipes[recipe];
        return (
          <Link to={`/recipe/${recipe}`} key={"recipe-" + idx}>
            <ItemLayout dark={idx % 2 === 0}>
              <RecipeItemLayout>
                <h2>{item.title}</h2>
                <p>
                  Prep:{" "}
                  {item.preparationMinutes ? item.preparationMinutes : "?"}{" "}
                  minutes
                </p>
                <p>Cook: {item.readyInMinutes} minutes</p>
              </RecipeItemLayout>
              <h2>${item.pricePerServing / 100}</h2>
            </ItemLayout>
          </Link>
        );
      })}
      {restaurants.map((restaurant, idx) => {
        let isDark = recipes.length % 2 === 0 ? idx % 2 === 0 : idx % 2 !== 0;
        let allRestaurants = JSON.parse(localStorage.getItem("restaurants"));
        let item = allRestaurants[restaurant];
        return (
          <Link to={`/restaurant/${restaurant}`} key={"restaurant-" + idx}>
            <ItemLayout dark={isDark}>
              <RestaurantItemLayout>
                <h2>{item.name}</h2>
                <p>Drive: </p>
                <p />
              </RestaurantItemLayout>
              <h2>{item.price}</h2>
            </ItemLayout>
          </Link>
        );
      })}
    </Container>
  );
  return results;
};

export default withRouter(ListManagementPage);
