package com.markettwits.repository.di

import android.content.Context
import androidx.work.WorkManager
import com.markettwits.auth.di.AuthQualifier
import com.markettwits.cloud_datasoruce.GitHubCloudDataSource
import com.markettwits.cloud_datasoruce.core.AuthDataSource
import com.markettwits.cloud_datasoruce.di.CloudGitHubDataSource
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.repository.data.AssetsDataSource
import com.markettwits.repository.data.mapper.CloudToDomainRepositoriesMapper
import com.markettwits.repository.data.mapper.CloudToDomainRepositoryMapper
import com.markettwits.repository.data.ForegroundServiceWrapper
import com.markettwits.repository.data.mapper.LanguageColorMapper
import com.markettwits.repository.data.RepositoryRepository
import com.markettwits.repository.data.mapper.CloudToDomainReadmeMapper
import com.markettwits.repository.domain.list.DomainToUiRepositoriesMapper
import com.markettwits.repository.domain.RepositoryInteractor
import com.markettwits.repository.domain.single.DomainToUiReadmeMapper
import com.markettwits.repository.domain.single.DomainToUiRepositoryMapper
import com.markettwits.repository.domain.single.UTF8Parse
import com.markettwits.repository.presentation.detail.communication.ReadmeCommunication
import com.markettwits.repository.presentation.detail.communication.RepositoryCommunication
import com.markettwits.repository.presentation.detail.RepositoryUiState
import com.markettwits.repository.presentation.detail.communication.RetryCommunication
import com.markettwits.repository.presentation.list.RepositoriesCommunication
import com.markettwits.repository.presentation.list.RepositoriesUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class RepositoriesModule {
    @Provides
    fun provideDomainToUiRepositoriesMapper() : DomainToUiRepositoriesMapper {
        return DomainToUiRepositoriesMapper.Base()
    }
    @Provides
    fun provideDomainToUIReadmeMapper() : DomainToUiReadmeMapper{
        return DomainToUiReadmeMapper.Base(UTF8Parse.Base())
    }
    @Provides
    fun provideRepositoryInteractor(
        repository: RepositoryRepository,
        mapper: DomainToUiRepositoriesMapper,
        repositoryMapper: DomainToUiRepositoryMapper,
        readmeMapper: DomainToUiReadmeMapper
    ): RepositoryInteractor {
        return RepositoryInteractor.Base(
            repository,
            mapper,
            repositoryMapper,
            readmeMapper
        )
    }
    @Provides
    fun provideCommunication(): RepositoriesCommunication {
        return RepositoriesCommunication.Base()
    }
    @Provides
    fun provideDomainToUiRepositoryMapper() : DomainToUiRepositoryMapper{
        return DomainToUiRepositoryMapper.Base()
    }
    @Provides
    fun provideCloudToDomainRepositoryMapper() : CloudToDomainRepositoryMapper {
        return CloudToDomainRepositoryMapper()
    }

    @Provides
    fun provideAsyncViewModel(): AsyncViewModel<List<RepositoriesUiState>> {
        return AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base()))
    }
    @Provides
    fun provideAsyncViewModelSingle(): AsyncViewModel<RepositoryUiState> {
        return AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base()))
    }
    @Provides
    fun provideCommunicationSingle() : RepositoryCommunication {
        return RepositoryCommunication.Base()
    }
    @Provides
    fun provideReadmeCommunication() : ReadmeCommunication = ReadmeCommunication.Base()
    @Provides
    fun provideRetryCommunication() : RetryCommunication = RetryCommunication.Base()
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
            CloudToDomainRepositoriesMapper(languageColorMapper),
            CloudToDomainRepositoryMapper(),
            CloudToDomainReadmeMapper()
        )
    }
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
