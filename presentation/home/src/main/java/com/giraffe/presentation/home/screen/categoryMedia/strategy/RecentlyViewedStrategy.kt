package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.movie.usecase.genre.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.recentlyViewed.ObserveRecentlyViewedMoviesUseCase
import com.giraffe.media.series.usecase.GetRecentlyViewedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.flow.first

class RecentlyViewedStrategy(
    private val observeRecentlyViewedMoviesUseCase: ObserveRecentlyViewedMoviesUseCase,
    private val getRecentlySeriesUseCase: GetRecentlyViewedSeriesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(page: Int, pageSize: Int): List<PosterMedia> {
        val recentMovies = observeRecentlyViewedMoviesUseCase.invoke(page, pageSize).first()
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
