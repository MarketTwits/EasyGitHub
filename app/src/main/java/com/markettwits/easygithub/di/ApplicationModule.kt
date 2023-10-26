package com.markettwits.easygithub.di

import android.content.Context
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import com.markettwits.core.navigation.Navigation
import com.markettwits.easygithub.presentation.navigation.BaseNavigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApplicationModule {
    @Singleton
    @Provides
    fun provideNavigation() : Navigation = BaseNavigation()
}
