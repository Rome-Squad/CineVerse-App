package com.giraffe.presentation.home.screen.show_more.strategy

import com.giraffe.media.movie.usecase.genre.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.upcoming.GetUpcomingMoviesUseCase
import com.giraffe.presentation.home.navigation.home.routes.MixedMediaSectionType
import com.giraffe.presentation.home.screen.show_more.MixedMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster

class UpcomingMoviesStrategy(
    private val getUpcomingMovies: GetUpcomingMoviesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
) : MixedMediaStrategy {
    override suspend fun loadData(page: Int, pageSize: Int) =
        getUpcomingMovies.invoke(page = page, pageSize).map { movie ->
            movie.toShowMorePoster(
                getMovieGenresUseCase(movie.genresID).map { it.title })
        }

    override fun getSectionType() = MixedMediaSectionType.UPCOMING_MOVIES
}