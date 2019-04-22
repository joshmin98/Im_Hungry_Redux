import firebase from 'firebase';

var config = {
  apiKey: 'AIzaSyCuTrQEGf-bCjFtZcmBNBmzSQo_995RlX4',
  authDomain: 'csci310project2-e2908.firebaseapp.com',
  databaseURL: 'https://csci310project2-e2908.firebaseio.com',
  projectId: 'csci310project2-e2908',
  storageBucket: 'csci310project2-e2908.appspot.com',
  messagingSenderId: '298643165340',
};

firebase.initializeApp(config);

export default firebase;
