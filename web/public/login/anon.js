/**
 * Handles the sign in button press.
 */
function toggleSignIn() {
    if (firebase.auth().currentUser) {
        // [START signout]
        firebase.auth().signOut();
        RESTLogout();
        // [END signout]
    } else {
        // [START authanon]
        firebase.auth().signInAnonymously().catch(function(error) {
            // Handle Errors here.
            var errorCode = error.code;
            // [START_EXCLUDE]
            if (errorCode === 'auth/operation-not-allowed') {
                alert('You must enable Anonymous auth in the Firebase Console.');
            } else {
                console.error(error);
            }
            // [END_EXCLUDE]
        });
        // [END authanon]
    }
    document.getElementById('quickstart-sign-in').disabled = true;
}

/**
 * initApp handles setting up UI event listeners and registering Firebase auth listeners:
 *  - firebase.auth().onAuthStateChanged: This listener is called when the user is signed in or
 *    out, and that is where we update the UI.
 */
function initApp() {
    // Listening for auth state changes.
    // [START authstatelistener]
    firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
            // User is signed in.
            var isAnonymous = user.isAnonymous;
            var uid = user.uid;
            // [START_EXCLUDE]
            document.getElementById('quickstart-sign-in-status').textContent = '';
            document.getElementById('quickstart-sign-in').textContent = 'Sign out';

            // on console instead
            let details = JSON.stringify(user, null, ' ');
            console.log(details)

            let status = 'Signed in';
            console.log(status)

            let result = user.refreshToken;
            console.log("Refresh token: " + result);
            RESTLogin(result);
            // [END_EXCLUDE]
        } else {
            // User is signed out.
            // [START_EXCLUDE]
            document.getElementById('quickstart-sign-in-status').textContent = '';

            let status = 'Signed out';
            console.log(status)

            document.getElementById('quickstart-sign-in').textContent = 'Sign in';
            document.getElementById('quickstart-account-details').textContent = '';
            // [END_EXCLUDE]
        }
        // [START_EXCLUDE]
        document.getElementById('quickstart-sign-in').disabled = false;
        // [END_EXCLUDE]
    });
    // [END authstatelistener]
    document.getElementById('quickstart-sign-in').addEventListener('click', toggleSignIn, false);
}
window.onload = function() {
    initApp();
};

function pollcode(){
    location.href = "../poll/pollcode.html";
}

function pollcreate(){
    location.href = "../poll/pollcreate.html";
}