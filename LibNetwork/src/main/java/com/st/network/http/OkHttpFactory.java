package com.st.network.http;


import com.st.network.http.config.RxNetwork;
import com.st.network.http.config.RxNetworkConfig;
import com.st.network.utils.LogUtil;
import com.st.network.utils.SSLUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * create by Dennis
 * on 2019/12/24
 * description：
 **/
public class OkHttpFactory {

    private OkHttpFactory() {
    }

    public static OkHttpClient getOkHttpClient(RxNetworkConfig rxNetworkConfig) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //cache
        if (rxNetworkConfig.mEnableCache) {
            File httpCacheDirectory = new File(RxNetwork.getInstance().getContext().getCacheDir(), "response");
            int cacheSize = rxNetworkConfig.mCacheSize;
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            CacheInterceptor cacheInterceptor = new CacheInterceptor();
            cacheInterceptor.setMaxAge(rxNetworkConfig.mCacheMaxAge);
            builder.addNetworkInterceptor(cacheInterceptor)
                    .addInterceptor(cacheInterceptor)
                    .cache(cache);
        }
        //debug
        if (RxNetwork.getInstance().isLog()) {
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//            builder.addInterceptor(logging);
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addNetworkInterceptor(loggingInterceptor);
        }
        //timeout
        builder.retryOnConnectionFailure(true)
                .connectTimeout(rxNetworkConfig.mConnectTimeOut, TimeUnit.SECONDS)
                .writeTimeout(rxNetworkConfig.mReadTimeOut, TimeUnit.SECONDS)
                .readTimeout(rxNetworkConfig.mWriteTimeOut, TimeUnit.SECONDS);
        //ssl
        if (rxNetworkConfig.mEnableSSLSocket) {
            X509TrustManager trustManager;
            SSLSocketFactory sslSocketFactory;
            InputStream inputStream;
            try {
                if (rxNetworkConfig.mCertificateName == null) {
                    throw new IllegalStateException("Must be set certificate name when open ssl socket");
                }
                inputStream = RxNetwork.getInstance().getContext().getAssets().open(rxNetworkConfig.mCertificateName);
                trustManager = SSLUtil.trustManagerForCertificates(inputStream);
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{trustManager}, null);
                sslSocketFactory = sslContext.getSocketFactory();
                builder.sslSocketFactory(sslSocketFactory, trustManager);
            } catch (IOException e) {
                LogUtil.e("IOException"+e);
            } catch (GeneralSecurityException e) {
                LogUtil.e("GeneralSecurityException"+e);
            }
        }

        if (rxNetworkConfig.mInterceptors != null) {
            for (Interceptor interceptor : rxNetworkConfig.mInterceptors) {

                builder.addInterceptor(interceptor);
            }
        }

        return builder.build();
    }

}
