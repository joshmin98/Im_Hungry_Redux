import React, { Component } from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import PrintPage from "./components/PrintPage";
import HomePage from "./components/HomePage";
import ListManagementPage from "./components/ListManagementPage";
import RestaurantPage from "./components/RestaurantPage";
import RecipePage from "./components/RecipePage";
import SearchPage from "./components/SearchPage";

class App extends Component {
  render() {
    return (
      <div className="App">
        <Router>
          <>
            <Route exact path="/" component={HomePage} />
            <Route path="/lists/:list" component={ListManagementPage} />
            <Route path="/restaurant/:id" component={RestaurantPage} />
            <Route path="/recipe/:id" component={RecipePage} />
            <Route path="/search" component={SearchPage} />
            <Route path="/print/:name/:id" component={PrintPage} />
          </>
        </Router>
      </div>
    );
  }
}

export default App;
