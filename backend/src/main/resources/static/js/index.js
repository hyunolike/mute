function getSearchApi() {
    // const getSearchName = document.getElementById("search-name").value;

    var count = 0;

    // 브라우저 시작 시 10개의 데이터 가져옴
    jQuery(document).ready(function ($) {
        console.log("start api")
        for(let i = 0; i < 2; i++){
            bringData(count)
            count++;
        }
    })

    const getLastEle = () => document.querySelector("#memo-item > .item:last-child")
    const infScrollCallback = (entries, observer) => {
        const entry = entries[0];
        if(!entry.isIntersecting) return;
        count += 1;
        bringData(count);
        observerLastEle();
        observer.unobserve(entry.target); //스크롤 위로 움직일때는 요청하지 않도록하는 기능
    };

    const infScrollObserver = new IntersectionObserver(infScrollCallback, {})
    const observerLastEle = () => {
        infScrollObserver.observe(getLastEle());
    }

    // function bringData(count){
    //     axios.get('http://localhost:9061/api/', {
    //         params: {
    //             "article-name": getSearchName
    //         }
    //     }).then(function (res) {
    //         $('<div class="item">' + '<h2>' + res.data.musics[count].music_name + '</h2>' + '<img src="' + res.data.musics[count].album_url + '"/>' + '</div>').appendTo('#memo-item');
    //         observerLastEle();
    //     })
    // }

    function bringData(count){
        axios.get('http://localhost:9061/api/musicmark')
            .then(function (res) {
                    $('<div class="item">' + '<div class="item-img">' + '<img src="' + res.data.musicmark_list[count].album_url + '"/>' + '</div>' + '<div class="item-title">' + res.data.musicmark_list[count].music_name + '</div>' + '<div class="item-singer">' + res.data.musicmark_list[count].singer + '</div>' + '</div>').appendTo('#memo-item');
                    observerLastEle();
                })
    }
}

getSearchApi();