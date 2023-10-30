package com.markettwits.menu.ui

import androidx.lifecycle.ViewModel
import com.markettwits.auth.data.AuthRepository
import com.markettwits.core.navigation.Navigation
import com.markettwits.core.wrappers.AsyncViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

interface ToolBarViewModel {
    fun goBack()
    fun signOut()
    @HiltViewModel
    class Base @Inject constructor(
        private val navigation: Navigation,
        private val authRepository: AuthRepository,
        private val async: AsyncViewModel<Unit>
    ) : ViewModel(), ToolBarViewModel {
        override fun goBack() {
            navigation.back()
        }

        override fun signOut() {
            navigation.navigateToAuth()
            async.handleAsync({
                authRepository.logOut()
            }){}
        }
    }
}