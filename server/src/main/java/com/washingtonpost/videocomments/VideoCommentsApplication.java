package com.washingtonpost.videocomments;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.IApplication;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.slf4j.Logger;


public class VideoCommentsApplication implements IApplication {
    private Logger log = Red5LoggerFactory.getLogger(VideoCommentsApplication.class);


    @Override
    public boolean appStart(IScope app) {
        log.debug("App start");
        return true;
    }

    @Override
    public boolean appConnect(IConnection conn, Object[] params) {
        log.debug("App connect");
        return true;
    }

    @Override
    public boolean appJoin(IClient client, IScope app) {
        log.debug("App join");
        return true;
    }

    @Override
    public void appDisconnect(IConnection conn) {
        log.debug("App disconnect");
    }

    @Override
    public void appLeave(IClient client, IScope app) {
        log.debug("App leave");
    }

    @Override
    public void appStop(IScope app) {
        log.debug("App stop");
    }

    @Override
    public boolean roomStart(IScope room) {
        log.debug("Room start");
        return true;
    }

    @Override
    public boolean roomConnect(IConnection conn, Object[] params) {
        log.debug("Room connect");
        return true;
    }

    @Override
    public boolean roomJoin(IClient client, IScope room) {
        log.debug("Room join");
        return true;
    }

    @Override
    public void roomDisconnect(IConnection conn) {
        log.debug("Room disconnect");
    }

    @Override
    public void roomLeave(IClient client, IScope room) {
        log.debug("Room leave");
    }

    @Override
    public void roomStop(IScope room) {
        log.debug("Room stop");
    }
}
