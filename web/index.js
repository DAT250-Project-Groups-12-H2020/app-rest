// Firebase App (the core Firebase SDK) is always required and
// must be listed before other Firebase SDKs
var firebase = require("firebase/app");

// Add the Firebase products that you want to use
require("firebase/auth");
require("firebase/firestore");

// For Firebase JavaScript SDK v7.20.0 and later, `measurementId` is an optional field
var firebaseConfig = {
    apiKey: "AIzaSyBVaiwbASOUfS4GoXaPYS62LgMaxsbSZG0",
    authDomain: "dat250-gr-2-h2020-app.firebaseapp.com",
    databaseURL: "https://dat250-gr-2-h2020-app.firebaseio.com",
    projectId: "dat250-gr-2-h2020-app",
    storageBucket: "dat250-gr-2-h2020-app.appspot.com",
    messagingSenderId: "851900401969",
    appId: "1:851900401969:web:32372ee6e94b18bbb4c810",
    measurementId: "G-03WHQEL0R0"
  };

function signInEmail() {
    firebase.auth().signInWithEmailAndPassword(email, password).catch(function(error) {
        // Handle Errors here.
        var errorCode = error.code;
        var errorMessage = error.message;
        // ...
        print(errorCode, ": ", errorMessage)
      });
}

function newUserEmail() {
    firebase.auth().signInWithEmailAndPassword(email, password).catch(function(error) {
        // Handle Errors here.
        var errorCode = error.code;
        var errorMessage = error.message;
        // ...
      });
}
  