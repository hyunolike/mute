/**
 * 메모 저장 로직
 * 1. localStorage 메모 저장 (이전 페이지)
 * 2. api 사용해 저장된 메모 데이터 불러오기
 * 2-1. 폴더 정보만 따로 추출해서 div element 생성 - 폴더 정보만 추출 가능한 api 추가 개발?
 * 2-2. 해당 폴더 영역 선택
 *
 * 3. 해당 폴더 데이터와 함께 api를 통해 저장 진행
 * 4. 완료 후, 메인 페이지로 자동 이동
 */
$(document).ready(function (){
    axios.get(`http://${PATH.PUBLIC_IP}/api/musicmark/folder`)
        .then(function (res) {

            console.log(res.data.folder_list);

            /**
             * 해당 api 결과 리스트 값
             * 1. 자바스크립트 이용 카운트, 그룹핑
             * 2. 해당 결과값 반환
             */
            // let result = [];
            // res.data.folder_list.forEach((x) => {
            //     result[x] = (result[x] || 0) + 1;
            //     });
            //
            // console.log(result);

            let folderData = [...new Set(res.data.folder_list.map(JSON.stringify))].map(JSON.parse);
            console.log(folderData[0]);
            let totalData = folderData.length;

            paging(folderData, totalData);
        })
});

/**
 * 저장 로직
 * 1. 해당 폴더 영역 클릭 후,
 * 1-1. 해당 css 변경 및 폴더 명과 함께 데이터 전송 준비
 * 2. 다음 링크 클릭 시, api통해 메모 저장되는 방식
 */
function paging(folderData, totalData){
    for(let i = 0; i < totalData; i++){
        $('<div id = "item' + i + '" class="item" onclick="saveMemo(\'' + 'item' + i + '\', \'' + folderData[i].folder_name + '\', \'' + folderData[i].folder_desc + '\')" ' + '"/>' + '<div class="item-album">' + '<img src="'+ 'https://via.placeholder.com/150' + '"/>' +'</div>' +
            '<div class="item-content">' + '<div class="item-title">' + folderData[i].folder_name + '</div>' +
            '<div class="item-singer">' + folderData[i].folder_desc + '</div>'+'</div>'
            + '</div>' + '<img src="../img/search-bar.svg"/>').appendTo('#folder-section');
    }
}

/**
 * 예외처리 필요
 */
function saveMemo(item, folder_name, folder_desc){
    document.getElementById(item).style.backgroundColor = "#151515";

    let memoMusicData = JSON.parse(localStorage.getItem("musicInfoData"));
    let lyricsData = JSON.parse(localStorage.getItem("menoMusicData"));
    let memo = JSON.parse(localStorage.getItem("menoData"));

    axios.post(`http://${PATH.PUBLIC_IP}/api/musicmark`, {
        "album_url": memoMusicData.album_url,
        "mark_info": lyricsData.lyrics_data,
        "memo": memo.meno_data,
        "music_name": memoMusicData.music_name,
        "singer": memoMusicData.artist_name,
        "track_id": null
    }).catch(function (err) {
        console.log(err);
    })
}


