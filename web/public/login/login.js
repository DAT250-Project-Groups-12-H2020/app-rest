function RESTLogin(refreshToken) {
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
            //console.log(result);
            var myHeaders = new Headers();
            var json = JSON.parse(result);
            //console.log("token type: " + json.token_type);
            //console.log("acctok: " + json.access_token);
            myHeaders.append("Authorization",  json.token_type + " " + json.access_token);
            //myHeaders.append('content-type', "API-Key");

            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                redirect: 'follow',
                credentials: 'include'
            };

            fetch("http://localhost:8090/api/v1/session/login", requestOptions)
                .then(response => response.text())
                .then(result => console.log(result))
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
        .then(result => console.log(result))
        .catch(error => console.log('error', error));

}