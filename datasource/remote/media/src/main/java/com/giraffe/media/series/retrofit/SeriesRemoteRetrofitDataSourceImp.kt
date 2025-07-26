package com.giraffe.media.series.retrofit

import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.series.datasource.remote.dto.SeriesDetailsDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import com.giraffe.media.util.RetrofitRequestBuilder

class SeriesRemoteRetrofitDataSourceImp(
    private val retrofitRequestBuilder: RetrofitRequestBuilder<SeriesApiServiceRetrofit>
) : SeriesRemoteDataSource {

    override suspend fun getSeriesByName(name: String, page: Int) =
        retrofitRequestBuilder.get { getSeriesByName(name, page) }.results

    override suspend fun getSeriesByGenre(genreId: Int, page: Int) =
        retrofitRequestBuilder.get {
            getSeriesByGenre(if (genreId == -1) "" else genreId.toString(), page)
        }.results

    override suspend fun getGenres() =
        retrofitRequestBuilder.get { getGenres() }.genres

    override suspend fun getSeriesDetails(seriesId: Int): SeriesDetailsDto =
        retrofitRequestBuilder.get { getSeriesDetails(seriesId) }

    override suspend fun getSeriesReviews(seriesId: Int, page: Int) =
        retrofitRequestBuilder.get { getSeriesReviews(seriesId, page) }.results

    override suspend fun getPopularitySeries(page: Int): List<SeriesDto> =
        retrofitRequestBuilder.get { getPopularSeries(page) }.results

    override suspend fun getRecentlyReleasedSeries(page: Int): List<SeriesDto> =
        retrofitRequestBuilder.get { getRecentlyReleasedSeries(page) }.results


    override suspend fun getTopRatedSeries(page: Int): List<SeriesDto> =
        retrofitRequestBuilder.get { getTopRatedSeries(page) }.results

    override suspend fun addRating(
        seriesId: Int,
        sessionId: String,
        request: RatingRequest
    ) {
        retrofitRequestBuilder.post { rateSeries(seriesId, sessionId, request) }
    }

    override suspend fun getSeriesRating(
        seriesId: Int,
        sessionId: String
    ): Float {
        return retrofitRequestBuilder.get { getSeriesRating(seriesId, sessionId) }
            .results.firstOrNull { it.id == seriesId }?.rating?.toFloat() ?: 0f
    }

    override suspend fun deleteSeriesRating(seriesId: Int, sessionId: String) =
        retrofitRequestBuilder.delete { deleteSeriesRating(seriesId, sessionId) }

    override suspend fun getSeriesRecommendations(seriesId: Long, page: Int) =
        retrofitRequestBuilder.get { getSeriesRecommendations(seriesId, page) }.results
}