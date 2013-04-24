/**
 * Created with IntelliJ IDEA.
 * User: eny
 * Date: 4/24/13
 * Time: 4:12 PM
 * To change this template use File | Settings | File Templates.
 */
package {
import flash.events.Event;
import flash.events.ProgressEvent;
import flash.net.FileFilter;
import flash.net.FileReference;
import flash.net.URLRequest;

public class VideoUploadHelper {

    private static const videoTypes:Array = [new FileFilter("Video files", "*.flv;*.mp4;*.mpg;*.avi")];
    private var fileReference:FileReference = new FileReference();

    public function VideoUploadHelper() {
        fileReference.addEventListener(Event.SELECT, selectionHandler);
        fileReference.addEventListener(Event.COMPLETE, uploadCompletionHandler);
        fileReference.addEventListener(ProgressEvent.PROGRESS, progressHandler);
    }

    public function upload() {
        fileReference.browse(videoTypes);
    }

    private function progressHandler(event:ProgressEvent):void {

    }

    private function selectionHandler(event:Event):void {
        var request:URLRequest = new URLRequest();
        //TODO: make configurable
        request.url = "http://localhost/api/upload";
        fileReference.upload(request);
    }

    private function uploadCompletionHandler(event:Event):void {

    }
}
}
