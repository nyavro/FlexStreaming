package com.washingtonpost.videocomments;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.IConnection;

public class VideoCommentsAdapter extends ApplicationAdapter {

    private static final Log log = LogFactory.getLog(VideoCommentsAdapter.class);

    public boolean appStart() {
        log.info("VideoComments.appStart");
        return true;
    }

    public void appStop() {
        log.info("VideoComments.appStop");
    }

    public boolean appConnect(IConnection conn, Object[] params) {
        log.info("VideoComments.appConnect " + conn.getClient().getId());
        return true;
    }

    public void appDisconnect(IConnection conn, Object[] params) {
        log.info("VideoComments.appDisconnect " + conn.getClient().getId());
    }

}
