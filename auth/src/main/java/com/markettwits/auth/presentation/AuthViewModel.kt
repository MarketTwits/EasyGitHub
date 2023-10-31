package com.markettwits.auth.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.markettwits.auth.data.AuthRepository
import com.markettwits.auth.presentation.communication.AuthCommunication
import com.markettwits.auth.presentation.communication.ValidationCommunication
import com.markettwits.auth.presentation.validation.HandleValidationToken
import com.markettwits.auth.presentation.validation.TokenValidationState
import com.markettwits.core.navigation.Navigation
import com.markettwits.core.wrappers.AsyncViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


interface AuthViewModel : AuthCommunication.Observe, ValidationCommunication.Observe {
    fun dismiss()
    fun signIn(token: String)
    fun trySignIn(token: String)
    fun provide(state: AuthUiState)
    fun validate(value: String)
    fun navigateTo()

    @HiltViewModel
    class Base @Inject constructor(
        private val repository: AuthRepository.Auth,
        private val async: AsyncViewModel<AuthUiState>,
        private val handleValidationToken: HandleValidationToken,
        private val tokenCommunication: ValidationCommunication,
        private val authCommunication: AuthCommunication,
        private val navigation: Navigation
    ) : AuthViewModel, ViewModel() {

        override fun dismiss() {
            authCommunication.map(AuthUiState.Empty)
        }

        override fun signIn(token: String) {
            authCommunication.map(AuthUiState.Loading)
            async.handleAsync({ repository.auth(token) }) {
                authCommunication.map(it)
            }
        }

        override fun trySignIn(token: String) {
            validate(token)
            if (tokenCommunication.fetch() is TokenValidationState.Valid)
                signIn(token)
        }

        override fun provide(state: AuthUiState) {
            if (state is AuthUiState.Loading){
                tokenCommunication.map(TokenValidationState.Loading())
            }else{
                tokenCommunication.map(TokenValidationState.Valid())
            }
        }

        override fun validate(value: String) {
            tokenCommunication.map(handleValidationToken.handle(value))
        }

        override fun navigateTo() {
            navigation.navigateToRepositoryList()
        }

        override fun observeValidation(
            owner: LifecycleOwner,
            observer: Observer<TokenValidationState>
        ) {
            tokenCommunication.observe(owner, observer)
        }

        override fun observeAuth(owner: LifecycleOwner, observer: Observer<AuthUiState>) {
            authCommunication.observe(owner, observer)
        }
    }
}
