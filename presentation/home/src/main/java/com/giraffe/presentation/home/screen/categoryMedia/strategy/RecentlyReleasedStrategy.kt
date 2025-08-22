package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.movie.usecase.genre.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.recentlyReleased.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.series.usecase.genre.GetSeriesGenresByIdsUseCase
import com.giraffe.media.series.usecase.recentlyReleased.GetRecentlyReleasedSeriesUseCase
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class RecentlyReleasedStrategy(
    private val getRecentlyReleasedMovies: GetRecentlyReleasedMoviesUseCase,
    private val getRecentlyReleasedSeries: GetRecentlyReleasedSeriesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(page: Int, pageSize: Int): List<PosterMedia> {
        return withContext(Dispatchers.IO) {
            val recentMovies =
                async {
                    getRecentlyReleasedMovies.invoke(page = page, limit = pageSize)
                        .map { movie ->
                            async {
                                movie.toShowMorePoster(
                                    getMovieGenresUseCase(movie.genresID).map { it.title }
                                )
                            }
                        }
                }
            val recentSeries = async {
                getRecentlyReleasedSeries(page = page, limit = pageSize).map { series ->
                    async {
                        series.toShowMorePoster(
                            getSeriesGenresUseCase(series.genreIDs).map { it.title })
                    }
                }
            }
            recentMovies.await().awaitAll() + recentSeries.await().awaitAll()
        }
    }

    override fun getSectionType() = CategoryMediaSectionType.RECENTLY_RELEASED
}