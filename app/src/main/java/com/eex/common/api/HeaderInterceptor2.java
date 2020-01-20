package com.eex.common.api;

import com.eex.common.util.RxBus;
import com.eex.home.bean.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\ = /O
 * ____/`---'\____
 * . ' \\| |// `.
 * / \\||| : |||// \
 * / _||||| -:- |||||- \
 * | | \\\ - /// | |
 * | \_| ''\---/'' | |
 * \ .-\__ `-` ___/-. /
 * ___`. .' /--.--\ `. . __
 * ."" '< `.___\_<|>_/___.' >'"".
 * | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * <p>
 * .............................................
 * 佛祖保佑 永无BUG
 *
 * @ProjectName: OverThrow
 * @Package: com.overthrow.common.api
 * @ClassName: HeaderInterceptor2
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/12 20:41
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/12 20:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HeaderInterceptor2 implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        //去掉外层的result

        Request request = chain.request();
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

        if (response.code() == 401 || response.code() == 403 || response.code() == 400) {
            //鉴权失败
            RxBus.getInstance().post(new User());
        }
        return response;
    }
}
