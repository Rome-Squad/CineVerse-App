package com.giraffe.media.series.retrofit

import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.series.datasource.remote.dto.SeriesDetailsDto
import com.giraffe.media.util.RetrofitRequestBuilder

class SeriesRemoteRetrofitDataSourceImp(
    private val RetrofitRequestBuilder: RetrofitRequestBuilder<SeriesApiServiceRetrofit>
) : SeriesRemoteDataSource {

    override suspend fun getSeriesByName(name: String, page: Int) =
        RetrofitRequestBuilder.get { getSeriesByName(name, page) }.results

    override suspend fun getSeriesByGenre(genreId: Int, page: Int) =
        RetrofitRequestBuilder.get {
            getSeriesByGenre(if (genreId == -1) "" else genreId.toString())
        }.results

    override suspend fun getGenres() =
        RetrofitRequestBuilder.get { getGenres() }.genres

    override suspend fun getSeriesDetails(seriesId: Int): SeriesDetailsDto =
        RetrofitRequestBuilder.get { getSeriesDetails(seriesId) }

    override suspend fun getSeriesReviews(seriesId: Int, page: Int) =
        RetrofitRequestBuilder.get { getSeriesReviews(seriesId, page) }.results

    override suspend fun addRating(
        seriesId: Int,
        sessionId: String,
        request: RatingRequest
    ) {
        RetrofitRequestBuilder.post { rateSeries(seriesId, sessionId, request) }
    }

    override suspend fun getSeriesRating(
        seriesId: Int,
        sessionId: String
    ): Float {
        return RetrofitRequestBuilder.get { getSeriesRating(seriesId, sessionId) }
            .results.firstOrNull { it.id == seriesId }?.rating?.toFloat() ?: 0f
    }

    override suspend fun getSeriesRecommendations(seriesId: Long, page: Int) =
        RetrofitRequestBuilder.get { getSeriesRecommendations(seriesId, page) }.results
}