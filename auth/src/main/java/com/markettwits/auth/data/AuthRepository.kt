package com.markettwits.auth.data

import com.markettwits.auth.presentation.AuthUiState
import com.markettwits.cloud_datasoruce.GitHubCloudDataSource
import com.markettwits.cloud_datasoruce.core.AuthDataSource
import com.markettwits.core.storage.SharedPreferencesStorage

interface AuthRepository : AuthDataSource {

    interface Check : AuthRepository{
        suspend fun checkToken() : AuthUiState
    }
    interface Auth : AuthRepository{
        suspend fun auth(token: String = AUTH_DEFAULT_VALUE): AuthUiState
    }
    interface All : Check, Auth, AuthRepository

    class Base(
        private val cache: SharedPreferencesStorage,
        private val cloudDataSource: GitHubCloudDataSource.Auth,
        private val mapper: CloudToDomainAuthMapper
    ) : All, AbstractLocal(cache) {
        override suspend fun checkToken(): AuthUiState {
            val token = cache.read(AUTH_VALUE, AUTH_DEFAULT_VALUE)
            val result = cloudDataSource.signIn(token)
            return result.map(mapper)
        }
        override suspend fun auth(token: String): AuthUiState {
            val localCache = cache.read(AUTH_VALUE, AUTH_DEFAULT_VALUE)
            return if (localCache.isEmpty()){
                val result = cloudDataSource.signIn(token)
                val mapped = result.map(mapper)
                if (mapped is AuthUiState.Success) {
                    cache.save(AUTH_VALUE, token)
                }
                mapped
            }else{
                AuthUiState.Success
            }
        }
    }
    abstract class AbstractLocal(private val cache: SharedPreferencesStorage) : AuthDataSource{
        override suspend fun logOut() {
            cache.delete(AUTH_VALUE)
        }
        override suspend fun checkAvailabilityToken(): Boolean {
            val token = cache.read(AUTH_VALUE, AUTH_DEFAULT_VALUE)
            return token.isNotEmpty()
        }
        override suspend fun value() =
            cache.read(AUTH_VALUE, AUTH_DEFAULT_VALUE)
    }
    class BaseLocal(private val cache: SharedPreferencesStorage) : AbstractLocal(cache)

    companion object{
        private const val AUTH_VALUE = "USER_TOKEN"
        private const val AUTH_DEFAULT_VALUE = ""
    }
}