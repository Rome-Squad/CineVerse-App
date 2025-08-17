package com.giraffe.presentation.home.screen.show_more.strategy

import com.giraffe.media.movie.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.GetUpcomingMoviesUseCase
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.show_more.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster

class UpcomingMoviesStrategy(
    private val getUpcomingMovies: GetUpcomingMoviesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
) : CategoryMediaStrategy {
    override suspend fun loadData(page: Int, pageSize: Int) =
        getUpcomingMovies(page = page, pageSize).map { movie ->
            movie.toShowMorePoster(
                getMovieGenresUseCase(movie.genresID).map { it.title })
        }

    override fun getSectionType() = CategoryMediaSectionType.UPCOMING_MOVIES
}