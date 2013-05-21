package com.wp.utils {
import flash.events.DataEvent;
import flash.events.Event;
import flash.events.ProgressEvent;
import flash.net.FileFilter;
import flash.net.FileReference;
import flash.net.URLRequest;

import mx.controls.Alert;

import mx.controls.ProgressBar;

public class VideoUploadHelper {

    private static const videoTypes:Array = [new FileFilter("Video files", "*.flv;*.mp4;*.webm;*.3gp")];
    private var fileReference:FileReference = new FileReference();
    private var url:String;
    private var id:String;
    private var progress:ProgressBar;
    private var progressHandler:Function;
    private var completionHandler:Function;

    public function VideoUploadHelper(url:String, id:String, progressHandler:Function, completionHandler:Function) {
        fileReference.addEventListener(Event.SELECT, selectionHandler);
//        fileReference.addEventListener(Event.COMPLETE, onComplete);
        fileReference.addEventListener(DataEvent.UPLOAD_COMPLETE_DATA, uploadDataComplete);
        fileReference.addEventListener(Event.CLOSE, onComplete);
        fileReference.addEventListener(Event.CANCEL, onComplete);
        fileReference.addEventListener(ProgressEvent.PROGRESS, onProgress);
        this.url = url;
        this.id = id;
        this.progressHandler = progressHandler;
        this.completionHandler = completionHandler;
    }

    public function upload():void {
        fileReference.browse(videoTypes);
    }

    private function onProgress(event:ProgressEvent):void {
        progressHandler(event.bytesLoaded, event.bytesTotal);
    }

    private function selectionHandler(event:Event):void {
        var request:URLRequest = new URLRequest(url + "/video?id=" + id);
        try {
            fileReference.upload(request);
        } catch (error:Error) {
            Alert.show(error.message);
            completionHandler(null);
        }
    }

    public function uploadDataComplete(event:DataEvent):void {
        completionHandler(event.data);
    }

    private function onComplete(event:Event):void {
        completionHandler(null);
    }
}
}
