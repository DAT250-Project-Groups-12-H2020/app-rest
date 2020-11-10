function logout(){
    firebase.auth().signOut();
    location.href = "../index.html";
}
