import { initializeApp, auth } from 'firebase';
import { auth as auth_ui } from 'firebaseui';

const firebaseConfig = {
    apiKey: "AIzaSyBVaiwbASOUfS4GoXaPYS62LgMaxsbSZG0",
    authDomain: "dat250-gr-2-h2020-app.firebaseapp.com",
    databaseURL: "https://dat250-gr-2-h2020-app.firebaseio.com",
    projectId: "dat250-gr-2-h2020-app",
    storageBucket: "dat250-gr-2-h2020-app.appspot.com",
    messagingSenderId: "851900401969",
    appId: "1:851900401969:web:32372ee6e94b18bbb4c810",
    measurementId: "G-03WHQEL0R0"
  };

  initializeApp(firebaseConfig);



auth().signInWithEmailAndPassword("test@test.no", "test@test.no").then(user => {
    console.log(user);
})

var ui = new auth_ui.AuthUI(auth());
ui.start('#firebaseui-auth-container', {
  signInOptions: [
    {
      provider: auth.EmailAuthProvider.PROVIDER_ID,
      requireDisplayName: false
    }
  ]
});