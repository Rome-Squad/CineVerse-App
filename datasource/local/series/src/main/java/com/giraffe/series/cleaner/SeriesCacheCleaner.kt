package com.giraffe.series.cleaner

interface SeriesCacheCleaner {
    suspend fun clearSeriesCache()
}