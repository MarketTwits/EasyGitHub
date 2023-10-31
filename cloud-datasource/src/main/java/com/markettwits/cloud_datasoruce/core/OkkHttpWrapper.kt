package com.markettwits.cloud_datasoruce.core

import com.markettwits.cloud_datasoruce.network.interceptor.AuthInterceptor
import com.markettwits.cloud_datasoruce.network.interceptor.LoginInterceptor
import okhttp3.OkHttpClient

interface OkkHttpWrapper {
    fun client() : OkHttpClient
    class Base(private val token : String) : OkkHttpWrapper{
        override fun client(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(token))
                .addInterceptor(LoginInterceptor())
                .build()
        }
    }
    class WithOutToken : OkkHttpWrapper {
        override fun client(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(LoginInterceptor())
                .build()
        }
    }
}