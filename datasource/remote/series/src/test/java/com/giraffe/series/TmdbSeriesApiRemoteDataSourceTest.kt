package com.giraffe.series

import com.giraffe.series.api.BaseRequest
import com.giraffe.series.api.RequestBuilder
import com.giraffe.series.model.GenreDto
import com.giraffe.series.model.SeriesDto
import com.giraffe.series.model_dto.GenresResponse
import com.giraffe.series.model_dto.SeriesResponse
import io.ktor.client.call.body
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals


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
        val result = dataSource.getSeriesByName(
            name = "Batman",
            language = "en-US",
            includeAdult = false,
            page = 1
        )

        // Then
        assertEquals(expectedSeries, result)
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
        assertEquals(expectedSeries, result)
        coVerify(exactly = 1) { requestBuilder.request(any()) }
    }

    @Test
    fun `getGenres returns list of genres`() = runTest {
        // Given
        val expectedGenres = listOf(GenreDto(1, "Drama"), GenreDto(2, "Comedy"))
        val mockResponse = GenresResponse(genres = expectedGenres)
        coEvery { requestBuilder.request(any()).body<GenresResponse>() } returns mockResponse

        // When
        val result = dataSource.getGenres("en")

        // Then
        assertEquals(expectedGenres, result)
        coVerify(exactly = 1) { requestBuilder.request(any()) }
    }

//    @Test
//    fun `getSeriesByName returns `() = runTest {
//        // Given
//        val name = "Batman"
//        val language = "en-US"
//        val includeAdult = false
//        val page = 1
//        val expectedSeriesList = listOf(
//            createSeriesDto(
//                id = 1,
//                name = "Batman",
//                overview = "Overview batman",
//                posterPath = "/poster.jpg"
//            )
//        )
//        val mockResponse = createSeriesResponse(seriesList = expectedSeriesList)
////        every { baseRequest.validate() } returns true
////        every { baseRequest.endpoint("search/tv") } returns baseRequest
////        every { baseRequest.method(HttpMethod.Get) } returns baseRequest
////        every { baseRequest.addParameter("include_adult", false) } returns baseRequest
////        every { baseRequest.addParameter("language", language) } returns baseRequest
////        every { baseRequest.addParameter("page", page) } returns baseRequest
////        every { baseRequest.addParameter("query", name) } returns baseRequest
//        coEvery { requestBuilder.request(baseRequest).body<SeriesResponse>() } returns mockResponse
//
//        // When
//        val result = dataSource.getSeriesByName(
//            name = name,
//            language = language,
//            includeAdult = includeAdult,
//            page = page
//        )
//
//        // Then
//        assertEquals(expectedSeriesList, result)
////        verify { baseRequest.validate() }
////        verify(exactly = 1) { baseRequest.endpoint("search/tv") }
////        verify(exactly = 1) { baseRequest.method(HttpMethod.Get) }
////        verify(exactly = 1) { baseRequest.addParameter("include_adult", false) }
////        verify(exactly = 1) { baseRequest.addParameter("language", language) }
////        verify(exactly = 1) { baseRequest.addParameter("page", page) }
////        verify(exactly = 1) { baseRequest.addParameter("query", name) }
////        coVerify(exactly = 1) { requestBuilder.request(baseRequest).body<SeriesResponse>() }
//    }

//    @Test
//    fun `getSeriesByName should return series list`() = runTest {
//        // Given
//        val seriesList = listOf(createSeriesDto(), createSeriesDto(id = 2, name = "Test 2"))
//        val response = createSeriesResponse(seriesList)
//
//        coEvery { requestBuilder.request<SeriesResponse>(any()) } returns response
//
//        // When
//        val result = dataSource.getSeriesByName(
//            name = "Breaking Bad",
//            language = "en",
//            includeAdult = false,
//            page = 1
//        )
//
//        // Then
//        assertEquals(seriesList, result)
//        coVerify {
//            baseRequest.endpoint("search/tv")
//            baseRequest.method(HttpMethod.Get)
//            baseRequest.addParameter("include_adult", false)
//            baseRequest.addParameter("language", "en")
//            baseRequest.addParameter("page", 1)
//            baseRequest.addParameter("query", "Breaking Bad")
//            requestBuilder.request<SeriesResponse>(baseRequest)
//        }
//    }
//
//    @Test
//    fun `getSeriesByGenreId should return series list`() = runTest {
//        // Given
//        val seriesList = listOf(createSeriesDto(), createSeriesDto(id = 2, name = "Test 2"))
//        val response = createSeriesResponse(seriesList)
//
//        coEvery { requestBuilder.request<SeriesResponse>(any()) } returns response
//
//        // When
//        val result = dataSource.getSeriesByGenreId(
//            genreId = 18,
//            language = "en",
//            includeAdult = true,
//            page = 2,
//            sortBy = "popularity.desc",
//            includeNullFirstAirDates = false
//        )
//
//        // Then
//        assertEquals(seriesList, result)
//        coVerify {
//            baseRequest.endpoint("discover/tv")
//            baseRequest.method(HttpMethod.Get)
//            baseRequest.addParameter("include_adult", true)
//            baseRequest.addParameter("language", "en")
//            baseRequest.addParameter("page", 2)
//            baseRequest.addParameter("with_genres", 18)
//            baseRequest.addParameter("include_null_first_air_dates", false)
//            requestBuilder.request<SeriesResponse>(baseRequest)
//        }
//    }
//
//    @Test
//    fun `getGenres should return genre list`() = runTest {
//        // Given
//        val genres = listOf(GenreDto(id = 1, name = "Drama"))
//        val response = GenresResponse(genres = genres)
//
//        coEvery { requestBuilder.request<GenresResponse>(any()) } returns response
//
//        // When
//        val result = dataSource.getGenres(language = "en")
//
//        // Then
//        assertEquals(genres, result)
//        coVerify {
//            baseRequest.endpoint("genre/tv/list")
//            baseRequest.method(HttpMethod.Get)
//            baseRequest.addParameter("language", "en")
//            requestBuilder.request<GenresResponse>(baseRequest)
//        }
//    }

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