package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.movie.usecase.GetMoviesByGenreIdsUseCase
import com.giraffe.media.movie.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetSeriesByGenreIdsUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster

class LateNightThrillsStrategy(
    private val getSeriesByGenresUseCase: GetSeriesByGenreIdsUseCase,
    private val getSeriesGenresByIdsUseCase: GetSeriesGenresByIdsUseCase,
    private val getMoviesByGenresUseCase: GetMoviesByGenreIdsUseCase,
    private val getMoviesGenresByIdsUseCase: GetMoviesGenresByIdsUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(
        page: Int,
        pageSize: Int
    ): List<PosterMedia> {
        val genreIdForHorror = 27
        val genreIdForThriller = 53
        val moviesResult =
            getMoviesByGenresUseCase(
                page = page,
                genreIds = listOf(genreIdForHorror, genreIdForThriller)
            ).map { movie ->
                movie.toShowMorePoster(
                    getMoviesGenresByIdsUseCase(movie.genresID).map { it.title }
                )
            }
        val seriesResult =
            getSeriesByGenresUseCase(
                page = page,
                genreIds = listOf(genreIdForHorror, genreIdForThriller)
            ).map { series ->
                series.toShowMorePoster(
                    getSeriesGenresByIdsUseCase(series.genreIDs).map { it.title }
                )
            }
        return moviesResult + seriesResult
    }

    override fun getSectionType() = CategoryMediaSectionType.LATE_NIGHT_THRILLS

}