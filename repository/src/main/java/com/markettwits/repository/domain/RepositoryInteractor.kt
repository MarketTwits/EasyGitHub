package com.markettwits.repository.domain

import com.markettwits.repository.data.RepositoryRepository
import com.markettwits.repository.presentation.list.RepositoriesUiState
import javax.inject.Inject

interface RepositoryInteractor {
    suspend fun repositories() : List<RepositoriesUiState>
    class Base @Inject constructor(
        private val repository: RepositoryRepository,
        private val mapper: DomainToUiRepositoriesMapper
    ) : RepositoryInteractor{
        override suspend  fun repositories(): List<RepositoriesUiState> {
            repository.fetchUserRepositories()
            val result = repository.fetchUserRepositoriesInternal()
            val mapped = result.map {
               it.map(mapper)
            }
            return mapped
        }
    }
}