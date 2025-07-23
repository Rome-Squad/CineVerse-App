package com.giraffe.media.series.retrofit

import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.series.datasource.remote.dto.SeriesDetailsDto
import com.giraffe.media.util.RetrofitRequestBuilder

class SeriesRemoteRetrofitDataSourceImp(
    private val retrofitRequestBuilder: RetrofitRequestBuilder<SeriesApiServiceRetrofit>
) : SeriesRemoteDataSource {

    override suspend fun getSeriesByName(name: String, page: Int) =
        retrofitRequestBuilder.get { getSeriesByName(name, page) }.results

    override suspend fun getSeriesByGenre(genreId: Int, page: Int) =
        retrofitRequestBuilder.get {
            getSeriesByGenre(if (genreId == -1) "" else genreId.toString())
        }.results

    override suspend fun getGenres() =
        retrofitRequestBuilder.get { getGenres() }.genres

    override suspend fun getSeriesDetails(seriesId: Int): SeriesDetailsDto =
        retrofitRequestBuilder.get { getSeriesDetails(seriesId) }

    override suspend fun getSeriesReviews(seriesId: Int, page: Int) =
        retrofitRequestBuilder.get { getSeriesReviews(seriesId, page) }.results

    override suspend fun getSeriesRecommendations(seriesId: Long, page: Int) =
        retrofitRequestBuilder.get { getSeriesRecommendations(seriesId, page) }.results
}