package com.markettwits.repository.presentation.list

sealed interface RepositoriesUiState {
    fun show(show: RepositoriesUiStateHandle)

    class Success(
        private val name: String,
        private val description: String,
        private val language: String
    ) : RepositoriesUiState {
        override fun show(show: RepositoriesUiStateHandle) {
            show.success(name, description, language)
        }
    }

    class Error(private val icon: Int, private val title: Int, private val message: Int) :
        RepositoriesUiState {
        override fun show(show: RepositoriesUiStateHandle) {
            show.error(icon, title, message)
        }
    }

    class Loading : RepositoriesUiState {
        override fun show(show: RepositoriesUiStateHandle) {
            show.loading()
        }
    }
}

interface RepositoriesUiStateHandle {

    fun success(name: String, description: String, language: String) = Unit

    fun loading() = Unit

    fun error(icon: Int, title: Int, message: Int) = Unit

}

sealed interface RepositoryUi {

}