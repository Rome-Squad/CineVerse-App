package com.giraffe.cineverseapp.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.giraffe.media.search.usecase.ClearExpiredSearchHistoryUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SearchCacheCleanupWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val clearExpiredSearchHistoryUseCase: ClearExpiredSearchHistoryUseCase,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            clearExpiredSearchHistoryUseCase()
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}