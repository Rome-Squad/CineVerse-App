package com.giraffe.cineverseapp.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.giraffe.media.movie.usecase.ClearMoviesCacheUseCase
import com.giraffe.media.series.usecase.ClearSeriesCacheUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class CacheCleanupWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val clearMoviesCacheUseCase: ClearMoviesCacheUseCase,
    private val clearSeriesCacheUseCase: ClearSeriesCacheUseCase
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            clearMoviesCacheUseCase.clearExceptRecentlyViewed()
            clearSeriesCacheUseCase()
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}