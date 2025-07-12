package com.giraffe.series.cleaner

import com.giraffe.series.database.SeriesDao


class SeriesCacheCleanerImp(private val dao: SeriesDao) : SeriesCacheCleaner {
    override suspend fun clearSeriesCache() {
        dao.clearSeriesCache(System.currentTimeMillis())
    }
}