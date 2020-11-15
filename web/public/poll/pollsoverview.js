function ownPolls(){
    location.href = "ownpollsoverview.html";
}

function pollCreate(){
    location.href = "pollcreate.html"
}

var url = "http://localhost:8090";

window.onload = (event) => {
    getMe();
    getAllPublicPolls();
};

function getMe(){
    var requestOptions = {
        method: 'GET',
        redirect: 'follow',
        credentials: 'include'
    };

    fetch(url + "/api/v1/accounts/me", requestOptions)
        .then(response => response.text())
        .then(result => {
            console.log(result)
            let user = JSON.parse(result);
            if(user.email == null){
                document.getElementById('view_own_polls_btn').hidden = true;
                document.getElementById('create_poll_btn').hidden = true;
            }else{
                document.getElementById('view_own_polls_btn').hidden = false;
                document.getElementById('create_poll_btn').hidden = false;
            }
        })
        .catch(error => console.log('error', error));
}

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
        })
        .catch(error => console.log('error', error));
    }

