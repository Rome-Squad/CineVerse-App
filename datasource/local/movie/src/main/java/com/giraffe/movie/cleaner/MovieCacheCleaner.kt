package com.giraffe.movie.cleaner

interface MovieCacheCleaner {
    suspend fun clearMovieCache()
}