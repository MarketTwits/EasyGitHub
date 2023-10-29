package com.markettwits.repository.presentation.detail

sealed interface RepositoryUiState {
    fun show(show: RepositoryUiStateHandle)

    class Success(
        private val name: String,
        private val htmlUrl: String,
        private val license: Any,
        private val forks: Int = 0,
        private val stars: Int = 0,
        private val watchers: Int = 0,
    ) : RepositoryUiState {
        override fun show(show: RepositoryUiStateHandle) {
            show.success(name, htmlUrl, license.toString(), forks, stars, watchers)
        }
    }

    class Error(private val icon: Int, private val title: Int, private val message: Int) :
        RepositoryUiState {
        override fun show(show: RepositoryUiStateHandle) {
            show.error(icon, title, message)
        }
    }

    data object Loading : RepositoryUiState {
        override fun show(show: RepositoryUiStateHandle) {
            show.loading()
        }
    }
}

interface RepositoryUiStateHandle {

    fun success(name: String, htmlUrl: String, license: String, forks : Int, stars: Int, watchers: Int)

    fun loading()

    fun error(icon: Int, title: Int, message: Int)

}
