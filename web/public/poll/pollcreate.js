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

function logout(){
    firebase.auth().signOut();
    /*here lol */
    location.href = "../index.html";
}

let url = 'http://localhost:8090';

async function createPoll() {
    let pollQuestion = document.getElementById("poll_question_field");
    let firstAnswer = document.getElementById("first_ans_field");
    let secondAnswer = document.getElementById("second_ans_field");
    let private = document.getElementById("private");
    let startDate = document.getElementById("start_date");
    let endDate = document.getElementById("end_date");
    let now = new Date(Date.now());

    let startDateChosen = new Date(startDate.value);
    let endDateChosen = new Date(endDate.value);

    let start_time = document.getElementById('start_time');
    let end_time = document.getElementById('end_time');

    let pollBody = {};

    let isDateChosen = false;
    if(endDate.value === "" || startDate.value === "") {
        // Date is not chosen

        pollBody = {
            "question": pollQuestion.value,
            "firstAnswer": firstAnswer.value,
            "secondAnswer": secondAnswer.value,
            "private": private.checked,
            "startDateTime":now.toISOString()

        }
        console.log("NOT CHOSEN" + JSON.stringify(pollBody));
    }else{
        // Date is chosen
        // Todo: It is currently possible to choose a time interval that is up to 1 hour behind the current time (when choosing the current day as start date) - needs fix

        let startTimeArr = start_time.value.toString().split(':');
        let startHour = startTimeArr[0];
        let startMinutes = startTimeArr[1];

        let endTimeArr = end_time.value.toString().split(':');
        let endHour = endTimeArr[0];
        let endMinutes = endTimeArr[1];

        if(startDateChosen.getFullYear() == now.getFullYear() &&
            startDateChosen.getDate() == now.getDate() &&
            startDateChosen.getDay() == now.getDay() &&
            startHour == now.getHours() &&
            startMinutes == now.getMinutes()
        ){
            // Same day as now. This is to set start date correctly (bug with seconds)
            startDateChosen = now;
        }else{
            startDateChosen.setUTCHours(parseInt(startHour));
            startDateChosen.setUTCMinutes(parseInt(startMinutes));
            startDateChosen.setUTCSeconds(now.getSeconds());
        }

        endDateChosen.setUTCHours(parseInt(endHour));
        endDateChosen.setUTCMinutes(parseInt(endMinutes));

        if(!(validateTimeInput(startDateChosen, endDateChosen))){
            alert("Time input is not valid");
            console.log("Returning");
            return;
        }else{
            isDateChosen = true;
        }

        pollBody = {
            "question":pollQuestion.value,
            "firstAnswer":firstAnswer.value,
            "secondAnswer":secondAnswer.value,
            "startDateTime":startDateChosen.toISOString(),
            "endDateTime":endDateChosen.toISOString(),
            "private":private.checked
        }
    }

    function checkIfAllInputsAreValid(){
        if(pollQuestion.value === ""
            || firstAnswer.value === ""
            || secondAnswer.value === ""
        ) {
            alert("Please make sure to choose a question and answers");
            return false;
        }else if(isDateChosen && (start_time.value === "" || end_time.value === "")){
            alert("Please choose time intervals");
            return false;
        }
        if(!isDateChosen){
            return true;
        }
        return true;
    }

    if(checkIfAllInputsAreValid()){
        pollPOST();
    }

    function pollPOST(){
        console.log(JSON.stringify(pollBody));
        var requestOptions = {
            method: 'POST',
            redirect: 'follow',
            credentials: 'include',
            body: JSON.stringify(pollBody),
            headers: {
                'Content-Type': 'application/json'
            }
        };

        fetch(url + '/api/v1/polls/create/', requestOptions)
            .then(response => response.text())
            .then(result => {
                console.log(result)
                if(checkIfAllInputsAreValid()){
                    //location.href = "privatepollsoverview.html";
                }

            })
            .catch(error => console.log('error', error));
    }
}

function validateTimeInput(startDateChosen, endDateChosen){
    let startHour = startDateChosen.getHours();
    let startMinutes = startDateChosen.getMinutes();

    let endHour = endDateChosen.getHours();
    let endMinutes = endDateChosen.getMinutes();
    let now = new Date(Date.now());

    if(!(startDateChosen >= now)){
        console.log(startDateChosen.toISOString() + " not >= " + now.toISOString());
        return false;
    }
    if(startDateChosen == endDateChosen){
        if(startHour > endHour){
            console.log("No, since " + startHour + " is greater than " + endHour);
            return false;
        }else if(endHour == startHour && startMinutes > endMinutes){
            console.log("No, since " + startMinutes + " is greater than " + endMinutes);
            return false;
        }
    }
    if(endDateChosen < startDateChosen){
        console.log("No, since start date can't be after end date")
        return false;
    }
    return true;
}