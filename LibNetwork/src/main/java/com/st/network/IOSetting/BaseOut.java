package com.st.network.IOSetting;

/**
 * create by Dennis
 * on 2020-01-07
 * description：
 **/
public class BaseOut {
    private String mIP = "";
    private int mPort ;

    public String getIP() {
        return mIP;
    }

    public void setIP(String IP) {
        mIP = IP;
    }

    public int getPort() {
        return mPort;
    }

    public void setPort(int port) {
        mPort = port;
    }

    @Override
    public String toString() {
        return "BaseOut{" +
                "mIP='" + mIP + '\'' +
                ", mPort='" + mPort + '\'' +
                '}';
    }
}
