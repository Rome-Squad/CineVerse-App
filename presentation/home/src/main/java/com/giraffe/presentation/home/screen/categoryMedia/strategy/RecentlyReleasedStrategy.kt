package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.movie.usecase.genre.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.recentlyReleased.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.series.usecase.GetRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.genre.GetSeriesGenresByIdsUseCase
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster

class RecentlyReleasedStrategy(
    private val getRecentlyReleasedMovies: GetRecentlyReleasedMoviesUseCase,
    private val getRecentlyReleasedSeries: GetRecentlyReleasedSeriesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(page: Int, pageSize: Int): List<PosterMedia> {
        val recentMovies =
            getRecentlyReleasedMovies.invoke(page = page, limit = pageSize)
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

    override fun getSectionType() = CategoryMediaSectionType.RECENTLY_RELEASED
}