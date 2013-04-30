package {
import flash.events.Event;
import flash.events.ProgressEvent;
import flash.net.FileFilter;
import flash.net.FileReference;
import flash.net.URLRequest;

public class VideoUploadHelper {

    private static const videoTypes:Array = [new FileFilter("Video files", "*.flv;*.mp4;*.mpg;*.avi")];
    private var fileReference:FileReference = new FileReference();
    private var url:String;
    private var id:String;

    public function VideoUploadHelper(url:String, id:String) {
        fileReference.addEventListener(Event.SELECT, selectionHandler);
        fileReference.addEventListener(Event.COMPLETE, uploadCompletionHandler);
        fileReference.addEventListener(ProgressEvent.PROGRESS, progressHandler);
        this.url = url;
        this.id = id;
    }

    public function upload() {
        fileReference.browse(videoTypes);
    }

    private function progressHandler(event:ProgressEvent):void {
    }

    private function selectionHandler(event:Event):void {
        var request:URLRequest = new URLRequest(url + "/video?id=" + id);
        fileReference.upload(request);
    }

    private function uploadCompletionHandler(event:Event):void {

    }
}
}
