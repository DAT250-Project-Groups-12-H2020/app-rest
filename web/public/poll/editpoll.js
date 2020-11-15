let url = 'http://localhost:8090';
let id = window.localStorage.getItem('id');

let pollQuestion = document.getElementById("poll_question_field");
let firstAnswer = document.getElementById("first_ans_field");
let secondAnswer = document.getElementById("second_ans_field");
let private = document.getElementById("private");

var requestOptions = {
    method: 'GET',
    redirect: 'follow',
    credentials: 'include'
};

fetch(url + "/api/v1/polls?id=" + id, requestOptions)
    .then(response => response.text())
    .then(result => {
        poll = JSON.parse(result);

        pollQuestion.value = poll.question;
        firstAnswer.value = poll.firstAnswer;
        secondAnswer.value = poll.secondAnswer;
        private.checked = poll.private;

        // poll active period
        let poll_period = document.getElementById('poll_period_date');
        if(poll.startDateTime == null && poll.endDateTime == null){
            poll_period.innerText = "Always open";
            poll_period.style.color = 'green';
        }else{
            if(poll.endDateTime == null){
                let time = new Date(poll.startDateTime);
                poll_period.innerText = time.toUTCString() + ' - Never Ends';
            }else{
                let time_s = new Date(poll.startDateTime);
                let time_e = new Date(poll.endDateTime);
                poll_period.innerText = time_s.toUTCString() + ' - ' + time_e.toUTCString();
            }
        }

    })
    .catch(error => console.log('error', error));


function editPoll() {
    let pollBody = {
        "question": pollQuestion.value,
        "firstAnswer": firstAnswer.value,
        "secondAnswer": secondAnswer.value,
        "private": private.checked,
    }

    if (pollQuestion.value === ""
        || firstAnswer.value === ""
        || secondAnswer.value === ""
    ) {
        alert("Please make sure to choose a question and answers");
    } else {
        pollPUT(pollBody);
    }
}

function pollPUT(pollBody) {
    var requestOptions = {
        method: 'PUT',
        body: JSON.stringify(pollBody),
        redirect: 'follow',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        }
    };

    fetch(url + "/api/v1/polls/" + id, requestOptions)
        .then(response => response.text())
        .then(result => {
            console.log(result)
            location.href = "../poll/ownpollsoverview.html";
        })
        .catch(error => console.log('error', error));
}

function deletePoll(){
    var requestOptions = {
    method: 'DELETE',
    redirect: 'follow',
    credentials: 'include'
    };

    fetch(url + "/api/v1/polls/" + id, requestOptions)
    .then(response => response.text())
    .then(result => {
    console.log(result)
    location.href = "../poll/pollsoverview.html";
    })
    .catch(error => console.log('error', error));
}
