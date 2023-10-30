package com.markettwits.easygithub.data

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe() : Flow<ConnectivityState>
}
interface ConnectivityState{
    object Available : ConnectivityState
    object Unavailable : ConnectivityState
}