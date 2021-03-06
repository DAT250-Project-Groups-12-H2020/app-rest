let url = "http://localhost:8090";
let nameInput = document.getElementById('name');
let emailInput = document.getElementById('email');
let profile = null;
getMe();

function getMe(){
    var requestOptions = {
        method: 'GET',
        redirect: 'follow',
        credentials: 'include'
    };

    fetch(url + "/api/v1/accounts/me", requestOptions)
        .then(response => response.text())
        .then(result => {
            profile = result;
            console.log(result);
            let user = JSON.parse(result);
            console.log(user);

            // Display name & email that was fetched
            if(user.name != null){
                nameInput.value = user.name;
                console.log(user.name);
            }
            if(user.email != null){
                emailInput.value = user.email;
                console.log(user.email);
            }
        })
        .catch(error => console.log('error', error));
}


function editProfile(){
    console.log(profile);
    let photoUrl = profile.photoUrl;
    let role = profile.role;
    let disabled = profile.disabled;

    var raw = JSON.stringify({"name":nameInput.value, "email":emailInput.value, "photoUrl":photoUrl, disabled:disabled, role:role});

    if(emailInput.value != profile.name){
        // Email has been changed
        // Todo: Send verification email
    }
    var requestOptions = {
        method: 'PUT',
        body: raw,
        headers: {
            'Content-Type': 'application/json'
        },
        redirect: 'follow',
        credentials: 'include'
    };

    fetch(url + "/api/v1/accounts/me", requestOptions)
        .then(response => response.text())
        .then(result => {
            console.log(result)
            //document.getElementById('message').innerText = "Account edited!";
        })
        .catch(error => console.log('error', error));
}

function deleteMyProfile(){
    if(confirm('Are you sure?')){
        var requestOptions = {
            method: 'DELETE',
            redirect: 'follow',
            credentials: 'include'
        };

        fetch(url + "/api/v1/accounts/me", requestOptions)
            .then(response => response.text())
            .then(result => {
                console.log(result)
                location.href = "../index.html";
            })
            .catch(error => console.log('error', error));
    }
}