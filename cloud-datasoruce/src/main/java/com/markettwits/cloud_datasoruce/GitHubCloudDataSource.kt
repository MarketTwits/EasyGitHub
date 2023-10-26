package com.markettwits.cloud_datasoruce

import android.util.Log
import com.markettwits.cloud_datasoruce.core.HandleNetworkResult
import com.markettwits.cloud_datasoruce.core.NetworkResult
import com.markettwits.cloud_datasoruce.model.RepositoryCloud
import com.markettwits.cloud_datasoruce.network.GitHubApiService
import javax.inject.Inject

interface GitHubCloudDataSource {
    interface Auth : GitHubCloudDataSource {
        suspend fun signIn(token: String): NetworkResult<RepositoryCloud.Owner>
    }

    interface Repository : GitHubCloudDataSource {
        interface Single : Repository {
            suspend fun fetchRepository(
                token : String,
                ownerName: String,
                repositoryName: String
            ): NetworkResult<RepositoryCloud>

            suspend fun fetchRepositoryReadme(
                token : String,
                ownerName: String,
                repositoryName: String,
                branch: String
            ): NetworkResult<RepositoryCloud.Readme>
        }

        interface RepositoryList : Repository {
            suspend fun fetchRepositories(token : String): NetworkResult<List<RepositoryCloud>>
        }

        interface Mutable : Single, RepositoryList
    }
    interface Mutable : Auth, Repository.Mutable, GitHubCloudDataSource
    class Base @Inject constructor(
        private val service: GitHubApiService,
        private val handleAsync: HandleNetworkResult
    ) : Mutable {
        override suspend fun signIn(token: String) =
            handleAsync.tryRequest {
                service.auth("$HEADER_VALUE $token")
            }

        override suspend fun fetchRepository(token : String, ownerName: String, repositoryName: String) =
            handleAsync.tryRequest {
                service.fetchRepository("$HEADER_VALUE $token",ownerName, repositoryName)
            }

        override suspend fun fetchRepositoryReadme(
            token : String,
            ownerName: String,
            repositoryName: String,
            branch: String
        ) = handleAsync.tryRequest {
            service.fetchReadme("$HEADER_VALUE $token", ownerName, repositoryName)
        }

        override suspend fun fetchRepositories(token: String) = handleAsync.tryRequest {
            service.fetchRepositories("$HEADER_VALUE $token")
        }
    }
    private companion object{
        const val HEADER_VALUE = "Bearer"
    }
}
