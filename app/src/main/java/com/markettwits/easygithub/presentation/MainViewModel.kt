package com.markettwits.easygithub.presentation

import android.util.Log
import androidx.annotation.NavigationRes
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.markettwits.auth.data.AuthRepository
import com.markettwits.auth.presentation.AuthUiState
import com.markettwits.core.navigation.Navigation
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.easygithub.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

interface MainViewModel {
    fun initNavigation(
        @NavigationRes navGraph: Int,
        navHostController: NavController
    )

    @HiltViewModel
    class Base @Inject constructor(
        private val navigation: Navigation,
        private val authRepository: AuthRepository.Check,
        private val async: AsyncViewModel<AuthUiState>
    ) : ViewModel(), MainViewModel {

        override fun initNavigation(
            @NavigationRes navGraph: Int,
            navHostController: NavController
        ) {
            async.handleAsync({ authRepository.checkAvailabilityToken()}) {
                if (it)
                    navigation.init(navGraph, R.id.repositoriesListFragment, navHostController)
                else
                    navigation.init(navGraph, R.id.authFragment, navHostController)
            }
        }
    }
}