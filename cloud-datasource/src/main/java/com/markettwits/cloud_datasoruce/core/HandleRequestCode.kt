package com.markettwits.cloud_datasoruce.core

import android.util.Log
import com.markettwits.cloud_datasoruce.network.GitHubApiService
import com.markettwits.core.R
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface HandleRequestCode {
    fun map(exception: HttpException): NetworkResult<Nothing>
    fun map(exception: Exception): NetworkResult<Nothing>
    class Base : HandleRequestCode {
        override fun map(exception: HttpException): NetworkResult<Nothing> {
            return when (exception.code()) {
                GitHubApiService.WRONG_TOKEN_CODE -> {
                    NetworkResult.Error(
                        R.string.wrong_token,
                        code = GitHubApiService.WRONG_TOKEN_CODE
                    )
                }

                GitHubApiService.NO_RIGHTS_CODE -> {
                    NetworkResult.Error(R.string.no_rights, code = GitHubApiService.NO_RIGHTS_CODE)
                }

                else -> NetworkResult.Error(
                    R.string.unknown_error,
                    code = GitHubApiService.NOT_FOUND_CODE
                )
            }
        }

        override fun map(exception: Exception) =
            when (exception) {
                is HttpException -> {
                    Log.d("mt05", "HttpException")
                    Log.d("mt05", exception.message.toString())
                    map(exception)
                }
                is SerializationException -> {
                    Log.d("mt05", "SerializationException")
                    NetworkResult.Error(
                        R.string.unknown_error,
                        code = GitHubApiService.NO_INTERNET_CODE
                    )
                }

                is UnknownHostException -> {
                    Log.d("mt05", "UnknownHostException")
                    NetworkResult.Error(
                        R.string.network_error,
                        code = GitHubApiService.NO_INTERNET_CODE
                    )
                }

                is SocketTimeoutException -> {
                    Log.d("mt05", "SocketTimeoutException")
                    NetworkResult.Error(
                        R.string.network_error,
                        code = GitHubApiService.NO_INTERNET_CODE
                    )
                }

                else -> {
                    Log.d("mt05", "Unknown error ${exception.message.toString()}")
                    NetworkResult.Error(
                        R.string.unknown_error,
                        code = GitHubApiService.NOT_FOUND_CODE
                    )
                }
            }
    }
}
