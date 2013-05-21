// TODO get rid off global variables: such as id
// TODO To be reviewed
(function ($) {
    $.fn.videocomments = function (options) {
        var defaults = {
            host: "localhost",
            videourl: "http://localhost",
            protocol: "http",
            port: "8080",
            app: "videocomments",
            swfUrl: "video-comment-recorder-1.0.swf",
            embedWidth: 400,
            embedHeight: 200,
            videoMaxDuration: 20,
            question: "Please record your comment",
            width: 640,
            height: 480
        }
        var settings = $.extend({id: null}, defaults, options); // TODO why the first attribute?
        var appUrl = settings.protocol+"://" + settings.host + ":" + settings.port + "/" + settings.app + "/api";
        var liveUrl = "rtmp://" + settings.host + "/" + settings.app + "/";
        settings.url = appUrl;
        this.settings = settings;
        if(!isMobile()) {
            if(isFlashEnabled()) {
                var flashvars = {};
                flashvars.liveUrl = liveUrl;
                flashvars.servletUrl = appUrl;
                flashvars.videoUrl = settings.videourl;
                flashvars.videoMaxDuration = settings.videoMaxDuration;
                flashvars.embedWidth = settings.embedWidth;
                flashvars.embedHeight = settings.embedHeight;
                flashvars.question = settings.question;
                flashvars.embedTemplate = settings.embedTemplate;
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
                swfobject.embedSWF(settings.swfUrl, this.attr("id"), settings.width, settings.height, "9.0.124", "expressInstall.swf", flashvars, params, attributes);
            }
            else {
                this
                    .append("<h1>Sorry, you need to install flash player and enable javascript to see this content</h1>")
                    .append("<p><a href='http://www.adobe.com/go/getflashplayer'><img src='http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player'/></a></p>");
            }
        }
        else {
            this.append("<input type='file' name='file' capture accept='video/*.mp4'/>");
            this.append("<input type='file' name='thumbnail' accept='image/*'/>");
            getRequestId(this);
            var self = this;
            $("input[name='file']", this)[0].addEventListener('change', function(evt) { handleFileSelect(self, evt);}, false);
            $("input[name='thumbnail']", this)[0].addEventListener('change', function(evt) { handleThumbnailSelect(self, evt);}, false);
        }
        return this;
    };


    function handleFileSelect(obj, evt) {
        //console.log(evt);
        var data = new FormData();
	    var file = $("input[name='file']", obj)[0].files[0];
        data.append('Filedata', file);
        var xhr = new XMLHttpRequest();
        xhr.open('POST', obj.settings.url + "/video?id=" + id, true);
        xhr.send(data);
        obj.videoSent = true;
        finish(obj);
    };

    function handleThumbnailSelect(obj, evt) {
        //console.log(evt);
        var data = new FormData();
        var file = $("input[name='thumbnail']", obj)[0].files[0];
        data.append('Filedata', file);
        var xhr = new XMLHttpRequest();
        xhr.open('POST', obj.settings.url + "/thumbnail?id=" + id, true);
        xhr.send(data);
        obj.thumbnailSent = true;
        finish(obj);
    };

    function finish(obj) {
        if(obj.thumbnailSent && obj.videoSent) {
            $.ajax({
                url: obj.settings.url + '/complete'
            });
        }
    }

    function getRequestId(obj) {
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