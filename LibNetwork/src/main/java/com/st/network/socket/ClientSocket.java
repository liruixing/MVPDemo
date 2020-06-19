package com.st.network.socket;

import android.util.Log;

import com.st.network.IOSetting.COut;
import com.st.network.IOSetting.CoutPrase;
import com.st.network.socketBase.IBaseSocket;
import com.st.network.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * create by Dennis
 * on 2019-12-24
 * description：
 **/
public class ClientSocket implements ClientSocketView {
    private final String TAG = ClientSocket.class.getSimpleName();

    private Socket mClientSocket = null;
    private String mIPAddress;//IP地址
    private int mPort;//端口

    public ClientSocket(String ipAddress, int port) {
        this.mIPAddress = ipAddress;
        this.mPort = port;
    }

    @Override
    public Observable<Object> connectClient() {
        return Observable.create((ObservableOnSubscribe<Object>) emitter -> {
            if (mClientSocket != null) {
                closeCliectSocket();//先关闭再开启
            }
            try {
                mClientSocket = new Socket(mIPAddress, mPort);
                mClientSocket.setSoTimeout(IBaseSocket.SocketTimeOut);
                emitter.onNext(new Object());
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.e("connect error:" + e.getMessage() + " ipaddress:" + mIPAddress);
                emitter.onError(e);
            } finally {
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Object> sendClientData(COut poMessage) {
        return Observable.create((ObservableOnSubscribe<Object>) emitter -> {
            try{
                sendData(poMessage,0);
            } catch (Exception e) {
                e.printStackTrace();
                emitter.onError(e);
            } finally {
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Object> releaseClient() {
        return Observable.create((ObservableOnSubscribe<Object>) emitter -> {
            ClientSocket.this.closeCliectSocket();
            emitter.onNext(new Object());
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void closeCliectSocket() {
        try {
            if (mClientSocket != null) {
                if (!mClientSocket.isClosed())
                    mClientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, e.getMessage());
        } finally {
            mClientSocket = null;
        }
    }


    /**
     * 发送消息
     * @param poMessage 消息体
     * @param retryCount 重试次数
     */
    private void sendData(COut poMessage,int retryCount) throws Exception {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        byte[] laBytes;
        laBytes = CoutPrase.OpenOUT(poMessage);
        if (mClientSocket != null) {
            outputStream = mClientSocket.getOutputStream();
            outputStream.write(laBytes, 0, laBytes.length);
            outputStream.flush();
            //============接收socket信息===========//
            byte[] recvBuf = new byte[1024];
            inputStream = mClientSocket.getInputStream();
            int liRead = inputStream.read(recvBuf);
            boolean isSuccess = false;
            if (liRead == 12) {
                for (int i = 0; i < 12; i++) {
                    if (i != 5 && laBytes[i] != recvBuf[i]) {
                        isSuccess = false;
                    }
                }
                isSuccess = true;

                if(!isSuccess && retryCount < 3){
                    Thread.sleep(60);
                    retryCount++;
                    sendData(poMessage,retryCount);
                }else if(!isSuccess && retryCount >2){
                    new Throwable(new Exception("send Data is timeOut."));
                }
            }
        } else {
            new Throwable(new Exception("ClientSocket is null"));
        }
    }

    public String getIPAddress() {
        return mIPAddress;
    }

    public int getPort() {
        return mPort;
    }
}
