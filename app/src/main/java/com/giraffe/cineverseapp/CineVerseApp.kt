package com.giraffe.cineverseapp

import android.app.Application
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.giraffe.cineverseapp.di.databaseModule
import com.giraffe.series.di.seriesRemoteDataModule
import com.giraffe.cineverseapp.di.localDataSourceModule
import com.giraffe.cineverseapp.di.networkModule
import com.giraffe.cineverseapp.di.repositoryModule
import com.giraffe.cineverseapp.di.useCaseModule
import com.giraffe.cineverseapp.di.viewModelModule
import com.giraffe.cineverseapp.worker.CacheCleanupWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.factory.KoinWorkerFactory
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class CineVerseApp : Application() {//, Configuration.Provider {
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
        }/*
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "CacheCleanupWork",
            ExistingPeriodicWorkPolicy.KEEP,
            PeriodicWorkRequestBuilder<CacheCleanupWorker>(
                1, TimeUnit.HOURS
            ).build()
        )*/
    }
/*
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(KoinWorkerFactory())
            .build()*/
}