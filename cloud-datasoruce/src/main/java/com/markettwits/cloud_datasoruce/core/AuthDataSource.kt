package com.markettwits.cloud_datasoruce.core

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthDataSourceQualifier

interface AuthDataSource {
    suspend fun checkAvailabilityToken() : Boolean
    suspend fun value() : String
    suspend fun logOut()

}