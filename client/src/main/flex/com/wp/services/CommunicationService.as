package com.wp.services {
import com.wp.RemotingConnection;

import flash.net.Responder;

import flash.utils.ByteArray;

import mx.controls.Alert;

import mx.utils.StringUtil;

import mx.utils.UIDUtil;

public class CommunicationService {
    private var conn:RemotingConnection;
    public function CommunicationService(url:String) {
        conn = new RemotingConnection(url);
    }

    public function generateId():String {
        //            new AsyncCall(
//                    "http://localhost:8080/api/generateId",
//                    function (videoId:String):void {
//                        this.videoId = videoId;
//                        streamingService = new StreamingService(liveUrl, videoId, cam, mic);
//                    }
//            ).run();
        trace("Generate ID");
        conn.call("RemoteClass.createNewComment", new Responder(idReceived, function ():void {Alert.show('Failure');}));
        return UIDUtil.createUID();
    }

    private function idReceived(event:Object):void {
        Alert.show("id received");
        var id:Number = event as Number;
        Alert.show(id + "");
    }

    public function getEmbedCode(id:String):String {
        var url:String = "url";
        var embedWidth:int = 480;
        var embedHeight:int = 270;
        return StringUtil.substitute(
            "<div id='{0}'></div><script type='text/javascript'>jwplayer('{0}').setup({file: '{1}', width: '{2}',height: '{3}'});</script>",
            [id, url, embedWidth, embedHeight]
        );
    }

    public function setThumbnail(id:String, bytes:ByteArray):void {
        //            var uploadURL:URLRequest = new URLRequest();
//            uploadURL.url = "http://localhost:8080/thumbnail/set?id=123";
//            uploadURL.contentType = 'application/octet-stream';
//            uploadURL.method = URLRequestMethod.POST;
//            uploadURL.data = encodeToJPEG((image.source as Bitmap).bitmapData);
//            var urlLoader:URLLoader = new URLLoader();
//            urlLoader.load(uploadURL);
    }

    public function upload(bytes:ByteArray):void {
    }
}
}
