package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.movie.usecase.genre.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.upcoming.GetUpcomingMoviesUseCase
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class UpcomingMoviesStrategy(
    private val getUpcomingMovies: GetUpcomingMoviesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
) : CategoryMediaStrategy {
    override suspend fun loadData(page: Int, pageSize: Int) = withContext(Dispatchers.IO) {
        getUpcomingMovies.invoke(page = page, pageSize).map { movie ->
            async {
                movie.toShowMorePoster(
                    getMovieGenresUseCase(movie.genresID).map { it.title })
            }
        }.awaitAll()
    }

    override fun getSectionType() = CategoryMediaSectionType.UPCOMING_MOVIES
}