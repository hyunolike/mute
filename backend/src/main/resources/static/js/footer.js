// 특정 주소일때 버튼 색상 고정
if(window.location.href.endsWith("home")){
    const div = document.getElementById("main-page-img");
    div.setAttribute('src', '../img/footer/home-mouseover.svg');
}

if(window.location.href.endsWith("search")){
    const div = document.getElementById("search-page-img");
    div.setAttribute('src', '../img/footer/search-mouseover.svg');
}

// if(window.location.href.endsWith("home")){
//     const div = document.getElementById("main-page-img");
//     div.setAttribute('src', '../img/footer/home-mouseover.svg');
// }
//
// if(window.location.href.endsWith("home")){
//     const div = document.getElementById("main-page-img");
//     div.setAttribute('src', '../img/footer/home-mouseover.svg');
// }
