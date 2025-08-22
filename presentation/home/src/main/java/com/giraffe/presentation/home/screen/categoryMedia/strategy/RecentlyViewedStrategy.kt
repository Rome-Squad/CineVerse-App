package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.movie.usecase.genre.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.recentlyViewed.ObserveRecentlyViewedMoviesUseCase
import com.giraffe.media.series.usecase.genre.GetSeriesGenresByIdsUseCase
import com.giraffe.media.series.usecase.recentlyViewed.ObserveRecentlyViewedSeriesUseCase
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class RecentlyViewedStrategy(
    private val observeRecentlyViewedMoviesUseCase: ObserveRecentlyViewedMoviesUseCase,
    private val observeRecentlyViewedSeriesUseCase: ObserveRecentlyViewedSeriesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(page: Int, pageSize: Int): List<PosterMedia> {
        return withContext(Dispatchers.IO) {
            val recentMovies = async {
                observeRecentlyViewedMoviesUseCase.invoke(page, pageSize).first()
                    .map { movie ->
                        async {
                            movie.toShowMorePoster(getMovieGenresUseCase(movie.genresID).map { it.title })
                        }
                    }
            }

            val recentSeries = async {
                observeRecentlyViewedSeriesUseCase.invoke(page, pageSize).first()
                    .map { series ->
                        async {
                            series.toShowMorePoster(getSeriesGenresUseCase(series.genreIDs).map { it.title })
                        }
                    }
            }

            (recentMovies.await().awaitAll() + recentSeries.await().awaitAll())
                .distinctBy { it.id }
                .sortedByDescending { it.recentViewedAt }
        }
    }

    override fun getSectionType() = CategoryMediaSectionType.RECENTLY_VIEWED
}
