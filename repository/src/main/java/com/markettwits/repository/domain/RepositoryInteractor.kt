package com.markettwits.repository.domain

import com.markettwits.repository.data.RepositoryRepository
import com.markettwits.repository.domain.list.DomainToUiRepositoriesMapper
import com.markettwits.repository.domain.single.DomainToUiReadmeMapper
import com.markettwits.repository.domain.single.DomainToUiRepositoryMapper
import com.markettwits.repository.presentation.detail.RepositoryReadmeUiState
import com.markettwits.repository.presentation.detail.RepositoryUiState
import com.markettwits.repository.presentation.list.RepositoriesUiState
import javax.inject.Inject

interface RepositoryInteractor {
    suspend fun repositories() : List<RepositoriesUiState>
    suspend fun readMe(owner : String, repositoryName : String, branch : String) : RepositoryReadmeUiState
    suspend fun repository(owner : String, repositoryName : String) : RepositoryUiState
    class Base @Inject constructor(
        private val repository: RepositoryRepository,
        private val repositoriesMapper: DomainToUiRepositoriesMapper,
        private val repositoryMapper : DomainToUiRepositoryMapper,
        private val readmeMapper : DomainToUiReadmeMapper
    ) : RepositoryInteractor{
        override suspend  fun repositories(): List<RepositoriesUiState> {
            val result = repository.fetchUserRepositoriesInternal()
            val mapped = result.map {
               it.map(repositoriesMapper)
            }
            return mapped
        }

        override suspend fun readMe(owner: String, repositoryName: String, branch: String): RepositoryReadmeUiState {
            val result =  repository.fetchRepositoryReadme(owner, repositoryName, branch)
            return result.map(readmeMapper)
        }

        override suspend fun repository(owner: String, repositoryName: String): RepositoryUiState {
            val result = repository.fetchRepository(owner, repositoryName)
            return result.map(repositoryMapper)
        }
    }
}