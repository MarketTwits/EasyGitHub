package com.markettwits.repository.presentation.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface RepositoryReadmeUiState : Parcelable {
    fun show(stateHandle: RepositoryReadmeUiStateHandle)
    @Parcelize
    class Success(private val message : String) : RepositoryReadmeUiState {
        override fun show(stateHandle: RepositoryReadmeUiStateHandle) {
            stateHandle.success(message)
        }
    }
    @Parcelize
    class Empty(private val message : Int) : RepositoryReadmeUiState {
        override fun show(stateHandle: RepositoryReadmeUiStateHandle) {
            stateHandle.empty(message)
        }
    }
    @Parcelize
    data object Loading : RepositoryReadmeUiState {
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