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


function logout(){
    firebase.auth().signOut();
    /*here lol */
    location.href = "../index.html";
}

function pollvote(){
    location.href = "pollvote.html";
}

var url = "http://localhost:8090";
let id = window.localStorage.getItem('id');
getPoll(id);

function getPoll(id){
    var requestOptions = {
        method: 'GET',
        redirect: 'follow',
        credentials: 'include'
    };

    console.log("id:" + id);
    fetch(url + "/api/v1/polls?id=" + id, requestOptions)
        .then(response => response.text())
        .then(result => {
            console.log(result)
            let poll = JSON.parse(result);
            console.log(poll);

            let question = document.getElementById('poll_question');
            question.innerText = poll.question;

            let pollVotes = poll.votes;
            let indexOfNewestId = findIndexOfNewestId(pollVotes);

            let totalVotesText = document.getElementById('total_votes_textarea');

            console.log(pollVotes);
            if(pollVotes.length != 0){
                totalVotesText.innerText = poll.firstAnswer + ": " + pollVotes[indexOfNewestId].firstVotes;
                totalVotesText.value += '\r\n' + poll.secondAnswer + ": " + pollVotes[indexOfNewestId].secondVotes;
            }else{
                totalVotesText.innerText = poll.firstAnswer + ": " + 0;
                totalVotesText.value += '\r\n' + poll.secondAnswer + ": " + 0;
            }

            let time_remaining = document.getElementById('time_remaining');
            if(poll.endDateTime == null){
                time_remaining.innerText = "Time remaining: Unlimited";
            } else{
                time_remaining.innerText = "Time remaining: " + poll.endDateTime;
            }
        })
        .catch(error => console.log('error', error));
}


function findIndexOfNewestId(pollVotes){
    let indexOfNewestId = 0;
    let newestId = 0;

    for(let i = 0; i < pollVotes.length; i++){
        if(pollVotes[i].id > newestId){
            indexOfNewestId = i;
            newestId = pollVotes[i].id;
        }
    }
    return indexOfNewestId;
}
