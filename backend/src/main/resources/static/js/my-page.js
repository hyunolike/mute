/**
 * 공통변수 파일을 따로 만들어 배포 시, 쉽게 변경 가능하도록 구성
 */
function signOut(){
    axios.get("http://" + PATH.PUBLIC_IP + PATH.SIGN_OUT)
        .then(function (){
            location.replace(`http://${PATH.PUBLIC_IP}/login`);
        })
}