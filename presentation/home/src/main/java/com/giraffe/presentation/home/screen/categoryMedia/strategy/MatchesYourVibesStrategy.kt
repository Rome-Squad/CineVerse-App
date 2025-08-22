package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.usecase.matchesYourVibe.GetMatchesYourVibeMoviesUseCase
import com.giraffe.media.series.usecase.matchesYourVibe.GetMatchesYourVibeSeriesUseCase
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MatchesYourVibesStrategy(
    private val getMatchesYourVibeMovies: GetMatchesYourVibeMoviesUseCase,
    private val getMatchesYourVibeSeries: GetMatchesYourVibeSeriesUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(
        page: Int,
        pageSize: Int,
        seriesGenres: List<Genre>,
        moviesGenres: List<Genre>
    ): List<PosterMedia> {
        return withContext(Dispatchers.IO) {
            val matchesYourVibeMovies =
                async { getMatchesYourVibeMovies.invoke(page = page, limit = pageSize) }

            val matchesYourVibeSeries =
                async { getMatchesYourVibeSeries(page = page, limit = pageSize) }

            val movies = matchesYourVibeMovies.await().map { movie ->
                movie.toShowMorePoster(
                    moviesGenres.filter { it.id in movie.genresID }.map { it.title })
            }

            val series = matchesYourVibeSeries.await().map { series ->
                series.toShowMorePoster(
                    seriesGenres.filter { it.id in series.genreIDs }.map { it.title })
            }

            (movies + series).distinctBy { it.id }
        }
    }

    override fun getSectionType() = CategoryMediaSectionType.MATCHES_YOUR_VIBES
}