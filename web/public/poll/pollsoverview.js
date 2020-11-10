
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



function ownPolls(){
    location.href = "ownpollsoverview.html";
}

function pollCreate(){
    location.href = "pollcreate.html"

}


function logout(){
    firebase.auth().signOut();
    location.href = "../index.html";
}

/* */

var url = "http://localhost:8090";

function getAllPublicPolls() {
    var requestOptions = {
        method: 'GET',
        redirect: 'follow',
        credentials: 'include'
    };

    let page = 0;

    fetch(url + "/api/v1/polls/public?size=10&page=" + page + "&sort=id,desc", requestOptions)
    .then(response => response.text())
    .then(result => {
        console.log(result)
        result = JSON.parse(result);

        polls = result.content;
        console.log(polls);

        let table = document.getElementById('table');

        for(let p of polls){
            // Insert poll to table
            table.insertRow();
            let newCell = table.rows[table.rows.length - 1].insertCell();
            newCell.textContent = p.question;
            newCell.addEventListener('click', () => {
            window.localStorage.setItem('id', p.id);
            location.href = "pollvote.html";
            })

            let pollCodeColumn = table.rows[table.rows.length - 1].insertCell();
            pollCodeColumn.textContent = p.id;

            if(isOpen(result)){
                newCell.style.color = "green";
            }else{
                newCell.style.color = "red";
            }
        }

        // Todo: Create 'next' and 'back' buttons so that more polls can be shown
        if(!result.empty){
        // Show 'next' button
        console.log("Show 'next' button");
        }
        if(result.pageable.pageNumber > 0){
            // Show 'back' button
            console.log("Show 'back' button");
            }
        })
        .catch(error => console.log('error', error));
    }

    window.onload = (event) => {
    getAllPublicPolls();
};

