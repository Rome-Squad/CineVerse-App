package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.movie.usecase.GetMoviesByGenreIdsUseCase
import com.giraffe.media.movie.usecase.genre.GetMoviesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetSeriesByGenreIdsUseCase
import com.giraffe.media.series.usecase.genre.GetSeriesGenresByIdsUseCase
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class MindBendingStoriesStrategy(
    private val getSeriesByGenresUseCase: GetSeriesByGenreIdsUseCase,
    private val getSeriesGenresByIdsUseCase: GetSeriesGenresByIdsUseCase,
    private val getMoviesByGenresUseCase: GetMoviesByGenreIdsUseCase,
    private val getMoviesGenresByIdsUseCase: GetMoviesGenresByIdsUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(
        page: Int,
        pageSize: Int
    ): List<PosterMedia> {
        return withContext(Dispatchers.IO) {
            val genreIdForMystery = 9648
            val genreIdForFantasy = 14
            val moviesResult = async {
                getMoviesByGenresUseCase(
                    page = page,
                    genreIds = listOf(genreIdForMystery, genreIdForFantasy)
                ).map { movie ->
                    async {
                        movie.toShowMorePoster(
                            getMoviesGenresByIdsUseCase(movie.genresID).map { it.title }
                        )
                    }
                }
            }

            val seriesResult = async {
                getSeriesByGenresUseCase(
                    page = page,
                    genreIds = listOf(genreIdForMystery, genreIdForFantasy)
                ).map { series ->
                    async {
                        series.toShowMorePoster(
                            getSeriesGenresByIdsUseCase(series.genreIDs).map { it.title }
                        )
                    }
                }
            }

            moviesResult.await().awaitAll() + seriesResult.await().awaitAll()
        }
    }

    override fun getSectionType() = CategoryMediaSectionType.MIND_BENDING_STORIES

}