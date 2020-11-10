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

var url = "http://localhost:8090";
let vote = "first";

let question = document.getElementById('poll_question');
let time_remaining = document.getElementById('time_remaining');

// id of poll the user is voting on
let id = localStorage.getItem('id');
getPollAndShowIt(id);

function getPollAndShowIt(id) {
    var requestOptions = {
        method: 'GET',
        redirect: 'follow',
        credentials: 'include'
    };

    fetch(url + "/api/v1/polls?id=" + id, requestOptions)
        .then(response => response.text())
        .then(result => {
            let poll = JSON.parse(result);
            console.log(poll);

            question.innerText = poll.question;

            document.getElementById('first_answer').innerText = poll.firstAnswer;
            document.getElementById('second_answer').innerText = poll.secondAnswer;

            if(poll.startDateTime == null && poll.endDateTime == null){
                time_remaining.innerText = "Always open";
                time_remaining.style.color = 'green';
            }else{
                // Todo: show time remaining, not just end date
                if(poll.endDateTime == null){
                    time_remaining.innerText = "Unlimited";
                }else{
                    time_remaining.innerText = poll.endDateTime;
                }
            }

            // Radio buttons
            let first_radio = document.getElementById('radio_first');
            let second_radio = document.getElementById('radio_second');

            first_radio.addEventListener('click', () => {
                vote = "first";
            });
            second_radio.addEventListener('click', () => {
                vote = "second";
            });
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

function sendVote(id) {
    let firstVotes = 0;
    let secondVotes = 0;

    // Get votes from poll
    var requestOptions = {
        method: 'GET',
        redirect: 'follow',
        credentials: 'include'
    };

    fetch(url + "/api/v1/polls?id=" + id, requestOptions)
        .then(response => response.text())
        .then(result => {
            let poll = JSON.parse(result);
            let pollVotes = poll.votes;

            let indexOfNewestId = findIndexOfNewestId(pollVotes);

            if (pollVotes.length != 0) {
                // Poll has been voted on before
                firstVotes = pollVotes[indexOfNewestId].firstVotes;
                secondVotes = pollVotes[indexOfNewestId].secondVotes;
            }
            sendPollUpdate(firstVotes, secondVotes);
        })
        .catch(error => console.log('error', error));
}


function sendPollUpdate(firstVotes, secondVotes){
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    console.log("Before:");
    console.log(firstVotes);
    console.log(secondVotes);

    if(vote == "first"){
        firstVotes = firstVotes + 1;
    }else if(vote == "second"){
        secondVotes = secondVotes + 1;
    }

    console.log("After:");
    console.log(firstVotes);
    console.log(secondVotes);

    var raw = JSON.stringify({"firstVotes":firstVotes,"secondVotes":secondVotes});

    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow',
        credentials: 'include'
    };

    fetch(url + "/api/v1/polls/" + id + "/vote", requestOptions)
        .then(response => response.text())
        .then(result => {
            console.log(result)
            location.href = "pollvotedone.html";
        })
        .catch(error => console.log('error', error));
}