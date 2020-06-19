package com.st.network.socket;

import com.st.network.IOSetting.COut;

import io.reactivex.Observable;

/**
 * create by Dennis
 * on 2019-12-24
 * description：
 **/
public interface ServerBaseSocketView {

    Observable<Object> createServer(String sIPAddress, int nPort);//建立链接

    Observable<Object> receiveData(COut poMessage);//发送数据

    Observable<Object> releaseClient();//释放内存

    void closeCliectSocket();//关闭socket

}
