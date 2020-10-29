firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
        // User is signed in.

        //document.getElementById("user_div").style.display = "block";
        document.getElementById("login_div").style.display = "block";
        //document.getElementById("poll_div").style.display = "block";

        var user = firebase.auth().currentUser;

        if(user != null){

            var email_id = user.email;
            document.getElementById("user_para").innerHTML = "Welcome User : " + email_id;

        }

    } else {
        // No user is signed in.

        //document.getElementById("user_div").style.display = "block";
        document.getElementById("login_div").style.display = "block";

    }
    document.getElementById("poll_div").style.display = "block";

});

 /**
function login(){

    var userEmail = document.getElementById("email_field").value;
    var userPass = document.getElementById("password_field").value;

    firebase.auth().signInWithEmailAndPassword(userEmail, userPass).catch(function(error) {
        // Handle Errors here.
        var errorCode = error.code;
        var errorMessage = error.message;

        window.alert("Error : " + errorMessage);

        // ...
    });


}
 **/

function email_password(){
    location.href = "login/email-password.html";
}

function email_link(){
    location.href = "login/email-link.html";
}

function guest(){
    location.href = "login/anon.html";

}

function google() {
    location.href = "login/google-redirect.html"
}

function pollvote(){
    location.href = "pollvote.html";
}

function pollcreate(){
    location.href = "pollcreate.html"

}

function pollfeedback(){
    location.href = "pollfeedback.html"

}

function userinfo(){
    location.href = ""

}

function polloverview(){
    location.href = "../poll/polloverview.html"

}
function admin_overview() {
    location.href = "adminoverview.html"
}

function logout(){
    firebase.auth().signOut();
}
