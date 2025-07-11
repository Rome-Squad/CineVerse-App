package com.giraffe.cineverseapp.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.giraffe.person.cleaner.PersonCacheCleaner

class CacheCleanupWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val personCacheCleaner: PersonCacheCleaner
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            personCacheCleaner.clearPersonCache()
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}