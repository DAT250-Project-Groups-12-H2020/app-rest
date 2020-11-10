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
    var requestOptions = {
        method: 'POST',
        redirect: 'follow',
        credentials: 'include'
    };

    fetch(url + "/api/v1/session/logout", requestOptions)
        .then(response => response.text())
        .then(result => {
            console.log(result)
            location.href = "../index.html";
        })
        .catch(error => console.log('error', error));

}