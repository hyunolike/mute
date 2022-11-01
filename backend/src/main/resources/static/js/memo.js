$(document).ready(function () {
    let memoInfoData = JSON.parse(localStorage.getItem("memoInfoData"));
    $('<p id="music_title" class="title">' + memoInfoData.music_name + '</p>' + '<p id="music_singer" class="singer">' + memoInfoData.singer + '</p>').appendTo("#song_txt")
    $('<div class="lyric_area">' + memoInfoData.mark_info + '</div> <div class="typing_area">' + memoInfoData.memo + '</div>').appendTo("#memo_info");
})