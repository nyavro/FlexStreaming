package com.washingtonpost.videocomments;


import com.google.common.base.Joiner;
import com.washingtonpost.videocomments.service.VideoCommentsService;
import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.IApplication;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.stream.IBroadcastStream;
import org.slf4j.Logger;

public class ApplicationAdapter extends MultiThreadedApplicationAdapter{

    private Logger log = Red5LoggerFactory.getLogger(ApplicationAdapter.class);

    private VideoCommentsService videoCommentsService;

    public void streamRecordStart(IBroadcastStream stream) {
//        Long streamId = videoCommentsService.createNewComment();

        IConnection connection = Red5.getConnectionLocal();
//        connection.setAttribute("streamId", streamId);
//        log.debug("Start stream " + streamId);
        super.streamRecordStart(stream);
    }

    public void streamBroadcastClose(IBroadcastStream stream) {
        // log w3c connect event
        IConnection connection = Red5.getConnectionLocal();
        log.debug("Stop stream");
        super.streamBroadcastClose(stream);

//        Long streamId = (Long) connection.getAttribute("streamId");
//        videoCommentsService.complete(streamId);
    }

    public void setVideoCommentsService(VideoCommentsService videoCommentsService) {
        this.videoCommentsService = videoCommentsService;
    }
}