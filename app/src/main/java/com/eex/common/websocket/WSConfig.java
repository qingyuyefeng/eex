package com.eex.common.websocket;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public final class WSConfig {
    protected long reconnectInterval = 1;
    protected TimeUnit reconnectIntervalTimeUnit = TimeUnit.SECONDS;
    protected boolean showLog = false;
    protected String logTag = "RxWebSocket";
    protected OkHttpClient client = new OkHttpClient();
    protected SSLSocketFactory sslSocketFactory;
    protected X509TrustManager trustManager;

    private WSConfig() {
    }


    public static final class Builder {
        private WSConfig config;

        public Builder() {
            config = new WSConfig();
        }

        /**
         * set your client
         *
         * @param client
         */
        public Builder setClient(OkHttpClient client) {
            config.client = client;
            return this;
        }

        /**
         * wss support
         *
         * @param sslSocketFactory
         * @param trustManager
         */
        public Builder setSSLSocketFactory(SSLSocketFactory sslSocketFactory, X509TrustManager trustManager) {
            config.sslSocketFactory = sslSocketFactory;
            config.trustManager = trustManager;
            return this;
        }

        /**
         * set reconnect interval
         *
         * @param Interval reconncet interval
         * @param timeUnit unit
         * @return
         */
        public Builder setReconnectInterval(long Interval, TimeUnit timeUnit) {
            config.reconnectInterval = Interval;
            config.reconnectIntervalTimeUnit = timeUnit;
            return this;

        }

        public Builder setShowLog(boolean showLog) {
            config.showLog = showLog;
            return this;
        }

        public Builder setShowLog(boolean showLog, String logTag) {
            config.showLog = showLog;
            config.logTag = logTag;
            return this;
        }

        public WSConfig build() {
            return config;
        }
    }
}
