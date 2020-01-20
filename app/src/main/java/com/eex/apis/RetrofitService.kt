package com.eex.apis

import com.landscape.mocknetapi.api.MockApi
import com.eex.Config
import retrofit2.Retrofit

import javax.inject.Inject

class RetrofitService @Inject
constructor() {

  @Inject
  lateinit var retrofit: Retrofit

  @Inject
  lateinit var mockApi: MockApi

  fun <T> createApi(clazz: Class<T>): T {
    return if (Config.MOCK) mockApi.create(clazz) else retrofit.create(clazz)
  }
}
