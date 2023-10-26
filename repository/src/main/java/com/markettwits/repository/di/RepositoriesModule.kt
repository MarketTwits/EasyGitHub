package com.markettwits.repository.di

import com.markettwits.auth.di.AuthQualifier
import com.markettwits.cloud_datasoruce.GitHubCloudDataSource
import com.markettwits.cloud_datasoruce.core.AuthDataSource
import com.markettwits.cloud_datasoruce.di.CloudGitHubDataSource
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.repository.data.CloudToDomainRepositoryMapper
import com.markettwits.repository.data.RepositoryRepository
import com.markettwits.repository.domain.DomainToUiRepositoriesMapper
import com.markettwits.repository.domain.RepositoryInteractor
import com.markettwits.repository.presentation.list.RepositoriesCommunication
import com.markettwits.repository.presentation.list.RepositoriesUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoriesModule {
    @Provides
    fun provideRepositoryInteractor(repository: RepositoryRepository): RepositoryInteractor {
        return RepositoryInteractor.Base(
            repository,
            DomainToUiRepositoriesMapper.Base()
        )
    }

    @Provides
    fun provideRepository(
        @AuthQualifier auth: AuthDataSource,
        @CloudGitHubDataSource cloudDataSource: GitHubCloudDataSource
    ): RepositoryRepository {
        return RepositoryRepository.Base(
            auth,
            cloudDataSource as GitHubCloudDataSource.Mutable,
            CloudToDomainRepositoryMapper()
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