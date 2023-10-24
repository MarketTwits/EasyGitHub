package com.markettwits.cloud_datasoruce.di

import com.markettwits.cloud_datasoruce.GitHubCloudDataSource
import com.markettwits.cloud_datasoruce.core.HandleNetworkResult
import com.markettwits.cloud_datasoruce.core.HandleRequestCode
import com.markettwits.cloud_datasoruce.core.RetrofitFactory
import com.markettwits.cloud_datasoruce.network.GitHubApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
internal class CloudDataSourceModule {
    @Provides
    fun provideGitHubService(): GitHubApiService {
        return RetrofitFactory
            .Base("https://api.github.com/")
            .create(GitHubApiService::class.java)
    }

    @Provides
    fun provideHandleAsync(): HandleNetworkResult {
        return HandleNetworkResult.Base(HandleRequestCode.Base())
    }
    @CloudGitHubDataSource
    @Provides
    fun provideCloudDataSource(
        service: GitHubApiService,
        async: HandleNetworkResult
    ): GitHubCloudDataSource {
        return GitHubCloudDataSource.Base(service, async)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CloudGitHubDataSource