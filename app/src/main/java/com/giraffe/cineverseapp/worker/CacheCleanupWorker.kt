package com.giraffe.cineverseapp.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.giraffe.media.movies.usecase.ClearMoviesCacheUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class CacheCleanupWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val clearMoviesCacheUseCase: ClearMoviesCacheUseCase
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            clearMoviesCacheUseCase.clearMovieCacheWithOutRecentViewed()
            Result.success()
        } catch (exception: Exception) {
            Result.retry()
        }
    }
}