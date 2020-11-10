

function publicPolls(){
    location.href = "pollsoverview.html";
}

function pollCreate(){
    location.href = "pollcreate.html"

}


function logout(){
    firebase.auth().signOut();
    /*here lol */
    location.href = "../index.html";
}

/* */
var url = "http://localhost:8090";

window.onload = (event) => {
    getOwnPolls();
};

function getOwnPolls(){
    var requestOptions = {
        method: 'GET',
        redirect: 'follow',
        credentials: 'include'
    };

    fetch(url + "/api/v1/accounts/me", requestOptions)
        .then(response => response.text())
        .then(result => {
            polls = JSON.parse(result).polls;

            if (polls != null) {
                // Find all polls that belong to the current user
                for(let i=0; i < polls.length; i++) {
                    getPoll(polls[i].id);
                }
            }
        })
        .catch(error => console.log('error', error));
}

function getPoll(id){
    let table = document.getElementById('table');

    var requestOptions = {
        method: 'GET',
        redirect: 'follow',
        credentials: 'include'
    };

    fetch(url + "/api/v1/polls?id=" + id, requestOptions)
        .then(response => response.text())
        .then(result => {
            result = JSON.parse(result);

            // Make tables - should ideally be done in its own method
            table.insertRow();

            // Go to poll by clicking on the poll question
            let newCell = table.rows[table.rows.length - 1].insertCell();
            newCell.addEventListener('click', () => {
                if(isOpen(result)){
                    // Store the id in localStorage so that it can be accessed from another page
                    window.localStorage.setItem('id', result.id);
                    //console.log(localStorage.getItem('id'));
                    location.href = "pollvote.html";
                }
            });

            // Get question from poll
            let question = result.question;

            // Set row to display question
            newCell.textContent = question;
            if(result.private){
                newCell.textContent += " [Private]";
            }else{
                newCell.textContent += " [Public]";
            }

            if(isOpen(result)){
                newCell.style.color = "green";
            }else{
                newCell.style.color = "red";
            }


            // New column/cell to the right
            let nC = table.rows[table.rows.length - 1].insertCell();
            nC.textContent = "Edit";
            nC.addEventListener('click', () => {
                window.localStorage.setItem('id', result.id);
                console.log("Edit");
                location.href = "../poll/editpoll.html";
            })
        })
        .catch(error => console.log('error', error));

    document.body.appendChild(table);
}

