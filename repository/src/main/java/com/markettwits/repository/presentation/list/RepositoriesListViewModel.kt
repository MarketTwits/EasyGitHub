package com.markettwits.repository.presentation.list

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.markettwits.cloud_datasoruce.core.AuthDataSource
import com.markettwits.core.communication.Communication
import com.markettwits.core.navigation.Navigation
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.repository.domain.RepositoryInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

interface RepositoriesListViewModel {
    fun repositories()
    fun singOut()

    @HiltViewModel
    class Base @Inject constructor(
        private val interactor: RepositoryInteractor,
        private val authRepository : AuthDataSource,
        private val async: AsyncViewModel<List<RepositoriesUiState>>,
        private val communication: RepositoriesCommunication,
        private val navigation: Navigation
    ) : ViewModel(), RepositoriesListViewModel, Communication.Observe<List<RepositoriesUiState>> {
        init {
           repositories()
        }
        override fun repositories() {
            communication.map(listOf(RepositoriesUiState.Loading()))
            async.handleAsync({
                interactor.repositories()
            }) { communication.map(it) }
        }

        override fun singOut() {
            navigation.navigateToAuth()
            async.handleAsync({
                authRepository.logOut()
            }){}
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<List<RepositoriesUiState>>) {
            communication.observe(owner, observer)
        }
    }
}

