package com.giraffe.presentation.home.screen.show_more.strategy

import com.giraffe.media.movie.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.GetRecentlyViewedMoviesUseCase
import com.giraffe.media.series.usecase.GetRecentlyViewedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.show_more.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.flow.first

class RecentlyViewedStrategy(
    private val getRecentlyViewedMovies: GetRecentlyViewedMoviesUseCase,
    private val getRecentlySeriesUseCase: GetRecentlyViewedSeriesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(page: Int, pageSize: Int): List<PosterMedia> {
        val recentMovies = getRecentlyViewedMovies(page, pageSize).first()
            .map { movie -> movie.toShowMorePoster(getMovieGenresUseCase(movie.genresID).map { it.title }) }
        val recentSeries = getRecentlySeriesUseCase(page, pageSize).first()
            .map { series -> series.toShowMorePoster(getSeriesGenresUseCase(series.genreIDs).map { it.title }) }
        return (recentMovies + recentSeries)
            .distinctBy { it.id }
            .filter { it.recentViewedAt != null }
            .sortedByDescending { it.recentViewedAt }
    }

    override fun getSectionType() = CategoryMediaSectionType.RECENTLY_VIEWED
}
