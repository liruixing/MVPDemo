package com.st.network.socket.listener;

/**
 * create by Dennis
 * on 2019-12-25
 * description：
 **/
public interface ServerSocketListener {

    void PlcMessageReceivedDelegate(String pcMessage,String ip);

}
