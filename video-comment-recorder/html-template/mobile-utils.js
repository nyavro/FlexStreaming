(function ($) {
    $.fn.videocomment = function (host, swfUrl, embedWidth, embedHeight, videoMaxDuration, question) {
        var PROTOCOL = "http";
        var PORT = "8080";
        var APP = "videocomments";
        var APP_URL = PROTOCOL + '://' + host + ':' + PORT + '/' + APP + '/api';
        var EMBED_CODE_TEMPLATE = APP_URL + "/video?id=";
        var settings = {
           id: null,
           url: APP_URL
        };
        this.settings = settings;
        if(!isMobile()) {
            if(isFlashEnabled()) {
                var flashvars = {};
                flashvars.liveUrl = "rtmp://" + host + "/";
                flashvars.servletUrl = APP_URL;
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
            this.append("<input type='file' name='file' capture accept='video/*.mp4'/>");
            requestId(this);
            var self = this;
            $("input[name='file']", this)[0].addEventListener('change', function(evt) { handleFileSelect(self, evt);}, false);
        }
        return this;
    };


    function handleFileSelect(obj, evt) {
        console.log(evt);
        var data = new FormData();
	    var file = $("input[name='file']", obj)[0].files[0];
        data.append('Filedata', file);
        var xhr = new XMLHttpRequest();
        xhr.open('POST', obj.settings.url + "/video?id=" + id, true);
        xhr.send(data);
    };

    function requestId(obj) {
        $.ajax({
            url: obj.settings.url + '/create',
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