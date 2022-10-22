/**
 * 공통변수 파일을 따로 만들어 배포 시, 쉽게 변경 가능하도록 구성
 */
function signOut(){
    axios.get("http://localhost:9061/logout")
        .then(function (){
            location.replace("http://localhost:9061/login");
        })
}