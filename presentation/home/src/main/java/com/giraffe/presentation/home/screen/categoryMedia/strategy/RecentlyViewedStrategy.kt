package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.usecase.recentlyViewed.ObserveRecentlyViewedMoviesUseCase
import com.giraffe.media.series.usecase.recentlyViewed.ObserveRecentlyViewedSeriesUseCase
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class RecentlyViewedStrategy(
    private val observeRecentlyViewedMoviesUseCase: ObserveRecentlyViewedMoviesUseCase,
    private val observeRecentlyViewedSeriesUseCase: ObserveRecentlyViewedSeriesUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(
        page: Int,
        pageSize: Int,
        seriesGenres: List<Genre>,
        moviesGenres: List<Genre>
    ): List<PosterMedia> {
        return withContext(Dispatchers.IO) {
            val recentMovies = async {
                observeRecentlyViewedMoviesUseCase.invoke(page, pageSize).first()
                    .map { movie ->
                        movie.toShowMorePoster(
                            moviesGenres.filter { it.id in movie.genresID }
                                .map { it.title }
                        )
                    }
            }

            val recentSeries = async {
                observeRecentlyViewedSeriesUseCase.invoke(page, pageSize).first()
                    .map { series ->
                        series.toShowMorePoster(
                            seriesGenres.filter { it.id in series.genreIDs }.map { it.title }
                        )

                    }
            }

            (recentMovies.await() + recentSeries.await())
                .distinctBy { it.id }
                .sortedByDescending { it.recentViewedAt }
        }
    }

    override fun getSectionType() = CategoryMediaSectionType.RECENTLY_VIEWED
}
