package com.st.network.socket;

import com.st.network.socket.listener.ServerSocketListener;
import com.st.network.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * create by Dennis
 * on 2019-12-24
 * description：
 **/
public class ServerBaseSocket {

    private ServerSocket serverSocket = null;
    //线程池
    private ExecutorService executorServer;
    // socket client 集合
    private List<Socket> serverClientList = new ArrayList<>();

    private int mPort;
    //PLC 结束符
    private String END = "<E>";

    private ServerSocketListener mServerSocketListener = null;


    public ServerBaseSocket(int nPort) {
        this.mPort = nPort;
    }

    public void setServerSocketListener(ServerSocketListener serverSocketListener) {
        mServerSocketListener = serverSocketListener;
    }

    /**
     * region TCP Server
     * 减少自定义线程的开销，交由rxjava进行部分的线程管理
     */
    public Observable<Object> startServerClient() {
        return Observable.create((ObservableOnSubscribe<Object>) emitter -> {
            if(executorServer!=null
                    && serverSocket != null
                    && !serverSocket.isClosed()){//判断是否已经关闭状态
                emitter.onNext(new Object());
                return;
            }
            if (executorServer == null) {
                executorServer = Executors.newCachedThreadPool(); // 创建线程池
            }
            serverSocket = new ServerSocket(mPort);
            //服务启动前三秒不接收数据，防止老的数据干扰
//                emitter.onNext(new Object());
//            Thread.sleep(3000);//先不开启
            emitter.onNext(new Object());//这个发射器需要确定在哪触发
            LogUtil.d("plc ServerSocket:" + serverSocket + "  Port:" + mPort);
            while (serverSocket != null && !serverSocket.isClosed()) {
                Socket client = serverSocket.accept();
                if (client != null) {
                    serverClientList.add(client);
                    executorServer.execute(new ServerSocketRunnable(client));
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * plc 连接过来的socket thread
     */
    public class ServerSocketRunnable implements Runnable {

        private Socket mSocket;
        private InputStream mInputStream;
        private OutputStream mOutputStream;
        private StringBuilder sb = new StringBuilder();
        private byte[] bytesBuffer = new byte[1024];

        public ServerSocketRunnable(Socket socket) throws IOException {
            mSocket = socket;
            mInputStream = socket.getInputStream();
            mOutputStream = socket.getOutputStream();
            sb.setLength(0);
        }

        @Override
        public void run() {
            try {
                int liRead = 0;
                while (true) {
                    liRead = mInputStream.read(bytesBuffer, 0, 1024);
                    if (liRead > 0) {
                        sb.append(new String(bytesBuffer, 0, liRead));
                        spitData(mSocket.getInetAddress());
                    } else {
                        LogUtil.e("liRead == 0 break");
                        break;
                    }
                }
            } catch (IOException e) {
                LogUtil.e("plc: " + e.getMessage());
                e.printStackTrace();
            } finally {
                sb.setLength(0);
                if (mSocket != null) {
                    try {
                        if (!mSocket.isClosed()) {
                            mSocket.close();
                            if (serverClientList.contains(mSocket)) {
                                synchronized (this) {
                                    serverClientList.remove(mSocket);
                                }
                            }
                        } else {

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        mSocket = null;
                    }
                }
                if (mInputStream != null) {
                    try {
                        mInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        mInputStream = null;
                    }
                }
                if (mOutputStream != null) {
                    try {
                        mOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        mOutputStream = null;
                    }
                }
            }
        }

        /**
         * 分包数据
         *
         * @param inetAddress
         */
        private void spitData(InetAddress inetAddress) throws IOException {
            String string = sb.toString();
            boolean isEndSign = string.endsWith(END);
            String[] split = string.split(END);
            if (split.length > 0) {
                for (int i = 0; i < split.length; i++) {
                    String str = split[i];
                    if (i == split.length - 1 && !isEndSign) {//最后一个 并且没有结束
                        sb.setLength(0);
                        sb.append(split[i]);
                    } else {
                        if (mServerSocketListener != null && inetAddress != null) {
                            mServerSocketListener.PlcMessageReceivedDelegate(str,
                                    inetAddress.getHostAddress());
                        } else {
                            LogUtil.d("listenser == null  mSocket:" + mSocket);
                        }
                        byte[] bytesSend = str.getBytes(Charset.forName("UTF-8"));
                        if (bytesSend.length >= 12) {
                            bytesSend[5] = 0x06;
                            mOutputStream.write(bytesSend, 0, 12);
                        }
                    }
                }
                if (isEndSign) {
                    sb.setLength(0);
                }
            }
        }
    }


    /**
     * 关闭server client
     */
    public void StopListening() {
        //关闭socket
        if (serverClientList.size() > 0) {
            synchronized (this) {
                for (int i = 0; i < serverClientList.size(); i++) {
                    Socket socket = serverClientList.get(i);
                    if (socket != null) {
                        try {
                            if (!socket.isClosed()) {
                                socket.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            socket = null;
                        }
                    } else {
                        LogUtil.d("socket = null");
                    }
                }
                serverClientList.clear();
            }
        }
        if (serverSocket != null) {
            try {
                if (!serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                serverSocket = null;
            }
        }
    }
}
