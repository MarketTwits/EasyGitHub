package com.markettwits.cloud_datasoruce.network.interceptor


import com.markettwits.cloud_datasoruce.core.UserToken
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor(private val token: UserToken) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = token.value().let { value ->
            chain.request().newBuilder()
                .header("Authorization", "Bearer $value")
                .build()
        }
        return chain.proceed(request)
    }
}
