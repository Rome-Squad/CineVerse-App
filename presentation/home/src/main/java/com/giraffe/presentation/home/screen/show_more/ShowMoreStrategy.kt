package com.giraffe.presentation.home.screen.show_more

import com.giraffe.media.movie.usecase.GetMatchesYourVibeMoviesUseCase
import com.giraffe.media.movie.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.movie.usecase.GetRecentlyViewedMoviesUseCase
import com.giraffe.media.movie.usecase.GetUpcomingMoviesUseCase
import com.giraffe.media.series.usecase.GetMatchesYourVibeSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentlyViewedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase
import com.giraffe.presentation.home.model.ShowMorePoster
import com.giraffe.presentation.home.navigation.home.routes.ShowMoreSectionType
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.flow.first

interface ShowMoreStrategy {
    suspend fun loadData(page: Int, pageSize: Int): List<ShowMorePoster>
    fun getSectionType(): ShowMoreSectionType
}

class RecentlyReleasedStrategy(
    private val getRecentlyReleasedMovies: GetRecentlyReleasedMoviesUseCase,
    private val getRecentlyReleasedSeries: GetRecentlyReleasedSeriesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
) : ShowMoreStrategy {
    override suspend fun loadData(page: Int, pageSize: Int): List<ShowMorePoster> {
        val recentMovies =
            getRecentlyReleasedMovies(page = page, limit = pageSize).map { movie ->
                movie.toShowMorePoster(getMovieGenresUseCase(movie.genresID).map { it.title })
            }
        val recentSeries =
            getRecentlyReleasedSeries(page = page, limit = pageSize).map { series ->
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
    override suspend fun loadData(page: Int, pageSize: Int) =
        getTopRatedSeries(page = page, limit = pageSize).map { series ->
            series.toShowMorePoster(
                getSeriesGenresUseCase(series.genreIDs).map { it.title })
        }

    override fun getSectionType() = ShowMoreSectionType.TOP_RATED_TV_SHOWS
}

class UpcomingMoviesStrategy(
    private val getUpcomingMovies: GetUpcomingMoviesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
) : ShowMoreStrategy {
    override suspend fun loadData(page: Int, pageSize: Int) =
        getUpcomingMovies(page = page, pageSize).map { movie ->
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
    override suspend fun loadData(page: Int, pageSize: Int): List<ShowMorePoster> {
        val recentMovies = getRecentlyViewedMovies(page, pageSize).first()
            .map { movie -> movie.toShowMorePoster(getMovieGenresUseCase(movie.genresID).map { it.title }) }
        val recentSeries = getRecentlySeriesUseCase(page, pageSize).first()
            .map { series -> series.toShowMorePoster(getSeriesGenresUseCase(series.genreIDs).map { it.title }) }
        return (recentMovies + recentSeries)
            .distinctBy { it.id }
            .filter { it.recentViewedAt != null}
            .sortedByDescending { it.recentViewedAt }
    }

    override fun getSectionType() = ShowMoreSectionType.RECENTLY_VIEWED
}

class MatchesYourVibesStrategy(
    private val getMatchesYourVibeMovies: GetMatchesYourVibeMoviesUseCase,
    private val getMatchesYourVibeSeries: GetMatchesYourVibeSeriesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
) : ShowMoreStrategy {
    override suspend fun loadData(page: Int, pageSize: Int): List<ShowMorePoster> {

        val matchesYourVibeMovies = getMatchesYourVibeMovies(page = page, limit = pageSize)
        val matchesYourVibeSeries = getMatchesYourVibeSeries(page = page, limit = pageSize)

        return (matchesYourVibeMovies.map { movie ->
            movie.toShowMorePoster(
                getMovieGenresUseCase(
                    movie.genresID
                ).map { it.title })
        } + matchesYourVibeSeries.map { series ->
            series.toShowMorePoster(
                getSeriesGenresUseCase(
                    series.genreIDs
                ).map { it.title })
        })
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