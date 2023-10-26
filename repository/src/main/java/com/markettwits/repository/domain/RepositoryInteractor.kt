package com.markettwits.repository.domain

import com.markettwits.repository.data.RepositoryRepository
import com.markettwits.repository.presentation.list.RepositoriesUiState

interface RepositoryInteractor {
    suspend fun repositories() : List<RepositoriesUiState>
    class Base(
        private val repository: RepositoryRepository,
        private val mapper: DomainToUiRepositoriesMapper
    ) : RepositoryInteractor{
        override suspend  fun repositories(): List<RepositoriesUiState> {
            val result = repository.fetchUserRepositories()
            val mapped = result.map {
               it.map(mapper)
            }
            return mapped
        }
    }
}