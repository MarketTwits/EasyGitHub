package com.markettwits.repository.presentation.detail

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.markettwits.core.communication.Communication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.HandleDeath
import com.markettwits.repository.domain.RepositoryInteractor
import com.markettwits.repository.presentation.detail.communication.ReadmeCommunication
import com.markettwits.repository.presentation.detail.communication.RepositoryCommunication
import com.markettwits.repository.presentation.detail.communication.RetryCommunication
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


interface RepositoryInfoViewModel{
    fun restore(state: RepositoryUiState)
    fun restoreReadme(state: RepositoryReadmeUiState)
    fun currentState() : RepositoryUiState
    fun currentReadmeState() : RepositoryReadmeUiState
    fun init(firstRun : Boolean,repositoryName: String, owner: String)
    fun retry()
    fun observeRetry(owner: LifecycleOwner, observer: Observer<Unit>)
    fun observeReadme(owner: LifecycleOwner, observer: Observer<RepositoryReadmeUiState>)
    fun fetchRepositoryInfoInner(repositoryName : String, owner : String)
    fun fetchRepositoryReadmeInner(repositoryName : String, owner : String)
    fun fetchRepositoryInfo(repositoryName : String, owner : String)
    @HiltViewModel
    class Base @Inject constructor(
        private val interactor: RepositoryInteractor,
        private val communication: RepositoryCommunication,
        private val readmeCommunication: ReadmeCommunication,
        private val retryCommunication: RetryCommunication,
        private val async: AsyncViewModel<RepositoryUiState>,
        private val handleDeath: HandleDeath
    ) : ViewModel(), Communication.Observe<RepositoryUiState>, RepositoryInfoViewModel {

        override fun observe(owner: LifecycleOwner, observer: Observer<RepositoryUiState>) {
            communication.observe(owner, observer)
        }

        override fun restore(state: RepositoryUiState) {
            if (communication.fetch() == null){
                communication.map(state)
            }
        }

        override fun restoreReadme(state: RepositoryReadmeUiState) {
            if (readmeCommunication.fetch() == null){
                readmeCommunication.map(state)
            }
        }
        override fun currentState() = communication.fetch() ?: RepositoryUiState.Loading
        override fun currentReadmeState() = readmeCommunication.fetch() ?: RepositoryReadmeUiState.Loading
        override fun init(firstRun: Boolean,repositoryName: String, owner: String) {
            if (firstRun){
                handleDeath.firstOpening()
                fetchRepositoryInfo(repositoryName, owner)
            }
        }

        override fun retry() {
            retryCommunication.map(Unit)
        }

        override fun observeRetry(owner: LifecycleOwner, observer: Observer<Unit>) {
           retryCommunication.observe(owner, observer)
        }

        override fun observeReadme(owner: LifecycleOwner, observer: Observer<RepositoryReadmeUiState>) {
            readmeCommunication.observe(owner, observer)
        }

        override fun fetchRepositoryInfoInner(repositoryName : String, owner : String) {
            communication.map(RepositoryUiState.Loading)
            async.handleAsync({
                interactor.repository(owner, repositoryName)
            }){
                communication.map(it)
            }
        }

        override fun fetchRepositoryReadmeInner(repositoryName: String, owner: String) {
            readmeCommunication.map(RepositoryReadmeUiState.Loading)
            async.handleAsync({
                interactor.readMe(owner, repositoryName, DEFAULT_BRANCH)
            }){
                readmeCommunication.map(it)
            }
        }

        override fun fetchRepositoryInfo(repositoryName: String, owner: String) {
                fetchRepositoryInfoInner(repositoryName, owner)
                fetchRepositoryReadmeInner(repositoryName, owner)
        }
        private companion object{
            const val DEFAULT_BRANCH = "master"
        }
    }
}

