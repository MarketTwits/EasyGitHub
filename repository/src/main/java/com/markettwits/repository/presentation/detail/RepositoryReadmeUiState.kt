package com.markettwits.repository.presentation.detail

interface RepositoryReadmeUiState {
    fun show(stateHandle: RepositoryReadmeUiStateHandle)
    class Success(private val message : String) : RepositoryReadmeUiState {
        override fun show(stateHandle: RepositoryReadmeUiStateHandle) {
            stateHandle.success(message)
        }
    }

    class Empty(private val message : Int) : RepositoryReadmeUiState {
        override fun show(stateHandle: RepositoryReadmeUiStateHandle) {
            stateHandle.empty(message)
        }
    }

    object Loading : RepositoryReadmeUiState {
        override fun show(stateHandle: RepositoryReadmeUiStateHandle) {
            stateHandle.loading()
        }
    }
}
interface RepositoryReadmeUiStateHandle{
    fun loading()
    fun success(message: String)
    fun empty(message: Int)
}