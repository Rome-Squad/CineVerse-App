package com.giraffe.media.series

import com.giraffe.media.series.api.BaseRequest
import com.giraffe.media.series.api.RequestBuilder
import com.giraffe.media.series.model.GenreDto
import com.giraffe.media.series.model.SeriesDto
import com.giraffe.media.series.model_dto.GenresResponse
import com.giraffe.media.series.model_dto.SeriesResponse
import com.google.common.truth.Truth.assertThat
import io.ktor.client.call.body
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test


class TmdbSeriesApiRemoteDataSourceTest {

    private lateinit var requestBuilder: RequestBuilder
    private lateinit var baseRequest: BaseRequest
    private lateinit var dataSource: TmdbSeriesApiRemoteDataSource

    @Before
    fun setUp() {
        requestBuilder = mockk()
        baseRequest = BaseRequest("https://Skaik-mo.api", "token")
        dataSource = TmdbSeriesApiRemoteDataSource(
            requestBuilder = requestBuilder,
            baseRequest = baseRequest
        )
    }

    @Test
    fun `getSeriesByName returns list of series`() = runTest {
        // Given
        val expectedSeries = listOf(
            createSeriesDto(
                id = 1,
                name = "Batman",
                overview = "Overview batman",
                posterPath = "/poster.jpg"
            )
        )
        val mockResponse = createSeriesResponse(seriesList = expectedSeries)
        coEvery { requestBuilder.request(any()).body<SeriesResponse>() } returns mockResponse

        // When
        val result = dataSource.getSeriesByName(name = "Batman", page = 1)

        // Then
        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { requestBuilder.request(any()) }
    }

    @Test
    fun `getSeriesByGenreId returns list of series`() = runTest {
        // Given
        val expectedSeries = listOf(
            createSeriesDto(
                id = 1,
                name = "Batman",
                overview = "Overview batman",
                posterPath = "/poster.jpg",
                genreIds = listOf(9)
            )
        )

        val mockResponse = createSeriesResponse(seriesList = expectedSeries)
        coEvery { requestBuilder.request(any()).body<SeriesResponse>() } returns mockResponse

        // When
        val result = dataSource.getSeriesByGenreId(genreId = 18)

        // Then
        assertThat(result).isEqualTo(expectedSeries)
        coVerify(exactly = 1) { requestBuilder.request(any()) }
    }

    @Test
    fun `getGenres returns list of genres`() = runTest {
        // Given
        val expectedGenres = listOf(GenreDto(1, "Drama"), GenreDto(2, "Comedy"))
        val mockResponse = GenresResponse(genres = expectedGenres)
        coEvery { requestBuilder.request(any()).body<GenresResponse>() } returns mockResponse

        // When
        val result = dataSource.getGenres()

        // Then
        assertThat(result).isEqualTo(expectedGenres)
        coVerify(exactly = 1) { requestBuilder.request(any()) }
    }

    @Test
    fun `getSeriesRecommendations returns list of series`() = runTest {
        // Given
        val seriesId = 1L
        val page = 1
        val expectedSeries = listOf(
            createSeriesDto(
                id = 1,
                name = "Batman",
                overview = "Overview batman",
                posterPath = "/poster.jpg"
            )
        )
        val mockResponse = createSeriesResponse(seriesList = expectedSeries)
        coEvery { requestBuilder.request(any()).body<SeriesResponse>() } returns mockResponse

        // When
        val result = dataSource.getSeriesRecommendations(seriesId, page)

        // Then
        assertThat(result).isEqualTo(expectedSeries)
    }

    private fun createSeriesDto(
        id: Int = 1,
        name: String = "Demo Series",
        voteCount: Int = 100,
        overview: String = "This is a demo overview.",
        popularity: Double = 9.8,
        originalName: String = "Demo Original Name",
        firstAirDate: String = "2020-01-01",
        posterPath: String? = "/demoPoster.jpg",
        backdropPath: String? = "/demoBackdrop.jpg",
        voteAverage: Double = 8.5,
        adult: Boolean = false,
        genreIds: List<Int> = listOf(18, 35),
        originCountry: List<String> = listOf("US"),
        originalLanguage: String = "en"
    ) = SeriesDto(
        id = id,
        name = name,
        voteCount = voteCount,
        overview = overview,
        popularity = popularity,
        originalName = originalName,
        firstAirDate = firstAirDate,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        adult = adult,
        genreIds = genreIds,
        originCountry = originCountry,
        originalLanguage = originalLanguage
    )

    private fun createSeriesResponse(seriesList: List<SeriesDto>): SeriesResponse = SeriesResponse(
        page = 1,
        results = seriesList,
        totalPages = 1,
        totalResults = 1
    )
}