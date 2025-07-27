//package com.giraffe.cineverseapp.worker
//
//import android.content.Context
//import androidx.hilt.work.HiltWorker
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import com.giraffe.media.explore.cleaner.KeywordsCacheCleaner
//import com.giraffe.media.person.cleaner.PersonCacheCleaner
//import com.giraffe.media.series.cleaner.SeriesCacheCleaner
//import com.giraffe.media.movie.cleaner.MovieCacheCleaner
//import dagger.assisted.Assisted
//import dagger.assisted.AssistedInject
//import javax.inject.Inject
//
//@HiltWorker
//class CacheCleanupWorker @AssistedInject constructor(
//    @Assisted appContext: Context,
//    @Assisted workerParams: WorkerParameters,
//    private val keywordsCleaner: KeywordsCacheCleaner,
//    private val personCacheCleaner: PersonCacheCleaner,
//    private val seriesCacheCleaner: SeriesCacheCleaner,
//    private val movieCacheCleaner: MovieCacheCleaner
//) : CoroutineWorker(appContext, workerParams) {
//
//    override suspend fun doWork(): Result {
//        return try {
//            keywordsCleaner.clearExpiredKeywordsCache()
//            personCacheCleaner.clearPersonCache()
//            seriesCacheCleaner.clearSeriesCache()
//            movieCacheCleaner.clearMovieCache()
//            Result.success()
//        } catch (_: Exception) {
//            Result.retry()
//        }
//    }
//}
