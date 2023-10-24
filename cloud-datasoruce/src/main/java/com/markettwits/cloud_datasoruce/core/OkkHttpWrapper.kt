package com.markettwits.cloud_datasoruce.core

import com.markettwits.cloud_datasoruce.network.interceptor.AuthInterceptor
import okhttp3.OkHttpClient

interface OkkHttpWrapper {
    fun client() : OkHttpClient
    class Base() : OkkHttpWrapper{
        override fun client(): OkHttpClient {
            return OkHttpClient.Builder()
               // .addInterceptor(AuthInterceptor())
                .build()
        }
    }
}