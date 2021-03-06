import React, { Component } from 'reactn';
import { BrowserRouter as Router, Route } from 'react-router-dom';

import PrintPage from './components/PrintPage';
import HomePage from './components/HomePage';
import ListManagementPage from './components/ListManagementPage.tsx';
import RestaurantPage from './components/RestaurantPage';
import RecipePage from './components/RecipePage';
import SearchPage from './components/SearchPage';
import AuthPage from './components/AuthPage';
import Grocery from './components/Grocery';

import firebase from './config/firebaseConfig';

export default class App extends Component {
  constructor(props) {
    super(props);
    this.setGlobal({ user: null });
  }

  componentDidMount() {
    firebase.auth().onAuthStateChanged(user => {
      if (user) {
        this.setGlobal({
          user: user.email,
        });
      } else {
        this.setGlobal({
          user: null,
        });
      }
    });
  }

  render() {
    return (
      <div className="App">
        <Router>
          <>
            <Route exact path="/" component={AuthPage} />
            <Route exact path="/homepage" component={HomePage} />
            <Route path="/lists/:list" component={ListManagementPage} />
            <Route path="/restaurant" component={RestaurantPage} />
            <Route path="/recipe" component={RecipePage} />
            <Route path="/search" component={SearchPage} />
            <Route path="/print/:name/" component={PrintPage} />
            <Route path="/grocery" component={Grocery} />
          </>
        </Router>
      </div>
    );
  }
}
