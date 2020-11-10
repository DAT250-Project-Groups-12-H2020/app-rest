firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
        // User is signed in.

        //document.getElementById("user_div").style.display = "block";
        //document.getElementById("login_div").style.display = "block";
        //document.getElementById("poll_div").style.display = "block";

        var user = firebase.auth().currentUser;

        if(user != null){

            //var email_id = user.email;
            //document.getElementById("user_para").innerHTML = "Welcome User : " + email_id;

        }

    } else {
        // No user is signed in.

        //document.getElementById("user_div").style.display = "block";
        //document.getElementById("login_div").style.display = "block";

    }
    //document.getElementById("poll_div").style.display = "block";

});

var url = "http://localhost:8090";

function goToPoll(){
    let id = document.getElementById('poll_field').value;

    // Bruker id som pollcode
    var requestOptions = {
        method: 'GET',
        redirect: 'follow',
        credentials: 'include'
    };
    console.log(id);

    fetch(url + "/api/v1/polls?id=" + id, requestOptions)
        .then(response => response.text())
        .then(result => {

            result = JSON.parse(result);
            console.log("status:");
            console.log(result.status);

            if(result.status == 404){
                // can't access poll with the id given
                alert("You can't access this poll");
            }else{
                // go to poll
                window.localStorage.setItem('id', result.id);
                location.href = "pollvote.html";
            }


        })
        .catch(error => console.log('error', error));
}

function logout(){
    firebase.auth().signOut();
    /*here lol */
    location.href = "../index.html";
}