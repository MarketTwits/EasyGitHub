package com.markettwits.repository.presentation.list

sealed interface RepositoriesUiState {
    fun show(show: RepositoriesUiStateHandle)

    class Success(
        private val name: String,
        private val owner : String,
        private val description: String,
        private val language: String,
        private val languageColor: String

    ) : RepositoriesUiState {
        override fun show(show: RepositoriesUiStateHandle) {
            show.success(name, owner,description, language, languageColor)
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

    fun success(name: String, owner : String, description: String, language: String,languageColor: String) = Unit

    fun loading() = Unit

    fun error(icon: Int, title: Int, message: Int) = Unit

}
