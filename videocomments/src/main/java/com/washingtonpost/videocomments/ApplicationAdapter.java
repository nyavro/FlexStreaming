package com.washingtonpost.videocomments;


import com.washingtonpost.videocomments.service.AmazonService;
import com.washingtonpost.videocomments.service.VideoCommentsService;
import org.apache.commons.io.IOUtils;
import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.stream.IBroadcastStream;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;

public class ApplicationAdapter extends MultiThreadedApplicationAdapter {

    private Logger log = Red5LoggerFactory.getLogger(ApplicationAdapter.class);

    private VideoCommentsService videoCommentsService;

    private AmazonService amazonService;

    public void streamRecordStart(IBroadcastStream stream) {
//        Long streamId = videoCommentsService.createNewComment();

        IConnection connection = Red5.getConnectionLocal();
//        connection.setAttribute("streamId", streamId);
        log.debug("Start stream ");
        super.streamRecordStart(stream);
    }

    public void streamBroadcastClose(IBroadcastStream stream) {
        // log w3c connect event
        IConnection connection = Red5.getConnectionLocal();
        log.debug("Stop stream");
        super.streamBroadcastClose(stream);
        String filename = stream.getSaveFilename();
        String publishname = stream.getPublishedName();
        FileInputStream fileInputStream = null;
        try {
            File file = new File(filename);
            if (file.exists() && file.isFile()) {
                fileInputStream = new FileInputStream(file);
            }
            amazonService.upload(fileInputStream, publishname + ".flv", file.length());
        } catch (Exception e) {
            IOUtils.closeQuietly(fileInputStream);
        }
//        Long streamId = (Long) connection.getAttribute("streamId");
//        videoCommentsService.complete(streamId);
    }

    public void setVideoCommentsService(VideoCommentsService videoCommentsService) {
        this.videoCommentsService = videoCommentsService;
    }

    public void setAmazonService(AmazonService amazonService) {
        this.amazonService = amazonService;
    }
}