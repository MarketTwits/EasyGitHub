package com.markettwits.repository.data

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.markettwits.core.navigation.Navigation
import com.markettwits.repository.presentation.list.RepositoriesListViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
@HiltWorker
class RepositoryWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted params: WorkerParameters,
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        Log.d("mt05", "Worker#doWork")
        val repository =
            EntryPointAccessors.fromApplication(appContext, ProvideEntryPoint::class.java).repository()
        repository.fetchUserRepositories()
        return Result.success()
    }
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface ProvideEntryPoint {
        fun repository(): RepositoryRepository
    }
}