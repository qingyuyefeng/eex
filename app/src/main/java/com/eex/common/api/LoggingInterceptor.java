package com.eex.common.api;

import android.support.annotation.NonNull;

import com.eex.WPConfig;
import com.eex.common.util.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by 空 on 2017/6/1 0001.
 */

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (WPConfig.isDebug) {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();
            long t1 = System.nanoTime();//请求发起的时间
            LogUtils.i("OKHttp", String.format("发送请求 %s %s on %s%n%s", request.method(), request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            LogUtils.i("OKHttp", "请求头:", request.headers());
            LogUtils.i("OKHttp", "参数:", request.body());
            long t2 = System.nanoTime();//收到响应的时间

            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            LogUtils.i("OKHttp", String.format("接收响应: %s  code:%s  %n返回json:{%s} %.1fms%n%s", response.request().url(), response.code() + "", responseBody.string(), (t2 - t1) / 1e6d, response.headers()) + "");

            return response;
        } else {
            return chain.proceed(chain.request());
        }
    }
}






















