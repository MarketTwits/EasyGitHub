package com.markettwits.cloud_datasoruce.core

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthDataSourceQualifier

interface AuthDataSource {
    suspend fun checkAvailabilityToken() : Boolean
    suspend fun value() : String
    suspend fun logOut()

}