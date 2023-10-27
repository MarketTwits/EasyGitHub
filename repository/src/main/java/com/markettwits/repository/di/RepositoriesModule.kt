package com.markettwits.repository.di

import android.content.Context
import androidx.work.WorkManager
import com.markettwits.auth.data.AuthRepository
import com.markettwits.auth.di.AuthQualifier
import com.markettwits.cloud_datasoruce.GitHubCloudDataSource
import com.markettwits.cloud_datasoruce.core.AuthDataSource
import com.markettwits.cloud_datasoruce.di.CloudGitHubDataSource
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.repository.data.AssetsDataSource
import com.markettwits.repository.data.CloudToDomainRepositoryMapper
import com.markettwits.repository.data.ForegroundServiceWrapper
import com.markettwits.repository.data.LanguageColorMapper
import com.markettwits.repository.data.RepositoryRepository
import com.markettwits.repository.domain.DomainToUiRepositoriesMapper
import com.markettwits.repository.domain.RepositoryInteractor
import com.markettwits.repository.presentation.list.RepositoriesCommunication
import com.markettwits.repository.presentation.list.RepositoriesUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class RepositoriesModule {
    @Provides
    fun provideDomainToUiRepositoriesMapper() : DomainToUiRepositoriesMapper{
        return DomainToUiRepositoriesMapper.Base()
    }
    @Provides
    fun provideRepositoryInteractor(repository: RepositoryRepository, mapper: DomainToUiRepositoriesMapper): RepositoryInteractor {
        return RepositoryInteractor.Base(
            repository,
            mapper
        )
    }
    @Provides
    fun provideCommunication(): RepositoriesCommunication {
        return RepositoriesCommunication.Base()
    }

    @Provides
    fun provideAsyncViewModel(): AsyncViewModel<List<RepositoriesUiState>> {
        return AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base()))
    }
}
@Module
@InstallIn(SingletonComponent::class)
class SingleRepositoryModule{
    @Singleton
    @Provides
    fun provideRepository(
        @AuthQualifier auth: AuthDataSource,
        @CloudGitHubDataSource cloudDataSource: GitHubCloudDataSource,
        languageColorMapper: LanguageColorMapper,
        service : ForegroundServiceWrapper.Base,
    ): RepositoryRepository {
        return RepositoryRepository.Base(
            auth,
            cloudDataSource as GitHubCloudDataSource.Repository.Mutable,
            service,
            CloudToDomainRepositoryMapper(languageColorMapper),
        )
    }
//    @Provides
//    fun provideWorkManager(@ApplicationContext context : Context) = WorkManager.getInstance(context)
    @Provides
    fun foregroundService(@ApplicationContext context: Context) : ForegroundServiceWrapper.Base{
        return ForegroundServiceWrapper.Base(WorkManager.getInstance(context))
    }
    @Provides
    fun provideAssistedDataSource(@ApplicationContext context: Context) : AssetsDataSource{
        return AssetsDataSource.Base(context)
    }

    @Provides
    fun provideLanguageColorMapper(assets : AssetsDataSource): LanguageColorMapper {
        return LanguageColorMapper.Base(assets)
    }

}
