package com.giraffe.presentation.home.screen.category_media.strategy

import com.giraffe.media.movie.usecase.GetMoviesBySortUseCase
import com.giraffe.media.movie.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetSeriesBySortUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.category_media.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster

class CinematicMasterpiecesStrategy(
    private val getSeriesBySortUseCase: GetSeriesBySortUseCase,
    private val getSeriesGenresByIdsUseCase: GetSeriesGenresByIdsUseCase,
    private val getMoviesBySortUseCase: GetMoviesBySortUseCase,
    private val getMoviesGenresByIdsUseCase: GetMoviesGenresByIdsUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(
        page: Int,
        pageSize: Int
    ): List<PosterMedia> {
        val sortBy = "vote_average.desc"
        val moviesResult =
            getMoviesBySortUseCase(
                page = page,
                sortBy = sortBy
            ).map { movie ->
                movie.toShowMorePoster(
                    getMoviesGenresByIdsUseCase(movie.genresID).map { it.title }
                )
            }
        val seriesResult =
            getSeriesBySortUseCase(
                page = page,
                sortBy = sortBy
            ).map { series ->
                series.toShowMorePoster(
                    getSeriesGenresByIdsUseCase(series.genreIDs).map { it.title }
                )
            }
        return moviesResult + seriesResult
    }

    override fun getSectionType() = CategoryMediaSectionType.CINEMATIC_MASTERPIECE

}