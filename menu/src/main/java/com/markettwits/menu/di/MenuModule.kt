package com.markettwits.menu.di

import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MenuModule {
    @Provides
    fun provideAsyncViewModel(): AsyncViewModel<Unit> {
        return AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base()))
    }
}