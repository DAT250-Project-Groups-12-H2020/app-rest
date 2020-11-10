function initApp() {
    firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
            // User is signed in.

            //document.getElementById("user_div").style.display = "block";
            //document.getElementById("login_div").style.display = "none";

            //var user = firebase.auth().currentUser;

            //document.getElementById('idtest').textContent = 'Signed';

            if (user != null) {

                //var email_id = user.email;
                //document.getElementById("user_para").innerHTML = "Welcome User : " + email_id;

            }

        } else {
            // No user is signed in.

            //document.getElementById("user_div").style.display = "none";
            //document.getElementById("login_div").style.display = "block";
            //document.getElementById('idtest').textContent = 'not';

        }
        // [START_EXCLUDE]
        //document.getElementById('idtest').disabled = false;

    }); //  funker ikke uten denne tydeligvis
    //document.getElementById('idtest').addEventListener('click', toggleSignIn, false);

}

window.onload = function() {
    initApp();
};

function index() {
    location.href = "../index.html";

}
function more_polls() {
    location.href = "pollsoverview.html";
}

function pollfeedback(){
    location.href = "pollfeedback.html";

}

function more_polls_code() {
    location.href = "pollcode.html"
}

function logout(){
    firebase.auth().signOut();
    /*here lol */
    location.href = "../index.html";
}