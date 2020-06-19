package com.st.network.http;

import com.st.network.http.config.RxNetworkConfig;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * create by Dennis
 * on 2019-12-24
 * description：
 **/
public class SingleRetrofit {

    public SingleRetrofit() {
        //no-op
    }

    public Retrofit getRetrofit(String baseUrl, RxNetworkConfig rxNetworkConfig) {
        OkHttpClient okHttpClient = OkHttpFactory.getOkHttpClient(rxNetworkConfig);
        return RetrofitFactory.getInstance().getRetrofit(baseUrl, okHttpClient);
    }

    public Retrofit getRetrofit(String baseUrl, RxNetworkConfig rxNetworkConfig, Converter.Factory... factory) {
        OkHttpClient okHttpClient = OkHttpFactory.getOkHttpClient(rxNetworkConfig);
        return RetrofitFactory.getInstance().getRetrofit(baseUrl, okHttpClient, factory);
    }

    public Retrofit getRetrofit(String baseUrl, OkHttpClient okHttpClient) {
        return RetrofitFactory.getInstance().getRetrofit(baseUrl, okHttpClient);
    }

    public Retrofit getRetrofit(String baseUrl, OkHttpClient okHttpClient, Converter.Factory... factory) {
        return RetrofitFactory.getInstance().getRetrofit(baseUrl, okHttpClient, factory);
    }
}
