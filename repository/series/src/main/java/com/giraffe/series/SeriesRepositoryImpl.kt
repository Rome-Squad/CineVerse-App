package com.giraffe.series

import com.giraffe.series.entity.Series
import com.giraffe.series.entity.SeriesGenre
import com.giraffe.series.repository.SeriesRepository

class SeriesRepositoryImpl(): SeriesRepository {
    override suspend fun searchSeriesByName(seriesName: String): List<Series> {
        TODO("Not yet implemented")
    }


    override suspend fun storeSeries(series: List<Series>) {
        TODO("Not yet implemented")
    }

    override suspend fun getSeriesGenres(): List<SeriesGenre> {
        TODO("Not yet implemented")
    }

}