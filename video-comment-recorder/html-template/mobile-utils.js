(function ($) {
    $.fn.videocomment = function (host, swfUrl, embedWidth, embedHeight, videoMaxDuration, question) {
        var PROTOCOL = "http";
        var PORT = "8080";
        var APP = "videocomments";
        var URL = PROTOCOL + '://' + host + ':' + PORT + '/' + APP + '/api';
        var EMBED_CODE_TEMPLATE = URL + "/video?id=";
        var id;
        if(!isMobile()) {
            if(isFlashEnabled()) {
                var flashvars = {};
                flashvars.liveUrl = "rtmp://" + host + "/";
                flashvars.servletUrl = URL;
                flashvars.videoMaxDuration = videoMaxDuration;
                flashvars.embedWidth = embedWidth;
                flashvars.embedHeight = embedHeight;
                flashvars.embedUrlTemplate = EMBED_CODE_TEMPLATE + "{0}";
                flashvars.question = question;
                var params = {};
                params.menu = "false";
                params.quality = "best";
                params.scale = "noScale";
                params.align = "left";
                params.salign = "lt";
                params.bgcolor = "#ffffff";
                params.allowscriptaccess = "sameDomain";
                params.allowFullScreen = "true";
                var attributes = {};
                attributes.id = "swfPlaceholder";
                attributes.name = "swfPlaceholder";
                swfobject.embedSWF(swfUrl, this.attr("id"), "100%", "100%", "9.0.124", "expressInstall.swf", flashvars, params, attributes);
            }
            else {
                this
                    .append("<h1>Sorry, you need to install flash player and enable javascript to see this content</h1>")
                    .append("<p><a href='http://www.adobe.com/go/getflashplayer'><img src='http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player'/></a></p>");
            }
        }
        else {
            this.append("<input type='file' id='record' name='files[]' capture accept='video/*.mp4'/>");
            requestId();
            $("#record")[0].addEventListener('change', handleFileSelect, false);
        }
        return this;
    };


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
                $("#code").append(embedTemplate.format(id, videoUrl, embedWidth,embedHeight));
            },
            cache: false,
            contentType: false,
            processData: false
        });
    };

    function requestId() {
        $.ajax({
            url: URL + '/create',
            success: function (data) {id = data;}
        });
    };

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
    };

    function isMobile(){
        return window.screen.width*window.screen.height/Math.pow((window.devicePixelRatio == undefined || navigator.userAgent.match(/(iPhone|iPod|iPad)/i) ? 1 : window.devicePixelRatio),2) < 480000;
    };

    function isFlashEnabled(){
        return ((typeof navigator.plugins != "undefined" && typeof navigator.plugins["Shockwave Flash"] == "object") || (window.ActiveXObject && (new ActiveXObject("ShockwaveFlash.ShockwaveFlash")) != false));
    };

}(jQuery));