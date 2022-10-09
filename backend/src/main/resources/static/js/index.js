function getSearchApi() {
    const getSearchName = document.getElementById("search-name").value;

    var count = 0;

    // 브라우저 시작 시 10개의 데이터 가져옴
    jQuery(document).ready(function ($) {
        console.log("start api")
        for(let i = 0; i < 10; i++){
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

    function bringData(count){
        axios.get('http://localhost:9061/api/search', {
            params: {
                "article-name": getSearchName
            }
        }).then(function (res) {
            $('<div class="item">' + '<h2>' + res.data.musics[count].music_name + '</h2>' + '<img src="' + res.data.musics[count].album_url + '"/>' + '</div>').appendTo('#memo-item');
            observerLastEle();
        })
    }
}