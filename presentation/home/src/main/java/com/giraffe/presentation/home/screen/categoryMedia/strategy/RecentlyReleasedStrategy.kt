package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.usecase.recentlyReleased.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.series.usecase.recentlyReleased.GetRecentlyReleasedSeriesUseCase
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class RecentlyReleasedStrategy(
    private val getRecentlyReleasedMovies: GetRecentlyReleasedMoviesUseCase,
    private val getRecentlyReleasedSeries: GetRecentlyReleasedSeriesUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(
        page: Int,
        pageSize: Int,
        seriesGenres: List<Genre>,
        moviesGenres: List<Genre>
    ): List<PosterMedia> {
        return withContext(Dispatchers.IO) {
            val recentMovies =
                async {
                    getRecentlyReleasedMovies.invoke(page = page, limit = pageSize)
                        .map { movie ->
                            movie.toShowMorePoster(
                                moviesGenres.filter { it.id in movie.genresID }.map { it.title }
                            )
                        }
                }
            val recentSeries = async {
                getRecentlyReleasedSeries(page = page, limit = pageSize).map { series ->
                    series.toShowMorePoster(
                        seriesGenres.filter { it.id in series.genreIDs }.map { it.title }
                    )
                }
            }

            recentMovies.await() + recentSeries.await()
        }
    }

    override fun getSectionType() = CategoryMediaSectionType.RECENTLY_RELEASED
}