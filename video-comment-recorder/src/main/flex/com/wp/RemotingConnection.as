package com.wp {
import flash.net.NetConnection;
import flash.net.ObjectEncoding;

public class RemotingConnection extends NetConnection {

    public function RemotingConnection(url:String) {
        objectEncoding = ObjectEncoding.AMF3;
        if(url) {
            connect(url);
        }
    }
}
}

