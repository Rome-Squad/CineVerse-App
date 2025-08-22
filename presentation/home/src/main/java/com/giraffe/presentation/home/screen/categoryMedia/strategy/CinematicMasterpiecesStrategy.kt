package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.usecase.GetMoviesBySortUseCase
import com.giraffe.media.series.usecase.GetSeriesBySortUseCase
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class CinematicMasterpiecesStrategy(
    private val getSeriesBySortUseCase: GetSeriesBySortUseCase,
    private val getMoviesBySortUseCase: GetMoviesBySortUseCase,
) : CategoryMediaStrategy {
    override suspend fun loadData(
        page: Int,
        pageSize: Int,
        seriesGenres: List<Genre>,
        moviesGenres: List<Genre>
    ): List<PosterMedia> {
        return withContext(Dispatchers.IO) {
            val sortBy = "vote_average.desc"
            val moviesResult = async {
                getMoviesBySortUseCase(
                    page = page,
                    sortBy = sortBy
                ).map { movie ->
                    movie.toShowMorePoster(
                        moviesGenres.filter { it.id in movie.genresID }.map { it.title }
                    )
                }
            }

            val seriesResult = async {
                getSeriesBySortUseCase(
                    page = page,
                    sortBy = sortBy
                ).map { series ->
                    series.toShowMorePoster(
                        seriesGenres.filter { it.id in series.genreIDs }.map { it.title }
                    )
                }
            }

            moviesResult.await() + seriesResult.await()
        }
    }

    override fun getSectionType() = CategoryMediaSectionType.CINEMATIC_MASTERPIECE

}