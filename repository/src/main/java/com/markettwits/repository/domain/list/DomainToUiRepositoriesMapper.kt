package com.markettwits.repository.domain.list

import com.markettwits.core.R
import com.markettwits.repository.presentation.list.RepositoriesUiState

interface DomainToUiRepositoriesMapper {
    fun map() : RepositoriesUiState
    fun map(name : String,owner : String, description : String, language: String, languageColor: String) : RepositoriesUiState
    fun map(code : Int, message : Int) : RepositoriesUiState
    class Base : DomainToUiRepositoriesMapper {
        override fun map() : RepositoriesUiState {
            return RepositoriesUiState.Error(R.drawable.ic_empty, R.string.empty_error_label, R.string.empty_repositories_list)
        }

        override fun map(name: String,owner : String, description: String, language: String, languageColor: String) : RepositoriesUiState {
            return RepositoriesUiState.Success(name, owner, description, language, languageColor)
        }

        override fun map(code: Int, message : Int) : RepositoriesUiState {
            val icon = when(code){
                499 -> R.drawable.ic_network_error
                else -> R.drawable.ic_something_error
            }
            return RepositoriesUiState.Error(icon, message, message)
        }
    }
}
