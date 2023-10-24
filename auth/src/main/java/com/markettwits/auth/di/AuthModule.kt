package com.markettwits.auth.di

import android.content.Context
import com.markettwits.auth.data.AuthRepository
import com.markettwits.auth.data.CloudToDomainAuthMapper
import com.markettwits.auth.data.cache.SecureSharedPreferences
import com.markettwits.auth.presentation.communication.AuthCommunication
import com.markettwits.auth.presentation.AuthUiState
import com.markettwits.auth.presentation.validation.HandleValidationToken
import com.markettwits.auth.presentation.communication.ValidationCommunication
import com.markettwits.cloud_datasoruce.GitHubCloudDataSource
import com.markettwits.cloud_datasoruce.di.CloudGitHubDataSource
import com.markettwits.core.storage.SharedPreferencesStorage
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ViewModelComponent::class)
@Module
class AuthModule {

    @Provides
    fun provideRepository(
        @ApplicationContext context: Context,
        @CloudGitHubDataSource cloudDataSource: GitHubCloudDataSource
    ): AuthRepository {
        return AuthRepository.Base(
            SharedPreferencesStorage.Base(
                SecureSharedPreferences.Base(context, "gb_01_stp").provideSecureSharedPreference()
            ),
            cloudDataSource as GitHubCloudDataSource.Auth,
            CloudToDomainAuthMapper()
        )
    }

    @Provides
    fun provideValidationCommunication() : ValidationCommunication {
        return ValidationCommunication.Base()
    }

    @Provides
    fun provideCommunication(): AuthCommunication {
        return AuthCommunication.Base()
    }

    @Provides
    fun provideAsyncViewModel(): AsyncViewModel<AuthUiState> {
        return AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base()))
    }
    @Provides
    fun provideValidationToken() : HandleValidationToken {
        return HandleValidationToken.Base()
    }

}