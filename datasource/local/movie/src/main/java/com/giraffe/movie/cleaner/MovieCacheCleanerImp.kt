package com.giraffe.movie.cleaner

import MovieDao

class MovieCacheCleanerImp(private val dao: MovieDao):MovieCacheCleaner {
    override suspend fun clearMovieCache() {
        dao.clearMovieCache(System.currentTimeMillis())
    }
}