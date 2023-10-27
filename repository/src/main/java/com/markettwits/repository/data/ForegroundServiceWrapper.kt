package com.markettwits.repository.data

import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import javax.inject.Inject

interface ForegroundServiceWrapper {
    fun start()
    class Base @Inject constructor(
        private val workManager: WorkManager
    ) : ForegroundServiceWrapper {
        override fun start() {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val work = OneTimeWorkRequestBuilder<RepositoryWorker>()
                .setConstraints(constraints)
                .build()
            workManager.beginUniqueWork(
                WORK_NAME,
                ExistingWorkPolicy.KEEP,
                work
            ).enqueue()
        }
        private companion object{
            private const val WORK_NAME = "easy_git_hub_worker"
        }
    }

}