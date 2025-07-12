package com.giraffe.cineverseapp.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.giraffe.person.cleaner.PersonCacheCleaner
import org.koin.core.component.KoinComponent
class CacheCleanupWorker(
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams), KoinComponent {
    private val personCacheCleaner: PersonCacheCleaner = getKoin().get()
    override suspend fun doWork(): Result {
        return try {
            personCacheCleaner.clearPersonCache()
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}
