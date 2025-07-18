package  com.giraffe.media.movie.cleaner

import com.giraffe.media.movie.dao.MovieDao

class MovieCacheCleanerImp(private val dao: MovieDao):MovieCacheCleaner {
    override suspend fun clearMovieCache() {
        dao.clearMovieCache(System.currentTimeMillis())
    }
}