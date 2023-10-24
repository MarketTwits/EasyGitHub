package com.markettwits.auth.presentation.validation

import com.markettwits.core.R

interface TokenValidationState {
    fun handle(state : TokenValidationStateHandle)
    abstract class Abstract(
        private val enabled: Boolean,
        private val message: Int,
        private val buttonTitle : Int,
        private val progress: Int
    ) : TokenValidationState{
        override fun handle(state: TokenValidationStateHandle) {
            state.handle(enabled, message, buttonTitle, progress)
        }
    }

    data class Empty(
        private val enabled: Boolean = false,
        private val message: Int = R.string.empty_input,
        private val buttonTitle : Int = R.string.btn_sign_in,
        private val progress: Int = GONE
    ) : Abstract(enabled, message, buttonTitle, progress) {
        override fun handle(state: TokenValidationStateHandle) {
            state.handle(enabled, message, buttonTitle, progress)
        }
    }

    data class Invalid(
        private val enabled: Boolean = false,
        private val title: Int = R.string.invalid_input,
        private val buttonTitle : Int = R.string.btn_sign_in,
        private val progress: Int = GONE
    ) : Abstract(enabled, title, buttonTitle, progress) {
        override fun handle(state: TokenValidationStateHandle) {
            state.handle(enabled, title, buttonTitle, progress)
        }
    }

    data class Valid(
        private val enabled: Boolean = true,
        private val message: Int = R.string.btn_sign_in,
        private val buttonTitle : Int = R.string.btn_sign_in,
        private val progress: Int = GONE
    ) : Abstract(enabled, message, buttonTitle,progress) {
        override fun handle(state: TokenValidationStateHandle) {
            state.handle(enabled, message,buttonTitle, progress)
        }
    }
    data class Loading(
        private val enabled: Boolean = false,
        private val message: Int = R.string.empty,
        private val buttonTitle : Int = R.string.empty,
        private val progress: Int = VISIBLE
    ) : Abstract(enabled, message, buttonTitle,progress) {
        override fun handle(state: TokenValidationStateHandle) {
            state.handle(enabled, message,buttonTitle, progress)
        }
    }

    private companion object {
        const val VISIBLE = 0
        const val GONE = 8
    }
}

interface TokenValidationStateHandle {
    fun handle(enabled: Boolean, message : Int, buttonTitle: Int, progress: Int)
}