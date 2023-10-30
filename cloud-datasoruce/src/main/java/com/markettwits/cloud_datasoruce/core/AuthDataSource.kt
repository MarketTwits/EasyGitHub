package com.markettwits.cloud_datasoruce.core

import javax.inject.Qualifier


interface AuthDataSource {
    suspend fun checkAvailabilityToken() : Boolean
    suspend fun value() : String
    suspend fun logOut()

}