package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.usecase.upcoming.GetUpcomingMoviesUseCase
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpcomingMoviesStrategy(
    private val getUpcomingMovies: GetUpcomingMoviesUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(
        page: Int,
        pageSize: Int,
        seriesGenres: List<Genre>,
        moviesGenres: List<Genre>
    ) = withContext(Dispatchers.IO) {
        getUpcomingMovies.invoke(page = page, pageSize).map { movie ->
            movie.toShowMorePoster(
                moviesGenres.filter { it.id in movie.genresID }.map { it.title })
        }
    }

    override fun getSectionType() = CategoryMediaSectionType.UPCOMING_MOVIES
}