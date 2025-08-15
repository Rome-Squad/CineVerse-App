package com.giraffe.media.series.retrofit

import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.series.datasource.remote.dto.SeriesDetailsDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import com.giraffe.media.util.RetrofitRequestBuilder
import javax.inject.Inject

class SeriesRemoteRetrofitDataSourceImp @Inject constructor(
    private val retrofitRequestBuilder: RetrofitRequestBuilder<SeriesApiServiceRetrofit>
) : SeriesRemoteDataSource {

    override suspend fun getSeriesByName(name: String, page: Int) =
        retrofitRequestBuilder.get { getSeriesByName(name, page) }.results

    override suspend fun getSeriesByGenre(genreId: Int, page: Int) =
        retrofitRequestBuilder.get {
            getSeriesByGenre(if (genreId == -1) "" else genreId.toString(), page)
        }.results

    override suspend fun getSeriesByGenreIds(
        genreIds: List<Int>,
        page: Int
    ) =
        retrofitRequestBuilder.get {
            discoverSeries(
                genreId = genreIds.joinToString(","),
                page = page
            )
        }.results

    override suspend fun getSeriesByKeywordsId(
        keywords: String,
        page: Int
    ) =
        retrofitRequestBuilder.get {
            discoverSeries(
                keywords = keywords,
                page = page
            )
        }.results

    override suspend fun getSeriesBySort(
        sortBy: String,
        page: Int
    ) =
        retrofitRequestBuilder.get {
            discoverSeries(
                sortBy = sortBy,
                page = page
            )
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

    override suspend fun getSeriesRecommendations(seriesId: Int, page: Int) =
        retrofitRequestBuilder.get { getSeriesRecommendations(seriesId, page) }.results

    override suspend fun getSeriesTrailerUrl(seriesId: Int): String {
        val results = retrofitRequestBuilder.get { getSeriesTrailerUrl(seriesId) }.results
        return results.firstOrNull { it.type == "Trailer" }?.key
            ?: results.firstOrNull()?.key.orEmpty()
    }

    override suspend fun getRatedSeries(
        accountId: Int
    ): List<SeriesDto> = retrofitRequestBuilder.get { getRatedSeries(accountId) }.results


    override suspend fun deleteSeriesRating(seriesId: Int) =
        retrofitRequestBuilder.delete { deleteSeriesRating(seriesId) }

    override suspend fun addRating(serisId: Int, request: RatingRequest) {
        retrofitRequestBuilder.post { rateSeries(serisId, request) }
    }

    override suspend fun getUserSeriesRating(seriesId: Int) =
        retrofitRequestBuilder.get { getUserSeriesRating(seriesId) }.getRating()
}