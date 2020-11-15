/**
 * Handles the sign in button press.
 */
function toggleSignIn() {
    // Disable the sign-in button during async sign-in tasks.
    document.getElementById('quickstart-sign-in').disabled = true;
    if (firebase.auth().currentUser) {
        // [START signout]
        firebase.auth().signOut().catch(function(error) {
            // Handle Errors here.
            // [START_EXCLUDE]
            handleError(error);
            // [END_EXCLUDE]
        });
        RESTLogout();
        // [END signout]
    } else {
        var email = document.getElementById('email').value;
        // Sending email with sign-in link.
        // [START authwithemail]
        var actionCodeSettings = {
            // URL you want to redirect back to. The domain (www.example.com) for this URL
            // must be whitelisted in the Firebase Console.
            'url': window.location.href, // Here we redirect back to this same page.
            'handleCodeInApp': true // This must be true.
        };

        firebase.auth().sendSignInLinkToEmail(email, actionCodeSettings).then(function() {
            // Save the email locally so you don’t need to ask the user for it again if they open
            // the link on the same device.
            window.localStorage.setItem('emailForSignIn', email);
            // The link was successfully sent. Inform the user.
            alert('An email was sent to ' + email + '. Please use the link in the email to sign-in.');
            // [START_EXCLUDE]
            // Re-enable the sign-in button.
            document.getElementById('quickstart-sign-in').disabled = false;
            // [END_EXCLUDE]
        }).catch(function(error) {
            // Handle Errors here.
            // [START_EXCLUDE]
            handleError(error);
            // [END_EXCLUDE]
        });
        // [END authwithemail]
    }
}

/**
 * Handles Errors from various Promises..
 */
function handleError(error) {
    // Display Error.
    alert('Error: ' + error.message);
    console.log(error);
    // Re-enable the sign-in button.
    document.getElementById('quickstart-sign-in').disabled = false;
}

/**
 * Handles automatically signing-in the app if we clicked on the sign-in link in the email.
 */
function handleSignIn() {
    // [START handlesignin]
    if (firebase.auth().isSignInWithEmailLink(window.location.href)) {
        // [START_EXCLUDE]
        // Disable the sign-in button during async sign-in tasks.
        document.getElementById('quickstart-sign-in').disabled = true;
        // [END_EXCLUDE]
        // Get the email if available.
        var email = window.localStorage.getItem('emailForSignIn');
        if (!email) {
            // User opened the link on a different device. To prevent session fixation attacks, ask the
            // user to provide the associated email again.
            email = window.prompt('Please provide the email you\'d like to sign-in with for confirmation.');
        }
        if (email) {
            // The client SDK will parse the code from the link for you.
            firebase.auth().signInWithEmailLink(email, window.location.href).then(function(result) {
                // Clear the URL to remove the sign-in link parameters.
                if (history && history.replaceState) {
                    window.history.replaceState({}, document.title, window.location.href.split('?')[0]);
                }
                // Clear email from storage.
                window.localStorage.removeItem('emailForSignIn');
                // Signed-in user's information.
                var user = result.user;

                RESTLogin(user.refreshToken);
                console.log(result)
            }).catch(function(error) {
                // Handle Errors here.
                // [START_EXCLUDE]
                handleError(error);
                // [END_EXCLUDE]
            });
        }
    }
    // [END handlesignin]
}

/**
 * initApp handles setting up UI event listeners and registering Firebase auth listeners:
 *  - firebase.auth().onAuthStateChanged: This listener is called when the user is signed in or
 *    out, and that is where we update the UI.
 */
function initApp() {
    // Restore the previously used value of the email.
    var email = window.localStorage.getItem('emailForSignIn');
    document.getElementById('email').value = email;
    // Automatically signs the user-in using the link.
    handleSignIn();

    // Listening for auth state changes.
    // [START authstatelistener]
    firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
            // User is signed in.
            // Update UI.
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
            // Update UI.
            // [START_EXCLUDE]
            document.getElementById('quickstart-sign-in-status').textContent = '';
            document.getElementById('quickstart-sign-in').textContent = 'Sign in without password';
            document.getElementById('quickstart-account-details').textContent = '';
            let status = 'Signed out';
            console.log(status)
            // [END_EXCLUDE]
        }
        // [START_EXCLUDE silent]
        document.getElementById('quickstart-sign-in').disabled = false;
        // [END_EXCLUDE]
    });
    // [END authstatelistener]
    document.getElementById('quickstart-sign-in').addEventListener('click', toggleSignIn, false);
}

window.onload = initApp;