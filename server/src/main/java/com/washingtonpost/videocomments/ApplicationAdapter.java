package com.washingtonpost.videocomments;


import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.IApplication;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.stream.IBroadcastStream;
import org.slf4j.Logger;

public class ApplicationAdapter extends MultiThreadedApplicationAdapter{

    private Logger log = Red5LoggerFactory.getLogger(ApplicationAdapter.class);

    private IApplication listener;

    public void register() {
        this.addListener(listener);
    }

    public void setListener(IApplication listener) {
        this.listener = listener;
    }

    public void streamRecordStart(IBroadcastStream stream) {
        IConnection connection = Red5.getConnectionLocal();
        log.debug("Start stream");
        super.streamRecordStart(stream);
    }

    public void streamBroadcastClose(IBroadcastStream stream) {
        // log w3c connect event
        IConnection conn = Red5.getConnectionLocal();
        log.debug("Stop stream");
        super.streamBroadcastClose(stream);
    }
}