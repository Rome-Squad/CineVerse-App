package com.giraffe.cineverseapp.worker

import android.content.Context
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.giraffe.cineverseapp.data.database.CineVerseDatabase
import com.giraffe.media.movie.cleaner.MovieCacheCleanerImp
import com.giraffe.media.person.cleaner.PersonCacheCleanerImp
import com.giraffe.media.series.cleaner.SeriesCacheCleanerImp


class CacheCleanupWorker constructor(
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {

    private val database =
        Room.databaseBuilder(appContext, CineVerseDatabase::class.java, "CineVerseDataBase").build()
    private val personCacheCleaner = PersonCacheCleanerImp(database.personDao())
    private val seriesCacheCleaner = SeriesCacheCleanerImp(database.seriesDao())
    private val movieCacheCleaner = MovieCacheCleanerImp(database.movieDao())

    override suspend fun doWork(): Result {
        return try {
            personCacheCleaner.clearPersonCache()
            seriesCacheCleaner.clearSeriesCache()
            movieCacheCleaner.clearMovieCache()
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}