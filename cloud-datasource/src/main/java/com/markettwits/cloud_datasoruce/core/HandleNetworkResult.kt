package com.markettwits.cloud_datasoruce.core

import android.util.Log
import com.markettwits.cloud_datasoruce.model.RepositoryCloud
import com.markettwits.cloud_datasoruce.network.GitHubApiService
import com.markettwits.core.R

import com.markettwits.cloud_datasoruce.network.GitHubApiService.Companion.NO_RIGHTS_CODE
import com.markettwits.cloud_datasoruce.network.GitHubApiService.Companion.WRONG_TOKEN_CODE
import kotlinx.serialization.SerializationException
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException


interface HandleNetworkResult {
    suspend fun <T : Any> tryRequest(request: suspend () -> T): NetworkResult<T>
    class Base(
        private val handleRequestCode: HandleRequestCode,
    ) : HandleNetworkResult {
        override suspend fun <T : Any> tryRequest(request: suspend () -> T): NetworkResult<T> {
            return try {
                NetworkResult.Success(request.invoke())
            } catch (e: Exception) {
                handleRequestCode.map(e)
            }
        }
    }
}