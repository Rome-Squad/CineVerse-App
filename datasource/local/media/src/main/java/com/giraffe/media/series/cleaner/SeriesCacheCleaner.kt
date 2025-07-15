package com.giraffe.media.series.cleaner

interface SeriesCacheCleaner {
    suspend fun clearSeriesCache()
}