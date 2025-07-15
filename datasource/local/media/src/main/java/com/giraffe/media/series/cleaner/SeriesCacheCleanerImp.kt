package com.giraffe.media.series.cleaner

import com.giraffe.media.series.database.SeriesDao


class SeriesCacheCleanerImp(private val dao: SeriesDao) : SeriesCacheCleaner {
    override suspend fun clearSeriesCache() {
        dao.clearSeriesCache(System.currentTimeMillis())
    }
}