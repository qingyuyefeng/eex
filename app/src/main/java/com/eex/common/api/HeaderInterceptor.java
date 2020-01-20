package com.eex.common.api;

import com.eex.common.util.RxBus;
import com.eex.home.bean.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSource;


/**
 * Created by Administrator on 2017/11/11.
 */
class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        //去掉外层的result

        Request request = chain.request()
                .newBuilder()
                .cacheControl(new CacheControl.Builder().maxStale(2, TimeUnit.HOURS).build()).build();

        Request.Builder builder = request.newBuilder();

        HashMap<String, String> params = new HashMap<>();
        builder.addHeader("user-agent", "Android");


        if (request.body() instanceof FormBody) {
            FormBody.Builder newFormBody = new FormBody.Builder();
            FormBody oldFormBody = (FormBody) request.body();
            for (int i = 0; i < oldFormBody.size(); i++) {
                newFormBody.addEncoded(oldFormBody.encodedName(i), oldFormBody.encodedValue(i));
                params.put(oldFormBody.encodedName(i), oldFormBody.encodedValue(i));
            }


            builder.method(request.method(), newFormBody.build());
        }

        List<Cookie> cookies = CookiesManager.getInstence().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.name();
                String cookieString = cookie.toString();
                if ("JSESSIONID".equals(name)) {
                    builder.addHeader("Set-Cookie", cookieString);
                }
            }
        }


        Response response = chain.proceed(builder.build());

        //这个是因为，如果请求下载链接的话，会导致无法获取response
        BufferedSource source = response.body().source();
        source.request(Long.MAX_VALUE);

        if (response.code() == 401 || response.code() == 403 || response.code() == 400) {
            //鉴权失败
            RxBus.getInstance().post(new User());
        }
        return response;
    }
}
