package com.giraffe.presentation.home.screen.show_more.strategy

import com.giraffe.media.movie.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.GetUpcomingMoviesUseCase
import com.giraffe.presentation.home.navigation.home.routes.MixedMediaSectionType
import com.giraffe.presentation.home.screen.show_more.MixedMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.flow.first

class UpcomingMoviesStrategy(
    private val getUpcomingMovies: GetUpcomingMoviesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
) : MixedMediaStrategy {
    override suspend fun loadData(page: Int, pageSize: Int) =
        getUpcomingMovies.invoke(page = page, pageSize).first()
            .map { movie ->
                movie.toShowMorePoster(
                    getMovieGenresUseCase(movie.genresID).map { it.title })
            }

    override fun getSectionType() = MixedMediaSectionType.UPCOMING_MOVIES
}