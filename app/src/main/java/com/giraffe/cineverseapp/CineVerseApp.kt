package com.giraffe.cineverseapp

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.giraffe.cineverseapp.di.dataModule
import com.giraffe.cineverseapp.di.localDataSourceModule
import com.giraffe.cineverseapp.di.networkModule
import com.giraffe.cineverseapp.di.repositoryModule
import com.giraffe.cineverseapp.di.useCaseModule
import com.giraffe.cineverseapp.worker.CacheCleanupWorker
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class CineVerseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CineVerseApp)
            modules(
                dataModule,
                localDataSourceModule,
                networkModule,
                repositoryModule,
                useCaseModule
            )
        }
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