package com.markettwits.repository.presentation.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface RepositoryUiState : Parcelable {
    fun show(show: RepositoryUiStateHandle)
    @Parcelize
    class Success(
        private val name: String,
        private val htmlUrl: String,
        private val license: String,
        private val forks: Int = 0,
        private val stars: Int = 0,
        private val watchers: Int = 0,
    ) : RepositoryUiState, Parcelable {

        override fun show(show: RepositoryUiStateHandle) {
            show.success(name, htmlUrl, license, forks, stars, watchers)
        }
    }
    @Parcelize
    class Error(private val icon: Int, private val title: Int, private val message: Int) :
        RepositoryUiState {
        override fun show(show: RepositoryUiStateHandle) {
            show.error(icon, title, message)
        }
    }
    @Parcelize
    data object Loading : RepositoryUiState {
        override fun show(show: RepositoryUiStateHandle) {
            show.loading()
        }
    }
}

interface RepositoryUiStateHandle {

    fun success(
        name: String,
        htmlUrl: String,
        license: String,
        forks: Int,
        stars: Int,
        watchers: Int
    )

    fun loading()

    fun error(icon: Int, title: Int, message: Int)

}
