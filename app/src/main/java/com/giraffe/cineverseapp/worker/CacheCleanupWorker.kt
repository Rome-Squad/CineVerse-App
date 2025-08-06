package com.giraffe.cineverseapp.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class CacheCleanupWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
//    private val clearMoviesCacheUseCase: ClearMoviesCacheUseCase
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("CacheCleanupWorker", "doWork: success")
        return try {
//            clearMoviesCacheUseCase.clearMovieCacheWithOutRecentViewed()
            Result.success()
        } catch (exception: Exception) {
            Log.e("CacheCleanupWorker", "doWork: retry", exception)
            Result.retry()
        }
    }
}

//@HiltWorker
//class CacheCleanupWorker @AssistedInject constructor(
//    @Assisted appContext: Context,
//    @Assisted workerParams: WorkerParameters,
//    private val clearMoviesCacheUseCase: ClearMoviesCacheUseCase
//) : CoroutineWorker(appContext, workerParams) {
//
////    private val database =
////        Room.databaseBuilder(appContext, CineVerseDatabase::class.java, "CineVerseDataBase").build()
////    private val personCacheCleaner = PersonCacheCleanerImp(database.personDao())
////    private val seriesCacheCleaner = SeriesCacheCleanerImp(database.seriesDao())
//
//    override suspend fun doWork(): Result {
//        Log.d("CacheCleanupWorker", "doWork: success")
//        return try {
////            personCacheCleaner.clearPersonCache()
////            seriesCacheCleaner.clearSeriesCache()
//            clearMoviesCacheUseCase.clearMovieCacheWithOutRecentViewed()
//            Result.success()
//        } catch (exception: Exception) {
//            Log.e("CacheCleanupWorker", "doWork: retry", exception)
//            Result.retry()
//        }
//    }
//}