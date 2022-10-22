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
    $(`<p>${musicInfo.lyrics_data}</p>`).appendTo("#가사");
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

// function selectText() {
//     console.log("mouse move");
//
//     var selectionText = "";
//     if (document.getSelection) {
//         selectionText = document.getSelection();
//
//     } else if (document.selection) {
//         selectionText = document.selection.createRange().text;
//         var html = "<div style='background-color: red; padding: 10px'>" + selectionText + "</div>";
//         document.selection.createRange().pasteHTML(html);
//     }
//
//     return selectionText;
// }
//
// document.onmouseup = function() {
//     document.getElementById("console").innerHTML = selectText();
// }

