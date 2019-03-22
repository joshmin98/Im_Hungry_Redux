import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";
import styled from "styled-components";

import { HungryProvider, HungryConsumer } from "./Context";

const url_prefix = "http://localhost:8080";

const PageLayout = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const SearchLayout = styled.div`
  display: flex;
  flex-direction: row;
`;

const SearchInput = styled.input`
  background-color: white;
  margin: 5px;
`;

const SearchButton = styled.button`
  background-color: red;
  color: white;
  margin: 5px;
`;

let HomePage = props => {
  const [searchQuery, setSearchQuery] = useState("");
  const [numResults, setNumResults] = useState(5);
  const [loading, setLoading] = useState(false);

  const sendQuery = () => {
    if (numResults < 1) {
      alert("Results must be greater than 1!");
      return;
    }

    setLoading(true);

    /* let fetchDrivetime =  (to, idx) => {
         *     return fetch(
         *         `http://www.mapquestapi.com/directions/v2/route?key=M0uBDKuMB2ap4E5dt1gMTkXqj7eYeAgc&from=USC,Los Angeles,CA&to=${
         *         to.address1
           },${to.city},${to.state}`
         *     ).then(resp => resp.json()).then(data => {
         *         let update = driveTimes;
         *         update[idx] = data.route.formattedTime;
         *         setDriveTimes(update);
         *     });
         * };
         */

    fetch(
      `${url_prefix}/RecipeServlet?query=${searchQuery}&numResults=${numResults}`
    )
      .then(resp => resp.json())
      .then(data => {
        localStorage.setItem("searchRecipes", JSON.stringify(data));

        let recipes = JSON.parse(localStorage.getItem("recipes"));
        if (recipes == null) {
          recipes = localStorage.getItem("searchRecipes");
          localStorage.setItem("recipes", recipes);
          localStorage.setItem("recipeIndex", "0");
        } else {
          let newRecipes = JSON.parse(localStorage.getItem("searchRecipes"));
          recipes = JSON.parse(localStorage.getItem("recipes"));
          let recipeIndex = recipes.results.length;
          localStorage.setItem("recipeIndex", recipeIndex.toString());
          recipes.results.push(...newRecipes.results);
          localStorage.setItem("recipes", JSON.stringify(recipes));
        }
      })
      .then(() => {
        fetch(
          `${url_prefix}/RestaurantServlet?query=${searchQuery}&numResults=${numResults}`
        )
          .then(resp => resp.json())
          .then(data => {
            localStorage.setItem(
              "searchRestaurants",
              JSON.stringify(data.businesses)
            );

            let restaurants = JSON.parse(localStorage.getItem("restaurants"));
            if (restaurants == null) {
              restaurants = localStorage.getItem("searchRestaurants");
              localStorage.setItem("restaurants", restaurants);
              localStorage.setItem("restaurantIndex", "0");
            } else {
              let newRestaurants = JSON.parse(
                localStorage.getItem("searchRestaurants")
              );
              restaurants = JSON.parse(localStorage.getItem("restaurants"));
              let restaurantIndex = restaurants.length;
              localStorage.setItem(
                "restaurantIndex",
                restaurantIndex.toString()
              );
              restaurants.push(...newRestaurants);
              localStorage.setItem("restaurants", JSON.stringify(restaurants));
            }
          });
      })
      .then(() => {
        // {restaurant: true, id: xxx}
        if (localStorage.getItem("Favorites") == null) {
          localStorage.setItem(
            "Favorites",
            JSON.stringify({
              recipes: [],
              restaurants: []
            })
          );
        }
        if (localStorage.getItem("To Explore") == null) {
          localStorage.setItem(
            "To Explore",
            JSON.stringify({
              recipes: [],
              restaurants: []
            })
          );
        }
        if (localStorage.getItem("Do Not Show") == null) {
          localStorage.setItem(
            "Do Not Show",
            JSON.stringify({
              recipes: [],
              restaurants: []
            })
          );
        }
      })
      .then(() => {
        localStorage.setItem("query", searchQuery);
        let restaurants = JSON.parse(localStorage.getItem("restaurants"));
        let recipes = JSON.parse(localStorage.getItem("recipes"));
        window.setTimeout(() => {
          props.history.push("/search");
        }, 2000);
      });
  };

  return (
    <PageLayout>
      <h1>I'm Hungry</h1>
      <SearchLayout>
        <form
          onSubmit={event => {
            event.preventDefault();
            sendQuery();
          }}
        >
          <SearchInput
            value={searchQuery}
            id="search"
            placeholder="Enter Food"
            onChange={event => {
              setSearchQuery(event.target.value);
            }}
          />
          <SearchInput
            value={numResults}
            id="numResults"
            data-tip="Number of items to show in results"
            onChange={event => {
              setNumResults(event.target.value);
            }}
          />
          <SearchButton onClick={sendQuery}>Feed Me!</SearchButton>
        </form>
      </SearchLayout>
      {loading ? <p>Loading...</p> : null}
    </PageLayout>
  );
};

export default withRouter(HomePage);
