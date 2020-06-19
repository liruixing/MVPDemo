package com.st.network.http.config;

import java.util.List;

import okhttp3.Interceptor;

/**
 * create by Dennis
 * on 2019-12-24
 * description：
 **/
public class RxNetworkConfig {
    public final int mConnectTimeOut;
    public final int mReadTimeOut;
    public final int mWriteTimeOut;
    public final boolean mEnableCache;
    public final int mCacheSize;
    public final int mCacheMaxAge;
    public final boolean mEnableSSLSocket;
    public final String mCertificateName;
    public final List<Interceptor> mInterceptors;

    private RxNetworkConfig(Builder builder) {
        mConnectTimeOut = builder.mConnectTimeOut;
        mReadTimeOut = builder.mReadTimeOut;
        mWriteTimeOut = builder.mWriteTimeOut;
        mEnableCache = builder.mEnableCache;
        mCacheSize = builder.mCacheSize;
        mCacheMaxAge  = builder.mCacheMaxAge;
        mEnableSSLSocket = builder.mEnableSSLSocket;
        mCertificateName = builder.mCertificateName;
        mInterceptors = builder.mInterceptors;
    }

    public static RxNetworkConfig getDefaultConfig() {
        return new RxNetworkConfig.Builder()
                .setCache(false)
                .setEnableSSLSocket(false)
                .build();
    }

    /**
     * the helper for build the network configuration
     */
    public static class Builder {
        private int mConnectTimeOut = 10;
        private int mReadTimeOut = 10;
        private int mWriteTimeOut = 10;
        private boolean mEnableCache = false;
        private int mCacheSize = 5 * 1024 * 1024;
        private int mCacheMaxAge = 5 * 60;
        private boolean mEnableSSLSocket = false;
        private String mCertificateName = null;
        private List<Interceptor> mInterceptors = null;

        public Builder setConnectTimeOut(int connectTimeOut) {
            mConnectTimeOut = connectTimeOut;
            return this;
        }

        public Builder setReadTimeOut(int readTimeOut) {
            mReadTimeOut = readTimeOut;
            return this;
        }

        public Builder setWriteTimeOut(int writeTimeOut) {
            mWriteTimeOut = writeTimeOut;
            return this;
        }

        public Builder setCache(boolean enableCache) {
            mEnableCache = enableCache;
            return this;
        }

        public Builder setCacheConfig(int cacheSize, int cacheMaxAge) {
            mCacheSize = cacheSize;
            mCacheMaxAge = cacheMaxAge;
            return this;
        }

        public Builder setEnableSSLSocket(boolean enableSSLSocket) {
            mEnableSSLSocket = enableSSLSocket;
            return this;
        }

        public Builder setCertificateName(String certificateName) {
            mCertificateName = certificateName;
            return this;
        }

        public Builder setInterceptors(List<Interceptor> interceptors) {
            mInterceptors = interceptors;
            return this;
        }

        public RxNetworkConfig build() {
            return new RxNetworkConfig(this);
        }
    }
}
