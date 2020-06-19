package com.st.network.socket;

import com.st.network.IOSetting.COut;

import io.reactivex.Observable;

/**
 * create by Dennis
 * on 2019-12-24
 * description：
 **/
public interface ClientSocketView {

    Observable<Object> connectClient();//建立链接

    Observable<Object> sendClientData(COut poMessage);//发送数据

    Observable<Object> releaseClient();//释放内存

    void closeCliectSocket();//关闭socket

}
