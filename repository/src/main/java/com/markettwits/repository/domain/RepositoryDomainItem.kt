package com.markettwits.repository.domain

import com.markettwits.repository.presentation.list.RepositoriesUiState

sealed interface RepositoryDomainItem {
    fun map(mapper : DomainToUiRepositoriesMapper) : RepositoriesUiState

    data class Success(private val name : String,
                       private val description : String,
                       private val language: String,
                       private val languageColor: String
    ) : RepositoryDomainItem {
        override fun map(mapper: DomainToUiRepositoriesMapper) : RepositoriesUiState {
            return mapper.map(name, description, language, languageColor)
        }
    }

    data class Error(private val message : Int, private val code : Int) : RepositoryDomainItem {
        override fun map(mapper: DomainToUiRepositoriesMapper) : RepositoriesUiState {
            return mapper.map(code, message)
        }
    }

}