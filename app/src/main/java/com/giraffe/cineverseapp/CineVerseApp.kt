package com.giraffe.cineverseapp

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.giraffe.cineverseapp.worker.CacheCleanupWorker
import com.giraffe.cineverseapp.worker.SearchCacheCleanupWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class CineVerseApp : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        setupCacheCleanupWorker()
        setupSearchCacheCleanupWorker()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.INFO)
            .setWorkerFactory(workerFactory)
            .build()

    private fun setupCacheCleanupWorker() {
        val workRequest = PeriodicWorkRequestBuilder<CacheCleanupWorker>(
            1, TimeUnit.DAYS
        ).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "CacheCleanupWork",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    private fun setupSearchCacheCleanupWorker() {
        val workRequest = PeriodicWorkRequestBuilder<SearchCacheCleanupWorker>(
            15, TimeUnit.MINUTES
        ).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "SearchCacheCleanupWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}