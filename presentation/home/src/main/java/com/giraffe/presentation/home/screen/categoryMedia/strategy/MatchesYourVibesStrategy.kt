package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.movie.usecase.genre.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.matchesYourVibe.GetMatchesYourVibeMoviesUseCase
import com.giraffe.media.series.usecase.genre.GetSeriesGenresByIdsUseCase
import com.giraffe.media.series.usecase.matchesYourVibe.GetMatchesYourVibeSeriesUseCase
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class MatchesYourVibesStrategy(
    private val getMatchesYourVibeMovies: GetMatchesYourVibeMoviesUseCase,
    private val getMatchesYourVibeSeries: GetMatchesYourVibeSeriesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(page: Int, pageSize: Int): List<PosterMedia> {
        return withContext(Dispatchers.IO) {
            val matchesYourVibeMovies =
                async { getMatchesYourVibeMovies.invoke(page = page, limit = pageSize) }

            val matchesYourVibeSeries =
                async { getMatchesYourVibeSeries(page = page, limit = pageSize) }

            val movies = matchesYourVibeMovies.await().map { movie ->
                async {
                    movie.toShowMorePoster(
                        getMovieGenresUseCase(
                            movie.genresID
                        ).map { it.title })
                }
            }

            val series = matchesYourVibeSeries.await().map { series ->
                async {
                    series.toShowMorePoster(
                        getSeriesGenresUseCase(
                            series.genreIDs
                        ).map { it.title })
                }
            }

            (movies.awaitAll() + series.awaitAll()).distinctBy { it.id }
        }
    }

    override fun getSectionType() = CategoryMediaSectionType.MATCHES_YOUR_VIBES
}