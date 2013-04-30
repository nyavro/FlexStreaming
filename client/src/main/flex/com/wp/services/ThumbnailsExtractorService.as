package com.wp.services {
import flash.display.BitmapData;
import flash.events.TimerEvent;
import flash.media.Video;
import flash.utils.Timer;

public class ThumbnailsExtractorService {
    private var video:Video;
    private var timer:Timer;
    private var thumbnails:Array;

    public function ThumbnailsExtractorService(video:Video) {
        this.video = video;
    }

    public function start():void {
        thumbnails = new Array();
        timer = new Timer(1000);
        timer.start();
        timer.addEventListener(TimerEvent.TIMER, makeSnapshot);
    }

    public function stop():Array {
        timer.stop();
        return thumbnails;
    }

    private function makeSnapshot(event:TimerEvent):void {
        var bitmapData:BitmapData = new BitmapData(video.width*0.45, video.height*0.45);
        bitmapData.draw(video);
        thumbnails.push(bitmapData);
    }
}
}
