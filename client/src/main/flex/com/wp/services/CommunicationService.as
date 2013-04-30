package com.wp.services {
import com.wp.RemotingConnection;

import flash.events.Event;

import flash.net.Responder;
import flash.net.URLLoader;
import flash.net.URLRequest;
import flash.net.URLRequestMethod;

import flash.utils.ByteArray;

import mx.controls.Alert;

import mx.utils.StringUtil;

import mx.utils.UIDUtil;

public class CommunicationService {
    private var url:String;
    public function CommunicationService(url:String) {
        this.url = url;
    }

    public function create(callback:Function):void {
//        Alert.show(url + "/create");
        var uploadURL:URLRequest = new URLRequest(url + "/create");
        uploadURL.method = URLRequestMethod.POST;
        var urlLoader:URLLoader = new URLLoader();
        urlLoader.addEventListener(Event.COMPLETE, function (event:Event):void {callback(event.target.data);});
        urlLoader.load(uploadURL);
    }

    public function complete(id:String):void {
        var uploadURL:URLRequest = new URLRequest(url + "/complete?id=" + id);
        uploadURL.method = URLRequestMethod.POST;
        var urlLoader:URLLoader = new URLLoader();
        urlLoader.load(uploadURL);
    }

    public function setThumbnail(id:String, bytes:ByteArray):void {
        var uploadURL:URLRequest = new URLRequest(url + "/thumbnail?id=" + id);
        uploadURL.contentType = 'application/octet-stream';
        uploadURL.method = URLRequestMethod.POST;
        uploadURL.data = bytes;
        var urlLoader:URLLoader = new URLLoader();
        urlLoader.load(uploadURL);
    }

    public function upload(bytes:ByteArray):void {
    }
}
}
