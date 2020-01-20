package com.eex.common.api;



import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.eex.WPConfig;
import com.eex.common.api.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import tech.linjiang.pandora.Pandora;

/**
 * Created by 空 on 2017/6/8 0008.
 */

public class ApiFactory {


    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(new LoggingInterceptor())
            .addInterceptor(new HeaderInterceptor())
            .addInterceptor(Pandora.get().getInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            //错误重联
            .retryOnConnectionFailure(true)
             .cookieJar(CookiesManager.getInstence())
            .dns(new ApiDns())
            .build();

    //主服务器
    private static ApiService apiService = new Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(ApiService.class);

    private static <T> T createApi(Class<T> clazz) {
        return  new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
                .create(clazz);
    }

    /**
     * 主服务器请求方法
     *
     * @return
     */

    public static ApiService getInstance() {
        return apiService;

    }

    public static <T> T getInstance(Class<T> clazz) {
        return createApi(clazz);
    }


    public static String getBaseUrl() {
        return WPConfig.INSTANCE.getBaseUrl();
    }



    // 饼状图
    private static ApiService coinCash = new Retrofit.Builder()
            .baseUrl(getCoinCashUrl())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(ApiService.class);



    /**
     * 主服务器请求方法
     *
     * @return
     */

    public static ApiService getCoinCash() {
        return coinCash;

    }


    public static String getCoinCashUrl() {
        return WPConfig.INSTANCE.getCoinCashUrl();
    }



}
