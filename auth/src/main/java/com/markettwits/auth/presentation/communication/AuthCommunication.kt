package com.markettwits.auth.presentation.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.markettwits.auth.presentation.AuthUiState
import com.markettwits.core.communication.Communication

interface AuthCommunication : Communication.Mutable<AuthUiState> {
    interface Observe{
        fun observeAuth(owner: LifecycleOwner, observer: Observer<AuthUiState>) = Unit
    }
    class Base : Communication.Abstract<AuthUiState>(), AuthCommunication
}
