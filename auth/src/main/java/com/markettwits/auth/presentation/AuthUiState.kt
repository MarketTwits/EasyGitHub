package com.markettwits.auth.presentation

import com.markettwits.auth.presentation.validation.TokenValidationState
import com.markettwits.core.R

sealed interface AuthUiState {
    fun handle(map : AuthUiStateHandle.Loading) = Unit
    fun handle(map: AuthUiStateHandle.Response) = Unit
    data object Loading : AuthUiState {
        override fun handle(map: AuthUiStateHandle.Loading) {
            map.loading()
        }
    }
    data object Empty : AuthUiState

    data object Success : AuthUiState {
        override fun handle(map: AuthUiStateHandle.Response) {
            map.success()
        }
    }
    class Error(private val message: Int) : AuthUiState {
        override fun handle(map: AuthUiStateHandle.Response) {
            map.error(message)
        }

    }
}
interface AuthUiStateHandle{
    interface Response{
        fun success() = Unit
        fun error(message: Int) = Unit
    }
    interface Loading : AuthUiStateHandle{
        fun loading() = Unit
    }
}