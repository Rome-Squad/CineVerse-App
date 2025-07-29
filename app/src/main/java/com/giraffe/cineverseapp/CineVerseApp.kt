package com.giraffe.cineverseapp

import android.app.Application
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.giraffe.cineverseapp.data.util.SessionProviderImpl
import com.giraffe.cineverseapp.di.databaseModule
import com.giraffe.cineverseapp.di.featureApiModule
import com.giraffe.cineverseapp.di.localDataSourceModule
import com.giraffe.cineverseapp.di.networkModule
import com.giraffe.cineverseapp.di.repositoryModule
import com.giraffe.cineverseapp.di.useCaseModule
import com.giraffe.cineverseapp.di.viewModelModule
import com.giraffe.cineverseapp.worker.CacheCleanupWorker
import com.giraffe.imageviewer.di.imageViewerModule
import com.giraffe.user.datastore.AuthenticationDatastore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class CineVerseApp : Application() {

    val sessionProvider: SessionProviderImpl by inject()
    val authenticationDatastore: AuthenticationDatastore by inject()


    val coroutineScope = CoroutineScope(Dispatchers.IO)

    val sessionIdFlow: StateFlow<String?> by lazy {
        authenticationDatastore
            .getSessionId()
            .catch { emit(null) } // handle exception instead of throwing
            .stateIn(
                scope = coroutineScope,
                started = SharingStarted.Eagerly,
                initialValue = null
            )
    }


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CineVerseApp)
            modules(
                databaseModule,
                localDataSourceModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
                imageViewerModule,
                featureApiModule,
                networkModule
            )
        }
        getSessionId()
        setupCacheCleanupWorker()
    }

    private fun getSessionId() {
        coroutineScope.launch {
            sessionIdFlow.collect { sessionId ->
                    Log.d("TAG", "Session id from flow: $sessionId")
                    sessionProvider.setSessionId(sessionId)
                    val session = sessionProvider.getSessionId()
                    Log.d("TAG", "Session id from session provider: $session")
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