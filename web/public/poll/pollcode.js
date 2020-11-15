var url = "http://localhost:8090";

function goToPoll(){
    let id = document.getElementById('poll_field').value;

    // Id is used as poll code
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