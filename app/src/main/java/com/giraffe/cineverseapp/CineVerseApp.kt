package com.giraffe.cineverseapp

import android.app.Application
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.giraffe.cineverseapp.di.databaseModule
import com.giraffe.cineverseapp.di.localDataSourceModule
import com.giraffe.cineverseapp.di.networkModule
import com.giraffe.cineverseapp.di.repositoryModule
import com.giraffe.cineverseapp.di.useCaseModule
import com.giraffe.cineverseapp.di.viewModelModule
import com.giraffe.cineverseapp.worker.CacheCleanupWorker
import com.giraffe.media.series.di.seriesRemoteDataModule
import com.giraffe.user.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class CineVerseApp : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val sessionManager: SessionManager by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CineVerseApp)
            modules(
                databaseModule,
                localDataSourceModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                seriesRemoteDataModule,
                viewModelModule
            )
        }

        createGuestSessionIfNotExists()

        setupCacheCleanupWorker()
    }

    private fun createGuestSessionIfNotExists() {
        applicationScope.launch {
            //TODO ("store session id when authenticated")
            try {
                val newGuestSessionId = sessionManager.createGuestSessionId()
                    ?: throw Exception("Failed to create guest session")

            } catch (e: Exception) {
                Log.e("CineVerseApp", "Failed to create guest session", e)
            }

        }
    }

    private fun setupCacheCleanupWorker() {
        val workRequest = PeriodicWorkRequestBuilder<CacheCleanupWorker>(
            1, TimeUnit.HOURS
        ).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "CacheCleanupWork",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}