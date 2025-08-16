package com.giraffe.presentation.home.screen.show_more.strategy

import com.giraffe.media.movie.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.series.usecase.GetRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.presentation.home.model.ShowMorePoster
import com.giraffe.presentation.home.navigation.home.routes.MixedMediaSectionType
import com.giraffe.presentation.home.screen.show_more.MixedMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster

class RecentlyReleasedStrategy(
    private val getRecentlyReleasedMovies: GetRecentlyReleasedMoviesUseCase,
    private val getRecentlyReleasedSeries: GetRecentlyReleasedSeriesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
) : MixedMediaStrategy {
    override suspend fun loadData(page: Int, pageSize: Int): List<ShowMorePoster> {
        val recentMovies =
            getRecentlyReleasedMovies.getRemoteRecentlyReleased(page = page, limit = pageSize)
                .map { movie ->
                    movie.toShowMorePoster(getMovieGenresUseCase(movie.genresID).map { it.title })
                }
        val recentSeries =
            getRecentlyReleasedSeries(page = page, limit = pageSize).map { series ->
                series.toShowMorePoster(
                    getSeriesGenresUseCase(series.genreIDs).map { it.title })
            }
        return recentMovies + recentSeries
    }

    override fun getSectionType() = MixedMediaSectionType.RECENTLY_RELEASED
}