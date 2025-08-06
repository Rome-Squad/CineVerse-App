package com.giraffe.media.movies.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class MovieUseCaseTest {

    private var addGenresUseCase: AddGenresUseCase = mockk()
    private var addMovieRatingUseCase: AddMovieRatingUseCase = mockk()
    private var addMoviesUseCase: AddMoviesUseCase = mockk()
    private var clearMovieCacheWithOutRecentViewedUseCase: ClearMovieCacheWithOutRecentViewedUseCase =
        mockk()
    private var clearMoviesCacheUseCase: ClearMoviesCacheUseCase = mockk()
    private var clearRecentlyMoviesUseCase: ClearRecentlyMoviesUseCase = mockk()
    private var getMovieDetailsUseCase: GetMovieDetailsUseCase = mockk()
    private var getMoviesGenresByIdsUseCase: GetMoviesGenresByIdsUseCase = mockk()
    private var getMovieReviewsUseCase: GetMovieReviewsUseCase = mockk()
    private var getMoviesByGenresUseCase: GetMoviesByGenresUseCase = mockk()
    private var getMoviesGenresUseCase: GetMoviesGenresUseCase = mockk()
    private var getPopularityMoviesUseCase: GetPopularityMoviesUseCase = mockk()
    private var getRecentlyReleasedMoviesUseCase: GetRecentlyReleasedMoviesUseCase = mockk()
    private var getRecentlyViewedMoviesUseCase: GetRecentlyViewedMoviesUseCase = mockk()
    private var getRecommendedMovieUseCase: GetRecommendedMovieUseCase = mockk()
    private var getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase = mockk()
    private var getUserMovieRatingUseCase: GetUserMovieRatingUseCase = mockk()
    private var searchMovieByNameUseCase: SearchMovieByNameUseCase = mockk()
    private var setMovieRecentUseCase: SetMovieRecentUseCase = mockk()

    private lateinit var movieUseCase: MovieUseCase

    @BeforeEach
    fun setUp() {
        movieUseCase = MovieUseCase(
            addGenresUseCase = addGenresUseCase,
            addMovieRatingUseCase = addMovieRatingUseCase,
            addMoviesUseCase = addMoviesUseCase,
            clearMovieCacheWithOutRecentViewedUseCase = clearMovieCacheWithOutRecentViewedUseCase,
            clearMoviesCacheUseCase = clearMoviesCacheUseCase,
            clearRecentlyMoviesUseCase = clearRecentlyMoviesUseCase,
            getMovieDetailsUseCase = getMovieDetailsUseCase,
            getMoviesGenresByIdsUseCase = getMoviesGenresByIdsUseCase,
            getMovieReviewsUseCase = getMovieReviewsUseCase,
            getMoviesByGenresUseCase = getMoviesByGenresUseCase,
            getMoviesGenresUseCase = getMoviesGenresUseCase,
            getPopularityMoviesUseCase = getPopularityMoviesUseCase,
            getRecentlyReleasedMoviesUseCase = getRecentlyReleasedMoviesUseCase,
            getRecentlyViewedMoviesUseCase = getRecentlyViewedMoviesUseCase,
            getRecommendedMovieUseCase = getRecommendedMovieUseCase,
            getUpcomingMoviesUseCase = getUpcomingMoviesUseCase,
            getUserMovieRatingUseCase = getUserMovieRatingUseCase,
            searchMovieByNameUseCase = searchMovieByNameUseCase,
            setMovieRecentUseCase = setMovieRecentUseCase
        )
    }

    @Test
    fun `movieUseCase should be properly instantiated`() {
        assertThat(movieUseCase).isNotNull()
    }

    @Test
    fun `addGenresUseCase property should return correct instance`() {
        assertThat(movieUseCase.addGenresUseCase).isEqualTo(addGenresUseCase)
    }

    @Test
    fun `addMovieRatingUseCase property should return correct instance`() {
        assertThat(movieUseCase.addMovieRatingUseCase).isEqualTo(addMovieRatingUseCase)
    }

    @Test
    fun `addMoviesUseCase property should return correct instance`() {
        assertThat(movieUseCase.addMoviesUseCase).isEqualTo(addMoviesUseCase)
    }

    @Test
    fun `clearMovieCacheWithOutRecentViewedUseCase property should return correct instance`() {
        assertThat(movieUseCase.clearMovieCacheWithOutRecentViewedUseCase).isEqualTo(
            clearMovieCacheWithOutRecentViewedUseCase
        )
    }

    @Test
    fun `clearMoviesCacheUseCase property should return correct instance`() {
        assertThat(movieUseCase.clearMoviesCacheUseCase).isEqualTo(clearMoviesCacheUseCase)
    }

    @Test
    fun `clearRecentlyMoviesUseCase property should return correct instance`() {
        assertThat(movieUseCase.clearRecentlyMoviesUseCase).isEqualTo(clearRecentlyMoviesUseCase)
    }

    @Test
    fun `getMovieDetailsUseCase property should return correct instance`() {
        assertThat(movieUseCase.getMovieDetailsUseCase).isEqualTo(getMovieDetailsUseCase)
    }

    @Test
    fun `getMoviesGenresByIdsUseCase property should return correct instance`() {
        assertThat(movieUseCase.getMoviesGenresByIdsUseCase).isEqualTo(getMoviesGenresByIdsUseCase)
    }

    @Test
    fun `getMovieReviewsUseCase property should return correct instance`() {
        assertThat(movieUseCase.getMovieReviewsUseCase).isEqualTo(getMovieReviewsUseCase)
    }

    @Test
    fun `getMoviesByGenresUseCase property should return correct instance`() {
        assertThat(movieUseCase.getMoviesByGenresUseCase).isEqualTo(getMoviesByGenresUseCase)
    }

    @Test
    fun `getMoviesGenresUseCase property should return correct instance`() {
        assertThat(movieUseCase.getMoviesGenresUseCase).isEqualTo(getMoviesGenresUseCase)
    }

    @Test
    fun `getPopularityMoviesUseCase property should return correct instance`() {
        assertThat(movieUseCase.getPopularityMoviesUseCase).isEqualTo(getPopularityMoviesUseCase)
    }

    @Test
    fun `getRecentlyReleasedMoviesUseCase property should return correct instance`() {
        assertThat(movieUseCase.getRecentlyReleasedMoviesUseCase).isEqualTo(
            getRecentlyReleasedMoviesUseCase
        )
    }

    @Test
    fun `getRecentlyViewedMoviesUseCase property should return correct instance`() {
        assertThat(movieUseCase.getRecentlyViewedMoviesUseCase).isEqualTo(
            getRecentlyViewedMoviesUseCase
        )
    }

    @Test
    fun `getRecommendedMovieUseCase property should return correct instance`() {
        assertThat(movieUseCase.getRecommendedMovieUseCase).isEqualTo(getRecommendedMovieUseCase)
    }

    @Test
    fun `getUpcomingMoviesUseCase property should return correct instance`() {
        assertThat(movieUseCase.getUpcomingMoviesUseCase).isEqualTo(getUpcomingMoviesUseCase)
    }

    @Test
    fun `getUserMovieRatingUseCase property should return correct instance`() {
        assertThat(movieUseCase.getUserMovieRatingUseCase).isEqualTo(getUserMovieRatingUseCase)
    }

    @Test
    fun `searchMovieByNameUseCase property should return correct instance`() {
        assertThat(movieUseCase.searchMovieByNameUseCase).isEqualTo(searchMovieByNameUseCase)
    }

    @Test
    fun `setMovieRecentUseCase property should return correct instance`() {
        assertThat(movieUseCase.setMovieRecentUseCase).isEqualTo(setMovieRecentUseCase)
    }
}