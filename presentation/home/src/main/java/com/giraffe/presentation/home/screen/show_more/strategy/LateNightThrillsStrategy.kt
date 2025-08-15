package com.giraffe.presentation.home.screen.show_more.strategy

import com.giraffe.media.movie.usecase.GetFilteredMoviesUseCase
import com.giraffe.media.movie.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetFilteredSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.presentation.home.model.ShowMorePoster
import com.giraffe.presentation.home.navigation.home.routes.MixedMediaSectionType
import com.giraffe.presentation.home.screen.show_more.MixedMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster

class LateNightThrillsStrategy(
    private val getSeriesByGenresUseCase: GetFilteredSeriesUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase,
    private val getMoviesByGenresUseCase: GetFilteredMoviesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase
) : MixedMediaStrategy {
    override suspend fun loadData(
        page: Int,
        pageSize: Int
    ): List<ShowMorePoster> {
        val genreIdForHorror = 27
        val genreIdForThriller = 53
        val moviesResult =
            getMoviesByGenresUseCase(
                page = page,
                genreId = listOf(genreIdForHorror, genreIdForThriller)
            ).map { movie ->
                movie.toShowMorePoster(
                    getMovieGenresUseCase(movie.genresID).map { it.title }
                )
            }
        val seriesResult =
            getSeriesByGenresUseCase(
                page = page,
                genreId = listOf(genreIdForHorror, genreIdForThriller)
            ).map { series ->
                series.toShowMorePoster(
                    getSeriesGenresUseCase(series.genreIDs).map { it.title }
                )
            }
        return moviesResult + seriesResult
    }

    override fun getSectionType() = MixedMediaSectionType.LATE_NIGHT_THRILLS

}