/**
 * 동작 순서
 * (전제) 해당 로직은 search-result.html 에서 이뤄져야됨!
 * (추가) 인풋 태그 마우스 클릭하게 되면 페이지 이동되게끔 구상!
 * 1. 검색어 입력 후, 버튼 클릭
 * 2. 입력값의 값 가져와서 div 생성
 * 3. axios 이용해서 데이터 전체 리스트 가져오기
 * 4. 페이지닝?? 어떻게?? - 자바스크립트 페이지 처리
 */

const currentURL = window.location.href;
const searchViewURL = "http://localhost:9061/search";

if(currentURL !== searchViewURL){
    const inputTag = document.getElementById('search-name');
    inputTag.addEventListener('mousedown', (event) => {
        location.replace("http://localhost:9061/search");
    });
}

function getSearchApi() {
    const getSearchName = document.getElementById("search-name").value;
    const fixBringSearchName = document.getElementById('search-value');

    fixBringSearchName.innerHTML = `<border> ${getSearchName} </border> 이(가) 포함 된 노래`;

    const searchHistoryList = document.getElementById('search-history-list');
    const searchResultList = document.getElementById('search-result-list');

    searchHistoryList.style.display = "none";
    searchResultList.style.display = "block";


    // bringData();
    //
    // function bringData(){
    //     axios.get('http://localhost:9061/api/', {
    //         params: {
    //             "article-name": getSearchName
    //         }
    //     }).then(function (res) {
    //         $('<div class="item">' + '<h2>' + res.data.musics[count].music_name + '</h2>' + '<img src="' + res.data.musics[count].album_url + '"/>' + '</div>').appendTo('#memo-item');
    //         observerLastEle();
    //     })
    // }
}

