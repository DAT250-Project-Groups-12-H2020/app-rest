function RESTLogin(refreshToken, redirect = "../login/loggedinoverview.html") {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/x-www-form-urlencoded");

    var urlencoded = new URLSearchParams();
    urlencoded.append("key", "AIzaSyBVaiwbASOUfS4GoXaPYS62LgMaxsbSZG0");
    urlencoded.append("grant_type", "refresh_token");
    urlencoded.append("refresh_token", refreshToken);

    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: urlencoded,
        redirect: 'follow'
    };

    fetch("https://securetoken.googleapis.com/v1/token", requestOptions)
        .then(response => response.text())
        .then(result => {
            var myHeaders = new Headers();
            var json = JSON.parse(result);
            myHeaders.append("Authorization",  json.token_type + " " + json.access_token);

            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                redirect: 'follow',
                credentials: 'include'
            };

            fetch("http://localhost:8090/api/v1/session/login", requestOptions)
                .then(response => response.text())
                .then(result => {
                    location.href = redirect;
                })
                .catch(error => console.log('error', error));
        })
        .catch(error => console.log('error', error));

}


function RESTLogout(){
    var requestOptions = {
        method: 'POST',
        redirect: 'follow',
        credentials: 'include'
    };

    fetch("http://localhost:8090/api/v1/session/logout", requestOptions)
        .then(response => response.text())
        .then(result => location.href = "../index.html")
        .catch(error => console.log('error', error));

}