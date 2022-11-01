function getMemoApi() {
    var count = 0;

    // 브라우저 시작 시 10개의 데이터 가져옴
    jQuery(document).ready(function ($) {
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

    function bringData(count){
        axios.get(`http://${PATH.PUBLIC_IP}/api/musicmark`)
            .then(function (res) {
                    $('<div class="item" onclick="goMemo('+ res.data.musicmark_list[count].memo_id + ')">' + '<div class="item-img">' + '<img src="' + res.data.musicmark_list[count].album_url + '"/>' + '</div>' + '<div class="item-title">' + res.data.musicmark_list[count].music_name + '</div>' + '<div class="item-singer">' + res.data.musicmark_list[count].singer + '</div>' + '</div>').appendTo('#memo-item');
                    observerLastEle();
                })
    }
}

getMemoApi();

function goMemo(memoId){
    axios.get(`http://${PATH.PUBLIC_IP}/api/musicmark/${memoId}`)
        .then(function (res){
        var memoInfoData = {
            "music_name": res.data.music_name,
            "singer": res.data.singer,
            "mark_info": res.data.mark_info,
            "memo": res.data.memo
        }

        localStorage.setItem("memoInfoData", JSON.stringify(memoInfoData));

        location.replace(`http://${PATH.PUBLIC_IP}/memo`);
    })
}