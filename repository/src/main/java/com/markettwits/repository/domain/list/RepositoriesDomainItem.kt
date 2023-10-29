package com.markettwits.repository.domain.list

import com.markettwits.repository.presentation.list.RepositoriesUiState

sealed interface RepositoriesDomainItem {
    fun map(mapper : DomainToUiRepositoriesMapper) : RepositoriesUiState

    data class Success(private val name : String,
                       private val owner : String,
                       private val description : String,
                       private val language: String,
                       private val languageColor: String
    ) : RepositoriesDomainItem {
        override fun map(mapper: DomainToUiRepositoriesMapper) : RepositoriesUiState {
            return mapper.map(name, owner,description, language, languageColor)
        }
    }

    data class Error(private val message : Int, private val code : Int) : RepositoriesDomainItem {
        override fun map(mapper: DomainToUiRepositoriesMapper) : RepositoriesUiState {
            return mapper.map(code, message)
        }
    }
    data object Empty : RepositoriesDomainItem {
        override fun map(mapper: DomainToUiRepositoriesMapper): RepositoriesUiState {
            return mapper.map()
        }
    }
}