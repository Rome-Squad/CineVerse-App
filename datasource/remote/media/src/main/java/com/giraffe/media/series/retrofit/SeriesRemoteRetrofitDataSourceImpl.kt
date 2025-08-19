package com.giraffe.media.series.retrofit

import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import com.giraffe.media.util.safeCallRemote
import javax.inject.Inject

class SeriesRemoteRetrofitDataSourceImpl @Inject constructor(
    private val seriesApiService: SeriesApiService,
) : SeriesRemoteDataSource {

    override suspend fun getSeriesByName(name: String, page: Int) =
        safeCallRemote { seriesApiService.getSeriesByName(name, page) }.results

    override suspend fun getSeriesByGenre(genreId: Int, page: Int): List<SeriesDto> {
        val id = if (genreId == -1) "" else genreId.toString()
        return safeCallRemote {
            seriesApiService.getSeriesByGenre(genreId = id, page = page)
        }.results
    }

    override suspend fun getSeriesByGenreIds(genreIds: List<Int>, page: Int): List<SeriesDto> {
        return safeCallRemote {
            seriesApiService.discoverSeries(
                genreId = genreIds.joinToString(","),
                page = page
            )
        }.results
    }

    override suspend fun getSeriesByKeywordsId(keywords: Int, page: Int): List<SeriesDto> {
        return safeCallRemote {
            seriesApiService.discoverSeries(
                keywords = keywords.toString(),
                page = page
            )
        }.results
    }

    override suspend fun getSeriesBySort(sortBy: String, page: Int): List<SeriesDto> {
        return safeCallRemote {
            seriesApiService.discoverSeries(
                sortBy = sortBy,
                page = page
            )
        }.results
    }


    override suspend fun getGenres() =
        safeCallRemote { seriesApiService.getGenres() }.genres

    override suspend fun getSeriesDetails(seriesId: Int) =
        safeCallRemote { seriesApiService.getSeriesDetails(seriesId) }

    override suspend fun getSeriesReviews(seriesId: Int, page: Int) =
        safeCallRemote { seriesApiService.getSeriesReviews(seriesId, page) }.results

    override suspend fun getPopularitySeries(page: Int) =
        safeCallRemote { seriesApiService.getPopularSeries(page) }.results

    override suspend fun getRecentlyReleasedSeries(page: Int) =
        safeCallRemote { seriesApiService.getRecentlyReleasedSeries(page) }.results


    override suspend fun getTopRatedSeries(page: Int) =
        safeCallRemote { seriesApiService.getTopRatedSeries(page) }.results

    override suspend fun getSeriesRecommendations(seriesId: Int, page: Int) =
        safeCallRemote { seriesApiService.getSeriesRecommendations(seriesId, page) }.results

    override suspend fun getSeriesTrailerUrl(seriesId: Int): String {
        val results = safeCallRemote { seriesApiService.getSeriesTrailerUrl(seriesId) }.results
        return results.firstOrNull { it.type == "Trailer" }?.key
            ?: results.firstOrNull()?.key.orEmpty()
    }

    override suspend fun getRatedSeries(accountId: Int) =
        safeCallRemote { seriesApiService.getRatedSeries(accountId) }.results

    override suspend fun deleteSeriesRating(seriesId: Int) =
        safeCallRemote { seriesApiService.deleteSeriesRating(seriesId) }

    override suspend fun addRating(serisId: Int, request: RatingRequest) =
        safeCallRemote { seriesApiService.rateSeries(serisId, request) }

    override suspend fun getUserSeriesRating(seriesId: Int) =
        safeCallRemote { seriesApiService.getUserSeriesRating(seriesId) }.getRating()
}