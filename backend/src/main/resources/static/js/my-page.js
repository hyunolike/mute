function signOut(){
    axios.get('http://localhost:9061/logout')
        .then(function (){
            location.replace("http://localhost:9061/login");
        })
}