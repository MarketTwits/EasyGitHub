package com.markettwits.auth.di

import android.content.Context
import com.markettwits.auth.data.AuthRepository
import com.markettwits.auth.data.CloudToDomainAuthMapper
import com.markettwits.auth.data.cache.SecureSharedPreferences
import com.markettwits.auth.di.AuthModule_ProvideRepositoryAuthFactory.provideRepositoryAuth
import com.markettwits.auth.di.AuthModule_ProvideRepositoryFactory.provideRepository
import com.markettwits.auth.presentation.communication.AuthCommunication
import com.markettwits.auth.presentation.AuthUiState
import com.markettwits.auth.presentation.validation.HandleValidationToken
import com.markettwits.auth.presentation.communication.ValidationCommunication
import com.markettwits.cloud_datasoruce.GitHubCloudDataSource
import com.markettwits.cloud_datasoruce.core.AuthDataSource
import com.markettwits.cloud_datasoruce.di.CloudGitHubDataSource
import com.markettwits.core.di.AuthQualifier
import com.markettwits.core.storage.SharedPreferencesStorage
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(ViewModelComponent::class)
@Module
class AuthModule {
    @Provides
    fun provideAuthService(
        @SecureSharedPref authCache: SharedPreferencesStorage,
        @CloudGitHubDataSource cloudDataSource: GitHubCloudDataSource
    ): AuthDataSource {
        return provideRepositoryAuth(authCache, cloudDataSource)
    }

    @Provides
    fun provideRepository(
        @SecureSharedPref authCache: SharedPreferencesStorage,
        @CloudGitHubDataSource cloudDataSource: GitHubCloudDataSource
    ): AuthRepository {
        return AuthRepository.Base(
            authCache,
            cloudDataSource as GitHubCloudDataSource.Auth,
            CloudToDomainAuthMapper()
        )
    }
    @Provides
    fun provideRepositoryAuth(
        @SecureSharedPref authCache: SharedPreferencesStorage,
        @CloudGitHubDataSource cloudDataSource: GitHubCloudDataSource
    ): AuthRepository.Auth {
        return provideRepository(authCache, cloudDataSource) as AuthRepository.Auth
    }

    @Provides
    fun provideRepositoryCheck(
        @SecureSharedPref authCache: SharedPreferencesStorage,
        @CloudGitHubDataSource cloudDataSource: GitHubCloudDataSource
    ): AuthRepository.Check {
        return provideRepository(authCache, cloudDataSource) as AuthRepository.Check
    }

    @Provides
    fun provideValidationCommunication(): ValidationCommunication {
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
    fun provideValidationToken(): HandleValidationToken {
        return HandleValidationToken.Base()
    }

}
@Module
@InstallIn(SingletonComponent::class)
class SingleAuthModule{
    @Singleton
    @Provides
    @AuthQualifier
    fun provideAuthDataSource(@SecureSharedPref cache : SharedPreferencesStorage) : AuthDataSource{
        return AuthRepository.BaseLocal(cache)
    }
    @Provides
    @SecureSharedPref
    fun provideAuthCache(@ApplicationContext context: Context): SharedPreferencesStorage {
        return SharedPreferencesStorage.Base(
            SecureSharedPreferences.Base(context, "gb_01_stp").provideSecureSharedPreference()
        )
    }
}