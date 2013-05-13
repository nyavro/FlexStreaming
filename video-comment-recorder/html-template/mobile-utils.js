function handleFileSelect(evt) {
    $.ajax({
        url: URL + '/videohtml?id=' + id,
        type: 'POST',
        enctype: 'multipart/form-data',
        data: evt.target.files[0],
        success: function (data) {
            var videoUrl = EMBED_CODE_TEMPLATE + id;
            var embedTemplate = "<<span>div</span> id='{0}'></div><<span>script</span> type='text/javascript'>jwplayer('{0}').setup({file: '{1}', width: '{2}',height: '{3}'});</<span>script</span>>";
            $("#embedCode").append("<a href='" + videoUrl + "')>Show uploaded video</a>");
            $("#code").append(embedTemplate.format(id, videoUrl, EMBED_WIDTH,EMBED_HEIGHT));
        },
        cache: false,
        contentType: false,
        processData: false
    });
}

function requestId() {
    $.ajax({
        url: URL + '/create',
        success: function (data) {id = data;}
    });
}

if (!String.prototype.format) {
    String.prototype.format = function() {
        var args = arguments;
        return this.replace(/{(\d+)}/g, function(match, number) {
            return typeof args[number] != 'undefined'
                ? args[number]
                : match
                ;
        });
    };
}

function isMobile(){
    return window.screen.width*window.screen.height/Math.pow((window.devicePixelRatio == undefined || navigator.userAgent.match(/(iPhone|iPod|iPad)/i) ? 1 : window.devicePixelRatio),2) < 480000;
}

function isFlashEnabled(){
    return ((typeof navigator.plugins != "undefined" && typeof navigator.plugins["Shockwave Flash"] == "object") || (window.ActiveXObject && (new ActiveXObject("ShockwaveFlash.ShockwaveFlash")) != false));
}