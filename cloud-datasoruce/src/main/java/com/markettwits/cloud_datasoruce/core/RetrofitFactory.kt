package com.markettwits.cloud_datasoruce.core

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.markettwits.cloud_datasoruce.network.interceptor.AuthInterceptor
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

internal interface RetrofitFactory {
    fun <T : Any> create (service : Class<T>) : T
    class Base(
         private val baseUrl : String,
       // private val client: OkHttpClient
       // @Inject private val contentType : MediaType,
        //@Inject private val json: Json
    ) : RetrofitFactory {
        val client = OkkHttpWrapper.Base().client()
        val json = Json{
            ignoreUnknownKeys = true
        }
        override fun <T : Any> create(service: Class<T>) = Retrofit.Builder()
                .baseUrl(baseUrl)
            .client(client)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()
                .create(service)

    }
}