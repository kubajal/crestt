function showJSON(json, jsonElement) {
    var jsonViewer = new JSONViewer();
    var selector = document.querySelector(jsonElement);
    selector.appendChild(jsonViewer.getContainer());
    var maxLvl = -1;
    var colAt = -1;
    jsonViewer.showJSON(json, maxLvl, colAt);
}
