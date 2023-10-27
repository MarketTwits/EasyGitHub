package com.markettwits.repository.di

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.markettwits.core.navigation.Navigation
import com.markettwits.repository.data.RepositoryRepository
import com.markettwits.repository.data.RepositoryWorker
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class RepositoryWorkerFactory @Inject constructor(private val repository: RepositoryRepository) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = RepositoryWorker(appContext, workerParameters)
}