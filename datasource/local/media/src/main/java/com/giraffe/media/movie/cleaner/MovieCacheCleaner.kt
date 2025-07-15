package  com.giraffe.media.movie.cleaner

interface MovieCacheCleaner {
    suspend fun clearMovieCache()
}