package com.markettwits.cloud_datasoruce.network.interceptor

import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

internal class AcceptInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Accept", "application/vnd.github.v3+json")
            .build()
        return chain.proceed(request)
    }
}