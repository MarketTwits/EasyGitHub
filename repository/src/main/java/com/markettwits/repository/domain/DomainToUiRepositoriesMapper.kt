package com.markettwits.repository.domain

import com.markettwits.core.R
import com.markettwits.repository.presentation.list.RepositoriesUiState

interface DomainToUiRepositoriesMapper {
    fun map(name : String,description : String, language: String, languageColor: String) : RepositoriesUiState
    fun map(code : Int, message : Int) : RepositoriesUiState
    class Base : DomainToUiRepositoriesMapper {
        override fun map(name: String, description: String, language: String, languageColor: String) : RepositoriesUiState {
            return RepositoriesUiState.Success(name, description, language, languageColor)
        }

        override fun map(code: Int, message : Int ) : RepositoriesUiState {
            val icon = when(code){
                499 -> R.drawable.ic_network_error
                else -> R.drawable.ic_something_error
            }
            return RepositoriesUiState.Error(icon, message, message)
        }
    }
}
