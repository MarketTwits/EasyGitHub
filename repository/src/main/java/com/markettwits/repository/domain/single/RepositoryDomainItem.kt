package com.markettwits.repository.domain.single

import com.markettwits.repository.presentation.detail.RepositoryUiState

sealed interface RepositoryDomainItem {
    fun map(mapper: DomainToUiRepositoryMapper) : RepositoryUiState
    class Base(
        val id: Int,
        val name: String,
        val htmlUrl: String,
        val license: Any,
        val forks: Int = 0,
        val stars: Int = 0,
        val watchers: Int = 0,
        ) : RepositoryDomainItem {
        override fun map(mapper: DomainToUiRepositoryMapper): RepositoryUiState {
            return mapper.map(this)
        }
    }

    data class Error(private val message : Int, private val code : Int) : RepositoryDomainItem {
        override fun map(mapper: DomainToUiRepositoryMapper): RepositoryUiState {
            return mapper.map(code, message)
        }
    }
}