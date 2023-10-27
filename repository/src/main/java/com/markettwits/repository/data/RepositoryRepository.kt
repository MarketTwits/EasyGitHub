package com.markettwits.repository.data

import com.markettwits.cloud_datasoruce.GitHubCloudDataSource
import com.markettwits.cloud_datasoruce.core.AuthDataSource
import com.markettwits.repository.domain.RepositoryDomainItem
import javax.inject.Inject

interface RepositoryRepository {
    suspend fun fetchUserRepositoriesInternal() : List<RepositoryDomainItem>
    suspend fun fetchUserRepositories()
    class Base @Inject constructor(
        private val auth: AuthDataSource,
        private val cloudGitHubDataSource: GitHubCloudDataSource.Repository.Mutable,
        private val foregroundServiceWrapper: ForegroundServiceWrapper.Base,
        private val mapper: CloudToDomainRepositoryMapper
    ) : RepositoryRepository {
        override suspend fun fetchUserRepositoriesInternal(): List<RepositoryDomainItem> {
            val result = cloudGitHubDataSource.fetchRepositories(auth.value())
            return result.map(mapper)
        }

        override suspend fun fetchUserRepositories() {
            foregroundServiceWrapper.start()
        }
    }
}