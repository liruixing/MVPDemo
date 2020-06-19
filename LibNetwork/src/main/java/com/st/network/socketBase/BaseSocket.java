package com.st.network.socketBase;


import java.net.Socket;

/**
 * create by Dennis
 * on 2020-01-07
 * description：
 **/
public class BaseSocket {
    private final String TAG = BaseSocket.class.getSimpleName();
    private Socket mSocket = null;

    protected void clientConnect(String ip,String port){}

    protected void clientSendData(String data){}

    protected void closeClient(){}




}
