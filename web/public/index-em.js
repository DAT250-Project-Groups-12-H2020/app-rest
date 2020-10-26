firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
        // User is signed in.

        //document.getElementById("user_div").style.display = "block";
        document.getElementById("login_div").style.display = "none";

        var user = firebase.auth().currentUser;

        if(user != null){

            var email_id = user.email;
            document.getElementById("user_para").innerHTML = "Welcome User : " + email_id;

        }

    } else {
        // No user is signed in.

        //document.getElementById("user_div").style.display = "none";
        document.getElementById("login_div").style.display = "block";

    }
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

function login(){
    location.href = "email-password.html";
}

function signin(){
    location.href = "email-link.html";
}

function guest(){
    location.href = "login/anon-em.html";

}

function index(){
    location.href = "../index-em.html"; // kommer fra poll/pollcode-em.html

}

function google(){
    location.href = "google-redirect.html"

}



function logout(){
    firebase.auth().signOut();
}
