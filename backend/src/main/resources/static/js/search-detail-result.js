function selectText() {
    console.log("mouse move");

    var selectionText = "";
    if (document.getSelection) {
        selectionText = document.getSelection();

    } else if (document.selection) {
        selectionText = document.selection.createRange().text;
        var html = "<div style='background-color: red; padding: 10px'>" + selectionText + "</div>";
        document.selection.createRange().pasteHTML(html);
    }

    return selectionText;
}

document.onmouseup = function() {
    document.getElementById("console").innerHTML = selectText();
}