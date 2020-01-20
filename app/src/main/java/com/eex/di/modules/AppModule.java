package com.eex.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.landscape.mocknetapi.api.MockApi;
import com.eex.ActivityStack;
import com.eex.Config;
import com.eex.EEXApp;
import com.eex.apis.converter.StringConverterFactory;
import com.eex.apis.interceptor.BasicParamInterceptor;
import com.eex.common.api.gson.GsonConverterFactory;
import com.eex.common.base.UserConstants;
import com.eex.constant.Constants;
import com.eex.repository.adapter.BigDecimalAdapter;
import com.squareup.moshi.Moshi;
import com.tencent.mmkv.MMKV;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import tech.linjiang.pandora.Pandora;
import timber.log.Timber;

@Module
public abstract class AppModule {

  @Singleton
  @Binds
  public abstract Context context(EEXApp application);

  /**
   * 实际网络请求类
   */
  @Provides
  @Singleton
  public static OkHttpClient provideOkHttpClient(BasicParamInterceptor paramInterceptor,
      SSLSocketFactory sslSocketFactory) {
    /** 手动创建一个OkHttpClient并设置参数 */
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    /** 设置请求时间 */
    builder.connectTimeout(15, TimeUnit.SECONDS);
    builder.readTimeout(20, TimeUnit.SECONDS);
    builder.writeTimeout(20, TimeUnit.SECONDS);
    /** 设置忽略https安全证书验证 */
    builder.hostnameVerifier((hostname, session) -> true);
    builder.sslSocketFactory(sslSocketFactory);
    /** 添加日志拦截器 */
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> {
      //打印retrofit日志
      Timber.i(message);
    });
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    builder.addInterceptor(paramInterceptor);
    builder.addInterceptor(loggingInterceptor);
    builder.addInterceptor(Pandora.get().getInterceptor());
    return builder.build();
  }

  /**
   * 网络请求格式解析器
   */
  @Provides
  @Singleton
  public static Retrofit provideRetrofit(OkHttpClient okHttpClient, Moshi moshi) {
    return new Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(StringConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
        .baseUrl(Config.INSTANCE.getHOST_URL())
        .build();
  }

  @Provides
  @Singleton
  public static MockApi provideMockApi(Context context) {
    return MockApi.builder().context(context).build();
  }

  @Provides
  @Singleton
  public static Moshi provideMoshi() {
    return new Moshi.Builder()
        .add(new BigDecimalAdapter())
        .build();
  }

  /**
   * 信任https
   */
  @Provides
  @Singleton
  public static SSLSocketFactory provideSSLSocketFactory(Context context) {
    SSLContext sc = null;
    try {
      sc = SSLContext.getInstance("TLS");
      sc.init(null, new TrustManager[] {
          new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
              return new X509Certificate[] {};
            }
          }
      }, new SecureRandom());
    } catch (NoSuchAlgorithmException e1) {
      e1.printStackTrace();
    } catch (KeyManagementException e) {
      e.printStackTrace();
    }
    return sc.getSocketFactory();
  }

  @Provides
  @Singleton public static ActivityStack provideActivityStack() {
    return new ActivityStack();
  }

  @Provides
  @Singleton public static SharedPreferences provideSharedPreferences(Context context) {
    return context.getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);
  }

  @Provides
  @Singleton public static MMKV provideMMKV(Context context) {
    String dir = context.getFilesDir().getAbsolutePath() + "/mmkv_user";
    MMKV.initialize(dir);
    return MMKV.mmkvWithID(UserConstants.USER_DAO, MMKV.MULTI_PROCESS_MODE);
  }
}
