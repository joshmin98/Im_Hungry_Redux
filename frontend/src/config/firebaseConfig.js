import firebase from 'firebase';

let config = {
  apiKey: 'AIzaSyB5QdHbvJ1iZnCUNaYqn84SOnL3455Ve8Q',
  authDomain: 'login-test-ff7da.firebaseapp.com',
  databaseURL: 'https://login-test-ff7da.firebaseio.com',
  projectId: 'login-test-ff7da',
  storageBucket: '',
  messagingSenderId: '824038302409',
};

firebase.initializeApp(config);

export default firebase;
