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

function poll_overview(){
    location.href = "../poll/pollsoverview.html";
}

function user_overview(){
    location.href = "useroverview.html"
}


function logout(){
    firebase.auth().signOut();
}
