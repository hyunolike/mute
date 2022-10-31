$(document).ready(function (){
    document.getElementById("clickBtn").click();

    let musicInfo = JSON.parse(localStorage.getItem("musicInfoData"));
    let title = musicInfo.music_name;
    let singer = musicInfo.artist_name;
    let albumUrl = musicInfo.album_url;
    let albumName = musicInfo.album_name;

    $('<p id="music_title" class="title">' + title + '</p>' + '<p id="music_singer" class="singer">' + singer + '</p>').appendTo("#song_txt")
    $(`<img id="album_cover_img" src='${albumUrl}'></img>`).appendTo("#album_cover");

    // 곡 정보
    $(`<li><p>아티스트</p><p>${singer}</p></li>`).appendTo('#m_info');
    $(`<li><p>앨범</p><p>${albumName}</p></li>`).appendTo('#m_info');
    $(`<li><p>장르</p><p>${musicInfo.genre}</p></li>`).appendTo('#m_info');
    $(`<li><p>발매일</p><p>${musicInfo.release_date}</p></li>`).appendTo('#m_info');

    // 가사 정보
    $(`<p id="drag_lyrics_data">${musicInfo.lyrics_data}</p>`).appendTo("#가사");
});

function openCity(evt, cityName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}

/**
 * 마우스 드레그 시, 데이터 뽑는 기능
 */
function selectText() {
    var selectionText = "";
    if (document.getSelection) {
        selectionText = document.getSelection();
    } else if (document.selection) {
        selectionText = document.selection.createRange().text;
    }
    console.log(selectionText.toString());
    return selectionText.toString();
}

function saveLocalStorage(lyrics_data){
    var menoMusicData = {
        "lyrics_data": lyrics_data
    }

    localStorage.setItem("menoMusicData", JSON.stringify(menoMusicData));
}

/**
 * 로직 - 마우스 드레그 시
 * (중요) 해당 영역에서만 마우스 이벤트 동작하게끔 구성 - 가사 영역 부분에서만 동작하게 구성
 * 1. 드레그 텍스트 뽑아 내기
 * 2. 해당 텍스트 localStorage 저장
 * 3. 메모 작성 페이지로 이동
 */
const drag_lyrics_data_selection = document.getElementById('가사');

drag_lyrics_data_selection.addEventListener('mouseup', () => {
    console.log("click")
    let lyrics_data = selectText()
    saveLocalStorage(lyrics_data);
    location.replace(`http://${PATH.PUBLIC_IP}/music-mark`);
});

