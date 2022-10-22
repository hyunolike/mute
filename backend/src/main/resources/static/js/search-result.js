/**
 * <h2>동작 순서</h2>
 * (전제) 해당 로직은 search-result.html 에서 이뤄져야됨!
 * (추가) 인풋 태그 마우스 클릭하게 되면 페이지 이동되게끔 구상!
 * 1. 검색어 입력 후, 버튼 클릭
 * 2. 입력값의 값 가져와서 div 생성
 * 3. axios 이용해서 데이터 전체 리스트 가져오기
 * 4. 페이지 처리
 *  4-1. readyPaging() 함수를 통해 api 호출 및 글 목록 표시 함수, 페이징 표시 함수 호출한다.
 *      4-1-1. 페이징 표시 함수에서 클릭 이벤트를 통해 글 목록 표시 함수, 페이징 표시 함수를 반복적으로 호출
 *      
 */

const currentURL = window.location.href;
const searchViewURL = "http://localhost:9061/search";

if(currentURL !== searchViewURL){
    const inputTag = document.getElementById('search-name');
    inputTag.addEventListener('mousedown', (event) => {
        location.replace("http://localhost:9061/search");
    });
}

/**
 * 로직 (서로다른 html 파일간 값 전달)
 * 1. 데이터 결과값을 객체 형태로 저장 (+ 추가적으로 엘범 url 데이터 삽입)
 * 2. localstorage.setItem()
 * 3. 다른 html 에서 해당 값을 이용해 데이터 뿌려줌
 * 4. html 이동!
 */
function sendMusicInfoAPI(res, albumUrl){
    axios.get(`http://localhost:9061/api/music-info/${res}`)
        .then((res) => {
        console.log(res.data);

        var musicInfoData = {
            "artist_name": res.data.artist_name,
            "album_name": res.data.album_name,
            "release_date": res.data.release_date,
            "genre": res.data.genre,
            "composition": res.data.composition,
            "lyricist": res.data.lyricist,
            "arranger": res.data.arranger,
            "lyrics_data": res.data.lyrics_data,
            "album_url": albumUrl
        }

        localStorage.setItem("musicInfoData", JSON.stringify(musicInfoData));

        location.replace("http://localhost:9061/music-info");
    })
}

function getSearchApi() {
    const getSearchName = document.getElementById("search-name").value;
    const fixBringSearchName = document.getElementById('search-value');

    fixBringSearchName.innerHTML = `'${getSearchName}' 이(가) 포함 된 노래`;

    const searchHistoryList = document.getElementById('search-history-list');
    const searchResultList = document.getElementById('search-result-list');
    const searchResult = document.getElementById('search-value');

    searchHistoryList.style.display = "none";
    searchResultList.style.display = "block";
    searchResult.style.display="flex";


    var totalData;
    var pageCount = 2;
    var globalCurrentPage = 1; //현재 페이지 번호
    var dataPerPage = 4; // 한 페이지에 나타낼 글 수
    var totalPage;
    var selectedPage;

    readyPaging();

    // 페이징 처리 준비 단계
    function readyPaging(){
        axios.get('http://localhost:9061/api/search', {
            params: {
                "article-name": getSearchName
            }
        }).then(function (res) {
            totalData = res.data.musics.length; // 반환되는 데이터 길이 100개(고정)
            console.log(`totalData: ${totalData}`);

            // 글 목록 표시 호출(테이블 생성)
            displayData(1, dataPerPage, res);
            // 페이징 표시 호출
            paging(totalData, dataPerPage, pageCount, 1, res);
        })
    }

    // 페이지 표시 함수
    function paging(totalData, dataPerPage, pageCount, currentPage, res){
        totalPage = Math.ceil(totalData / dataPerPage); //총 페이지 수 Ex. 100 / 4 = 25 ... 총 25 페이지 생성

        let pageGruop = Math.ceil(currentPage / pageCount); //페이지 그룹
        let last = totalPage; //화면에 보여질 마지막 페이지 번호

        let next = currentPage + dataPerPage; // 4개씩 건너띄어야됨, 왜냐면 한번에 4개씩 보여줄꺼니까
        let prev = currentPage - dataPerPage;

        let pageHtml = "";

        pageHtml += "<button class='next'><a href='#' id='prev'><img src='../img/navigate-before.svg' alt= /></a></button>";
        pageHtml +=  "<div class='on'>" + globalCurrentPage + "</a></div>";
        pageHtml += "<div>"+ "/" + last + "</div>";
        pageHtml += "<button class='next'><a href='#' id='next'><img src='../img/navigate-next.svg' alt= /></a></button>";

        $("#paging").html(pageHtml);

        let displayCount = "";
        displayCount = "현재 1 - " + totalPage + " 페이지 / " + totalData + "건";
        $("#displayCount").text(displayCount);

        // 클릭 이벤트
        /**
         * <h2>이전, 다음 클릭 시 로직 순서</h2>
         * 1. 기존 자식 요소들 전체 삭제 (div 요소 쌓이지 않게끔)
         * 2. 해당 요소 아이디에 맞는 selectedPage 값 가져오기
         * 3. paging()-페이지 표시 함수, displayData()-글 목록 표시 함수 호출
         */
        $("#paging button a").click(function (){
            var removeItemDiv = document.getElementById("view-list");

            // 자식 요소 전체 삭제
            while(removeItemDiv.hasChildNodes()){
                removeItemDiv.removeChild(removeItemDiv.firstChild);
            }

            let $id = $(this).attr("id");
            selectedPage = $(this).text();

            if ($id == "next") selectedPage = next; // 만약, 현재 페이지 1이였다면 클릭하게 되면 + 4니까 5부터 시작
            if ($id == "prev") selectedPage = prev;

            // 전역변수에 선택한 페이지 번호 담는다.
            globalCurrentPage = selectedPage;
            // 페이징 표시 재호출
            paging(totalData, dataPerPage, pageCount, selectedPage, res);
            // 글 목록 표시 재호출
            displayData(selectedPage, dataPerPage, res);
        })
    }

    // 글 목록 표시 함수
    // 현재 페이지(currentPage)와 페이지당 글 개수(dataPerPage) 반영
    function displayData(currentPage, dataPerPage, res){

        currentPage = Number(currentPage);
        dataPerPage = Number(dataPerPage);

        for(
            var i = (currentPage - 1) * dataPerPage;
            i < (currentPage - 1) * dataPerPage + dataPerPage;
            i++
        ){
            $('<div class="item" onclick="sendMusicInfoAPI(\'' + res.data.musics[i].singer + ' ' + res.data.musics[i].music_name + '\', \'' + res.data.musics[i].album_url + '\')">' + '<div class="item-album">' + '<img src="' + res.data.musics[i].album_url + '"/>' +'</div>' +
                '<div class="item-content">' + '<div class="item-title">' + res.data.musics[i].music_name + '</div>' +
                '<div class="item-singer">' + res.data.musics[i].singer + '</div>'+'</div>'
                + '</div>' + '<img src="../img/search-bar.svg"/>').appendTo('#view-list');
        }
    }
}

