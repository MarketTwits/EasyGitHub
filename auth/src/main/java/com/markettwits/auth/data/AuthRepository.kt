package com.markettwits.auth.data

import com.markettwits.auth.presentation.AuthUiState
import com.markettwits.cloud_datasoruce.GitHubCloudDataSource
import com.markettwits.core.storage.SharedPreferencesStorage

interface AuthRepository {
    suspend fun auth(token: String): AuthUiState
    suspend fun logOut()
    class Base(
        private val cache: SharedPreferencesStorage,
        private val cloudDataSource: GitHubCloudDataSource.Auth,
        private val mapper: CloudToDomainAuthMapper
    ) : AuthRepository {
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

        override suspend fun logOut() {
            cache.delete(AUTH_VALUE)
        }
    }
    companion object{
        private const val AUTH_VALUE = "USER_TOKEN"
        private const val AUTH_DEFAULT_VALUE = ""
    }
}