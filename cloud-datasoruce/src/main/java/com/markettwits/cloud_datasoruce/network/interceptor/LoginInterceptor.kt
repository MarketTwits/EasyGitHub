package com.markettwits.cloud_datasoruce.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class LoginInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val loginInterceptor =  HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return loginInterceptor.intercept(chain)
    }
}