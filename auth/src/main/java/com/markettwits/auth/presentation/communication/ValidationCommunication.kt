package com.markettwits.auth.presentation.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.markettwits.auth.presentation.validation.TokenValidationState
import com.markettwits.core.communication.Communication

interface ValidationCommunication : Communication.Mutable<TokenValidationState>{
    interface Observe{
        fun observeValidation(owner: LifecycleOwner, observer: Observer<TokenValidationState>)
    }
    class Base : Communication.Abstract<TokenValidationState>(), ValidationCommunication
}