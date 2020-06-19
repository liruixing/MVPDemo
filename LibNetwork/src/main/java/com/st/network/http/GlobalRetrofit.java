package com.st.network.http;

import com.st.network.http.config.RxNetworkConfig;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * create by Dennis
 * on 2019-12-24
 * description：
 **/
public class GlobalRetrofit {
    private OkHttpClient mOkHttpClient;
    private Map<String, Retrofit> mRetrofitMap;

    private GlobalRetrofit() {
        mRetrofitMap = new HashMap<>(2);
    }

    private static class SingletonHolder {
        private static final GlobalRetrofit INSTANCE = new GlobalRetrofit();

        private SingletonHolder() {
        }
    }

    public static GlobalRetrofit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void setupConfig(RxNetworkConfig rxNetworkConfig) {
        mOkHttpClient = OkHttpFactory.getOkHttpClient(rxNetworkConfig);
    }

    public void setupConfig(OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
    }

    public Retrofit getRetrofit(String baseUrl) {
        Retrofit retrofit = getRetrofitByCache(baseUrl);
        if (retrofit == null) {
            checkNull();
            retrofit = RetrofitFactory.getInstance().getRetrofit(baseUrl, mOkHttpClient);
            putRetrofitToCache(baseUrl, retrofit);
        }
        return retrofit;
    }

    public Retrofit getRetrofit(String baseUrl, Converter.Factory... factory) {
        Retrofit retrofit = getRetrofitByCache(baseUrl);
        if (retrofit == null) {
            checkNull();
            retrofit = RetrofitFactory.getInstance().getRetrofit(baseUrl, mOkHttpClient, factory);
            putRetrofitToCache(baseUrl, retrofit);
        }
        return retrofit;
    }

    private Retrofit getRetrofitByCache(String baseUrl) {
        Retrofit retrofit = null;
        if (mRetrofitMap.containsKey(baseUrl)) {
            retrofit = mRetrofitMap.get(baseUrl);
        }
        return retrofit;
    }

    private void putRetrofitToCache(String baseUrl, Retrofit retrofit) {
        mRetrofitMap.put(baseUrl, retrofit);
    }

    private void checkNull() {
        if (mOkHttpClient == null) {
            throw new IllegalStateException("OkHttpClient can not be null");
        }
    }
}
