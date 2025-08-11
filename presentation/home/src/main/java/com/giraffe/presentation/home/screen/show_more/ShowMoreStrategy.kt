package com.giraffe.presentation.home.screen.show_more

import com.giraffe.media.movie.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.movie.usecase.GetRecentlyViewedMoviesUseCase
import com.giraffe.media.movie.usecase.GetRecommendedMoviesUseCase
import com.giraffe.media.movie.usecase.GetUpcomingMoviesUseCase
import com.giraffe.media.series.usecase.GetRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentlyViewedSeriesUseCase
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase
import com.giraffe.presentation.home.model.ShowMorePoster
import com.giraffe.presentation.home.navigation.home.routes.ShowMoreSectionType
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.flow.first

interface ShowMoreStrategy {
    suspend fun loadData(): List<ShowMorePoster>
    fun getSectionType(): ShowMoreSectionType
}

class RecentlyReleasedStrategy(
    private val getRecentlyReleasedMovies: GetRecentlyReleasedMoviesUseCase,
    private val getRecentlyReleasedSeries: GetRecentlyReleasedSeriesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
) : ShowMoreStrategy {
    override suspend fun loadData(): List<ShowMorePoster> {
        val recentMovies =
            getRecentlyReleasedMovies(page = 1).map { movie ->
                movie.toShowMorePoster(getMovieGenresUseCase(movie.genresID).map { it.title })
            }
        val recentSeries =
            getRecentlyReleasedSeries(page = 1, limit = 10).map { series ->
                series.toShowMorePoster(
                    getSeriesGenresUseCase(series.genreIDs).map { it.title })
            }
        return recentMovies + recentSeries
    }

    override fun getSectionType() = ShowMoreSectionType.RECENTLY_RELEASED
}

class TopRatedTvShowsStrategy(
    private val getTopRatedSeries: GetTopRatedSeriesUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
) : ShowMoreStrategy {
    override suspend fun loadData() = getTopRatedSeries(page = 1, limit = 10).map { series ->
        series.toShowMorePoster(
            getSeriesGenresUseCase(series.genreIDs).map { it.title })
    }

    override fun getSectionType() = ShowMoreSectionType.TOP_RATED_TV_SHOWS
}

class UpcomingMoviesStrategy(
    private val getUpcomingMovies: GetUpcomingMoviesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
) : ShowMoreStrategy {
    override suspend fun loadData() = getUpcomingMovies(page = 1).map { movie ->
        movie.toShowMorePoster(
            getMovieGenresUseCase(movie.genresID).map { it.title })
    }

    override fun getSectionType() = ShowMoreSectionType.UPCOMING_MOVIES
}


class RecentlyViewedStrategy(
    private val getRecentlyViewedMovies: GetRecentlyViewedMoviesUseCase,
    private val getRecentlySeriesUseCase: GetRecentlyViewedSeriesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
) : ShowMoreStrategy {
    override suspend fun loadData(): List<ShowMorePoster> {
        val recentMovies = getRecentlyViewedMovies().first()
            .map { movie -> movie.toShowMorePoster(getMovieGenresUseCase(movie.genresID).map { it.title }) }
        val recentSeries = getRecentlySeriesUseCase().first()
            .map { series -> series.toShowMorePoster(getSeriesGenresUseCase(series.genreIDs).map { it.title }) }
        return (recentMovies + recentSeries).distinctBy { it.id }
    }

    override fun getSectionType() = ShowMoreSectionType.RECENTLY_VIEWED
}

class MatchesYourVibesStrategy(
    private val getRecentlyViewedMovies: GetRecentlyViewedMoviesUseCase,
    private val getRecentlySeriesUseCase: GetRecentlyViewedSeriesUseCase,
    private val getRecommendedMovies: GetRecommendedMoviesUseCase,
    private val getRecommendedSeries: GetRecommendedSeriesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
) : ShowMoreStrategy {
    override suspend fun loadData(): List<ShowMorePoster> {
        val recentMovieId = getRecentlyViewedMovies().first().firstOrNull()?.id
        val recentSeriesId = getRecentlySeriesUseCase().first().firstOrNull()?.id

        val recommendedMovies =
            recentMovieId?.let { getRecommendedMovies(it, page = 1) } ?: emptyList()
        val recommendedSeries =
            recentSeriesId?.let { getRecommendedSeries(it, page = 1) } ?: emptyList()

        return (recommendedMovies.map { movie ->
            movie.toShowMorePoster(
                getMovieGenresUseCase(
                    movie.genresID
                ).map { it.title })
        } + recommendedSeries.map { series -> series.toShowMorePoster(getSeriesGenresUseCase(series.genreIDs).map { it.title }) })
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