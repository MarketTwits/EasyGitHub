package com.markettwits.repository.data

import com.markettwits.auth.data.AuthRepository
import com.markettwits.cloud_datasoruce.GitHubCloudDataSource
import com.markettwits.cloud_datasoruce.core.AuthDataSource
import com.markettwits.cloud_datasoruce.model.RepositoryCloud
import com.markettwits.repository.domain.RepositoryDomainItem

interface RepositoryRepository {
    suspend fun fetchUserRepositories() : List<RepositoryDomainItem>
    class Base(
        private val auth: AuthDataSource,
        private val cloudGitHubDataSource: GitHubCloudDataSource.Repository.Mutable,
        private val mapper: CloudToDomainRepositoryMapper
    ) : RepositoryRepository {
        override suspend fun fetchUserRepositories(): List<RepositoryDomainItem> {
            val result = cloudGitHubDataSource.fetchRepositories(auth.value())
            return result.map(mapper)
        }
    }
}