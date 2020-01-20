package com.eex.apis.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class BasicParamInterceptor @Inject
constructor() : Interceptor {

  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val requestBuilder = request.newBuilder()

    return chain.proceed(requestBuilder.build())
  }
}
