package com.wp.services {
import flash.display.BitmapData;
import flash.events.TimerEvent;
import flash.media.Video;
import flash.utils.Timer;

public class ThumbnailsExtractorService {
    private var video:Video;
    private var timer:Timer;
    private var thumbnails:Array;
    private var extractWidth:int;
    private var extractHeight:int;

    public function ThumbnailsExtractorService(video:Video, extractWidth:int, extractHeight:int) {
        this.video = video;
        this.extractWidth = extractWidth;
        this.extractHeight = extractHeight;
    }

    public function start():void {
        thumbnails = new Array();
        timer = new Timer(1000);
        timer.start();
        timer.addEventListener(TimerEvent.TIMER, makeSnapshot);
    }

    public function stop():void {
        timer.stop();
    }

    public function getThumbnails():Array {
        return thumbnails;
    }

    private function makeSnapshot(event:TimerEvent):void {
        var bitmapData:BitmapData = new BitmapData(extractWidth, extractHeight);
        bitmapData.draw(video);
        thumbnails.push(bitmapData);
    }
}
}
