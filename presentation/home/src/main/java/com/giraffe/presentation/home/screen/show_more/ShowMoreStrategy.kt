package com.giraffe.presentation.home.screen.show_more

import com.giraffe.media.movie.usecase.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.movie.usecase.GetRecentlyViewedMoviesUseCase
import com.giraffe.media.movie.usecase.GetRecommendedMovieUseCase
import com.giraffe.media.movie.usecase.GetUpcomingMoviesUseCase
import com.giraffe.media.series.usecase.GetRecentSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase
import com.giraffe.presentation.home.utils.toPosterUi
import kotlinx.coroutines.flow.first

interface ShowMoreStrategy {
    suspend fun loadData(): List<PosterUiState>
    fun getSectionType(): ShowMoreSectionType
}

class RecentlyReleasedStrategy(
    private val getRecentlyReleasedMovies: GetRecentlyReleasedMoviesUseCase,
    private val getRecentlyReleasedSeries: GetRecentlyReleasedSeriesUseCase
) : ShowMoreStrategy {
    override suspend fun loadData(): List<PosterUiState> {
        val recentMovies = getRecentlyReleasedMovies(page = 1).map { it.toPosterUi() }
        val recentSeries = getRecentlyReleasedSeries(page = 1, limit = 10).map { it.toPosterUi() }
        return recentMovies + recentSeries
    }

    override fun getSectionType() = ShowMoreSectionType.RECENTLY_RELEASED
}

class TopRatedTvShowsStrategy(
    private val getTopRatedSeries: GetTopRatedSeriesUseCase
) : ShowMoreStrategy {
    override suspend fun loadData(): List<PosterUiState> {
        return getTopRatedSeries(page = 1, limit = 10).map { it.toPosterUi() }
    }

    override fun getSectionType() = ShowMoreSectionType.TOP_RATED_TV_SHOWS
}

class UpcomingMoviesStrategy(
    private val getUpcomingMovies: GetUpcomingMoviesUseCase
) : ShowMoreStrategy {
    override suspend fun loadData(): List<PosterUiState> {
        return getUpcomingMovies(page = 1).map { it.toPosterUi() }
    }

    override fun getSectionType() = ShowMoreSectionType.UPCOMING_MOVIES
}


class RecentlyViewedStrategy(
    private val getRecentlyViewedMovies: GetRecentlyViewedMoviesUseCase,
    private val getRecentlySeriesUseCase: GetRecentSeriesUseCase
) : ShowMoreStrategy {
    override suspend fun loadData(): List<PosterUiState> {
        val recentMovies = getRecentlyViewedMovies().first().map { it.toPosterUi() }
        val recentSeries = getRecentlySeriesUseCase().first().map { it.toPosterUi() }
        return (recentMovies + recentSeries).distinctBy { it.id }
    }

    override fun getSectionType() = ShowMoreSectionType.RECENTLY_VIEWED
}

class MatchesYourVibesStrategy(
    private val getRecentlyViewedMovies: GetRecentlyViewedMoviesUseCase,
    private val getRecentlySeriesUseCase: GetRecentSeriesUseCase,
    private val getRecommendedMovie: GetRecommendedMovieUseCase,
    private val getRecommendedSeries: GetRecommendedSeriesUseCase
) : ShowMoreStrategy {
    override suspend fun loadData(): List<PosterUiState> {
        val recentMovieId = getRecentlyViewedMovies().first().firstOrNull()?.id
        val recentSeriesId = getRecentlySeriesUseCase().first().firstOrNull()?.id

        val recommendedMovies =
            recentMovieId?.let { getRecommendedMovie(it, page = 1) } ?: emptyList()
        val recommendedSeries =
            recentSeriesId?.let { getRecommendedSeries(it, page = 1) } ?: emptyList()

        return (recommendedMovies.map { it.toPosterUi() } + recommendedSeries.map { it.toPosterUi() })
            .distinctBy { it.id }
    }

    override fun getSectionType() = ShowMoreSectionType.MATCHES_YOUR_VIBES
}


class ShowMoreFactory(
    private val recentlyReleasedStrategy: RecentlyReleasedStrategy,
    private val topRatedTvShowsStrategy: TopRatedTvShowsStrategy,
    private val upcomingMoviesStrategy: UpcomingMoviesStrategy,
    private val recentlyViewedStrategy: RecentlyViewedStrategy,
    private val matchesYourVibesStrategy: MatchesYourVibesStrategy
) {
    fun createStrategy(sectionType: ShowMoreSectionType): ShowMoreStrategy {
        return when (sectionType) {
            ShowMoreSectionType.RECENTLY_RELEASED -> recentlyReleasedStrategy
            ShowMoreSectionType.TOP_RATED_TV_SHOWS -> topRatedTvShowsStrategy
            ShowMoreSectionType.UPCOMING_MOVIES -> upcomingMoviesStrategy
            ShowMoreSectionType.RECENTLY_VIEWED -> recentlyViewedStrategy
            ShowMoreSectionType.MATCHES_YOUR_VIBES -> matchesYourVibesStrategy
        }
    }
}