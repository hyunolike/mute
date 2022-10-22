$(document).ready(function (){
    let menoMusicData = JSON.parse(localStorage.getItem("menoMusicData"));

    $(`<p>${menoMusicData.lyrics_data}</p>`).appendTo(".lyric_area");
})