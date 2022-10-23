$(document).ready(function (){
    let menoMusicData = JSON.parse(localStorage.getItem("menoMusicData"));

    $(`<p>${menoMusicData.lyrics_data}</p>`).appendTo(".lyric_area");
})


var memo = document.getElementById("typing_val");

memo.addEventListener('mouseout', () => {
    var menoData = {
        "meno_data": memo.value
    }
    localStorage.setItem("menoData", JSON.stringify(menoData));
})