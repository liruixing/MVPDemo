package com.st.network.http.config;

import android.annotation.SuppressLint;
import android.content.Context;

import com.st.network.http.GlobalRetrofit;
import com.st.network.http.SingleRetrofit;
import com.st.network.utils.LogUtil;

import okhttp3.OkHttpClient;
import retrofit2.Converter;

/**
 * create by Dennis
 * on 2019-12-24
 * description：
 **/
public class RxNetwork {
    @SuppressLint("StaticFieldLeak")
    private static Context sContext;
    private static boolean sLog;

    private RxNetwork() {
        if (sContext == null) {
            throw new IllegalStateException("The RxNetwork has not been initialized yet");
        }
    }

    public static RxNetwork getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        @SuppressLint("StaticFieldLeak")
        private static final RxNetwork INSTANCE = new RxNetwork();

        private SingletonHolder() {
        }
    }

    public static void init(Context context, Boolean isLog) {
        setup(context, isLog);
    }

    public static void init(Context context, Boolean isLog, RxNetworkConfig rxNetworkConfig) {
        setup(context, isLog);
        setGlobalNetworkConfig(rxNetworkConfig);
    }

    public static void init(Context context, Boolean isLog, OkHttpClient okHttpClient) {
        setup(context, isLog);
        setGlobalNetworkConfig(okHttpClient);
    }

    private static void setup(Context context, Boolean isLog) {
        if (sContext != null) {
            throw new IllegalStateException("The RxNetwork has been initialized");
        }
        sContext = context;
        sLog = isLog;
        LogUtil.setLog(isLog);
        LogUtil.i("init RxNetwork");
    }

    public static void setGlobalNetworkConfig(RxNetworkConfig rxNetworkConfig) {
        GlobalRetrofit.getInstance().setupConfig(rxNetworkConfig);
    }

    public static void setGlobalNetworkConfig(OkHttpClient okHttpClient) {
        GlobalRetrofit.getInstance().setupConfig(okHttpClient);
    }

    public Context getContext() {
        return sContext;
    }

    public boolean isLog() {
        return sLog;
    }

    /**
     * Create an implementation of the API by global network config
     *
     * @param baseUrl base url
     * @param cls     API
     * @return the implementation
     */
    public <T> T createApi(String baseUrl, Class<T> cls) {
        return GlobalRetrofit.getInstance().getRetrofit(baseUrl).create(cls);
    }

    /**
     * Create an implementation of the API by global network config
     *
     * @param baseUrl base url
     * @param cls     API
     * @param factory customize converter factory
     * @return the implementation
     */
    public <T> T createApi(String baseUrl, Class<T> cls, Converter.Factory... factory) {
        return GlobalRetrofit.getInstance().getRetrofit(baseUrl, factory).create(cls);
    }

    /**
     * Create an implementation of the API by single network config
     *
     * @param baseUrl         base url
     * @param cls             API
     * @param rxNetworkConfig rxNetwork config
     * @return the implementation
     */
    public <T> T createSingleApi(String baseUrl, Class<T> cls, RxNetworkConfig rxNetworkConfig) {
        SingleRetrofit singleRetrofit = new SingleRetrofit();
        return singleRetrofit.getRetrofit(baseUrl, rxNetworkConfig).create(cls);
    }

    /**
     * Create an implementation of the API by single network config
     *
     * @param baseUrl         base url
     * @param cls             API
     * @param rxNetworkConfig rxNetwork config
     * @param factory         customize converter factory
     * @return the implementation
     */
    public <T> T createSingleApi(String baseUrl, Class<T> cls, RxNetworkConfig rxNetworkConfig, Converter.Factory... factory) {
        SingleRetrofit singleRetrofit = new SingleRetrofit();
        return singleRetrofit.getRetrofit(baseUrl, rxNetworkConfig, factory).create(cls);
    }

    /**
     * Create an implementation of the API by single network config
     *
     * @param baseUrl      base url
     * @param cls          API
     * @param okHttpClient okHttpClient
     * @return the implementation
     */
    public <T> T createSingleApi(String baseUrl, Class<T> cls, OkHttpClient okHttpClient) {
        SingleRetrofit singleRetrofit = new SingleRetrofit();
        return singleRetrofit.getRetrofit(baseUrl, okHttpClient).create(cls);
    }

    /**
     * Create an implementation of the API by single network config
     *
     * @param baseUrl      base url
     * @param cls          API
     * @param okHttpClient okHttpClient
     * @param factory      customize converter factory
     * @return the implementation
     */
    public <T> T createSingleApi(String baseUrl, Class<T> cls, OkHttpClient okHttpClient, Converter.Factory... factory) {
        SingleRetrofit singleRetrofit = new SingleRetrofit();
        return singleRetrofit.getRetrofit(baseUrl, okHttpClient, factory).create(cls);
    }
}
