package com.giraffe.media.series

import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.series.datasource.remote.dto.SeriesDetailsDto
import com.giraffe.media.util.RetrofitRequestBuilder

class SeriesRemoteRetrofitDataSourceImp(
    private val builder: RetrofitRequestBuilder<SeriesApiServiceRetrofit>
) : SeriesRemoteDataSource {

    override suspend fun getSeriesByName(name: String, page: Int) =
        builder.get { getSeriesByName(name, page) }.results

    override suspend fun getSeriesByGenre(genreId: Int, page: Int) =
        builder.get {
            getSeriesByGenre(if (genreId == -1) "" else genreId.toString())
        }.results

    override suspend fun getGenres() =
        builder.get { getGenres() }.genres

    override suspend fun getSeriesDetails(seriesId: Int): SeriesDetailsDto =
        builder.get { getSeriesDetails(seriesId) }

    override suspend fun getSeriesReviews(seriesId: Int, page: Int) =
        builder.get { getSeriesReviews(seriesId, page) }.results

    override suspend fun getSeriesRecommendations(seriesId: Long, page: Int) =
        builder.get { getSeriesRecommendations(seriesId, page) }.results
}
