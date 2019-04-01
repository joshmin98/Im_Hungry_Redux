import React, { Component } from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import PrintPage from './components/PrintPage';
import HomePage from './components/HomePage';
import ListManagementPage from './components/ListManagementPage.tsx';
import RestaurantPage from './components/RestaurantPage';
import RecipePage from './components/RecipePage';
import SearchPage from './components/SearchPage';
import firebase from './config/firebaseConfig';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      user: null,
    };
  }

  componentDidMount() {
    this.authListener();
  }

  authListener() {
    firebase.auth().onAuthStateChanged(user => {
      if (user) {
        this.setState({ user });
        console.log(user);
      } else {
        this.setState({ user: null });
      }
    });
  }

  render() {
    return (
      <div className="App">
        <Router>
          <>
            <Route exact path="/" component={HomePage} />
            <Route path="/lists/:list" component={ListManagementPage} />
            <Route path="/restaurant" component={RestaurantPage} />
            <Route path="/recipe" component={RecipePage} />
            <Route path="/search" component={SearchPage} />
            <Route path="/print/:name/:id" component={PrintPage} />
          </>
        </Router>
      </div>
    );
  }
}

export default App;
