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

class FamilyNightPicksStrategy(
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
            val genreIdForFamily = 10751
            val genreIdForAnimation = 16
            val moviesResult = async {
                getMoviesByGenresUseCase(
                    page = page,
                    genreIds = listOf(genreIdForFamily, genreIdForAnimation)
                ).map { movie ->
                    movie.toShowMorePoster(
                        moviesGenres.filter { it.id in movie.genresID }.map { it.title }
                    )
                }
            }

            val seriesResult = async {
                getSeriesByGenresUseCase(
                    page = page,
                    genreIds = listOf(genreIdForFamily, genreIdForAnimation)
                ).map { series ->
                    series.toShowMorePoster(
                        seriesGenres.filter { it.id in series.genreIDs }.map { it.title }
                    )
                }
            }

            moviesResult.await() + seriesResult.await()
        }
    }

    override fun getSectionType() = CategoryMediaSectionType.FAMILY_NIGHT_PICKS

}