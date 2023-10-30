package com.markettwits.cloud_datasoruce.di

import android.content.Context
import androidx.work.WorkManager
import com.markettwits.cloud_datasoruce.GitHubCloudDataSource
import com.markettwits.cloud_datasoruce.core.HandleNetworkResult
import com.markettwits.cloud_datasoruce.core.HandleRequestCode
import com.markettwits.cloud_datasoruce.core.OkkHttpWrapper
import com.markettwits.cloud_datasoruce.core.RetrofitFactory
import com.markettwits.cloud_datasoruce.network.GitHubApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
internal class CloudDataSourceModule {
    @Provides
    fun provideGitHubService(client: OkHttpClient, json: Json): GitHubApiService {
        return RetrofitFactory
            .Base("https://api.github.com/", client, json)
            .create(GitHubApiService::class.java)
    }


    @Provides
    fun provideHandleAsync(): HandleNetworkResult {
        return HandleNetworkResult.Base(HandleRequestCode.Base())
    }

    @Provides
     fun provideWithOutTokenClient(): OkHttpClient {
        return OkkHttpWrapper.WithOutToken().client()
    }
    @Provides
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
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
