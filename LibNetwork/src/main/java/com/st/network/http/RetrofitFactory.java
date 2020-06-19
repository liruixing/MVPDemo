package com.st.network.http;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * create by Dennis
 * on 2019-12-24
 * description：
 **/
public class RetrofitFactory {

    private RetrofitFactory() {
    }

    private static class SingletonHolder {
        private static final RetrofitFactory INSTANCE = new RetrofitFactory();

        private SingletonHolder() {
        }
    }

    public static RetrofitFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Retrofit getRetrofit(String baseUrl, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    public Retrofit getRetrofit(String baseUrl, OkHttpClient okHttpClient, Converter.Factory... factory) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl);
        if (factory != null && factory.length != 0) {
            for (Converter.Factory fa : factory) {
                builder.addConverterFactory(fa);
            }
        }
        builder.addConverterFactory(GsonConverterFactory.create());
        return builder.build();
    }

}
