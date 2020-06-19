package com.st.network.http;

import com.st.network.http.config.RxNetwork;
import com.st.network.utils.LogUtil;
import com.st.network.utils.NetworkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * create by Dennis
 * on 2019-12-24
 * description：
 **/
public class CacheInterceptor implements Interceptor {
    private int mMaxAge = 0;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtil.isNetworkConnected(RxNetwork.getInstance().getContext())) {
            LogUtil.d("network is not reachable, just force cache");
            //对于无网络的情况，强制使用缓存中的数据。
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        Response.Builder newBuilder = response.newBuilder();
        if (NetworkUtil.isNetworkConnected(RxNetwork.getInstance().getContext())) {
            LogUtil.d("network is normal, use cache if it within the max-age value");
            return newBuilder
                    .removeHeader("Pragma")
                    .header("Cache-Control", "max-age=" + mMaxAge)
                    .build();
        } else {
            //无网络情况下，请求头已经做了强制使用缓存处理，返回的response已经是缓存中的了
            return newBuilder.build();
        }
    }

    public void setMaxAge(int mMaxAge) {
        this.mMaxAge = mMaxAge;
    }
}
