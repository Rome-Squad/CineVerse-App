package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.usecase.GetMoviesByGenreIdsUseCase
import com.giraffe.media.series.usecase.GetSeriesByGenreIdsUseCase
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MindBendingStoriesStrategy(
    private val getSeriesByGenresUseCase: GetSeriesByGenreIdsUseCase,
    private val getMoviesByGenresUseCase: GetMoviesByGenreIdsUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(
        page: Int,
        pageSize: Int,
        seriesGenres: List<Genre>,
        moviesGenres: List<Genre>
    ): List<PosterMedia> {
        return withContext(Dispatchers.IO) {
            val genreIdForMystery = 9648
            val genreIdForFantasy = 14
            val moviesResult = async {
                getMoviesByGenresUseCase(
                    page = page,
                    genreIds = listOf(genreIdForMystery, genreIdForFantasy)
                ).map { movie ->
                    movie.toShowMorePoster(
                        moviesGenres.filter { it.id in movie.genresID }.map { it.title }
                    )
                }
            }

            val seriesResult = async {
                getSeriesByGenresUseCase(
                    page = page,
                    genreIds = listOf(genreIdForMystery, genreIdForFantasy)
                ).map { series ->
                    series.toShowMorePoster(
                        seriesGenres.filter { it.id in series.genreIDs }.map { it.title }
                    )
                }
            }

            moviesResult.await() + seriesResult.await()
        }
    }

    override fun getSectionType() = CategoryMediaSectionType.MIND_BENDING_STORIES

}