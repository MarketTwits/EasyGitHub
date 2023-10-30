package com.markettwits.repository.data

import com.markettwits.cloud_datasoruce.GitHubCloudDataSource
import com.markettwits.core.storage.AuthDataSource
import com.markettwits.repository.data.mapper.CloudToDomainReadmeMapper
import com.markettwits.repository.data.mapper.CloudToDomainRepositoriesMapper
import com.markettwits.repository.data.mapper.CloudToDomainRepositoryMapper
import com.markettwits.repository.domain.list.RepositoriesDomainItem
import com.markettwits.repository.domain.single.ReadmeDomain
import com.markettwits.repository.domain.single.RepositoryDomainItem
import javax.inject.Inject

interface RepositoryRepository {
    suspend fun fetchRepository(owner : String, repositoryName : String) : RepositoryDomainItem
    suspend fun fetchUserRepositoriesInternal() : List<RepositoriesDomainItem>
    suspend fun fetchRepositoryReadme(owner : String, repositoryName : String, branch : String) : ReadmeDomain
    suspend fun fetchUserRepositories()
    class Base @Inject constructor(
        private val auth: AuthDataSource,
        private val cloudGitHubDataSource: GitHubCloudDataSource.Repository.Mutable,
        private val foregroundServiceWrapper: ForegroundServiceWrapper.Base,
        private val mapper: CloudToDomainRepositoriesMapper,
        private val repositoryMapper: CloudToDomainRepositoryMapper,
        private val readmeMapper: CloudToDomainReadmeMapper
    ) : RepositoryRepository {
        override suspend fun fetchRepository(owner : String, repositoryName : String): RepositoryDomainItem {
            val result = cloudGitHubDataSource.fetchRepository(auth.value(), owner, repositoryName)
            return result.map(repositoryMapper)
        }

        override suspend fun fetchUserRepositoriesInternal(): List<RepositoriesDomainItem> {
            val result = cloudGitHubDataSource.fetchRepositories(auth.value())
            return result.map(mapper)
        }

        override suspend fun fetchRepositoryReadme(
            owner: String,
            repositoryName: String,
            branch: String
        ): ReadmeDomain {
            val result = cloudGitHubDataSource.fetchRepositoryReadme(auth.value(),owner,repositoryName, branch)
            return result.map(readmeMapper)
        }

        override suspend fun fetchUserRepositories() {
            foregroundServiceWrapper.start()
        }
    }
}