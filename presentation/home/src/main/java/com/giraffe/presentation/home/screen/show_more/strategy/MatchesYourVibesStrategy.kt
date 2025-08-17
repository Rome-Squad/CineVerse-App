package com.giraffe.presentation.home.screen.show_more.strategy

import com.giraffe.media.movie.usecase.matchesYourVibe.GetMatchesYourVibeMoviesUseCase
import com.giraffe.media.movie.usecase.genre.GetMoviesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetMatchesYourVibeSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.show_more.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster

class MatchesYourVibesStrategy(
    private val getMatchesYourVibeMovies: GetMatchesYourVibeMoviesUseCase,
    private val getMatchesYourVibeSeries: GetMatchesYourVibeSeriesUseCase,
    private val getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(page: Int, pageSize: Int): List<PosterMedia> {

        val matchesYourVibeMovies = getMatchesYourVibeMovies.invoke(page = page, limit = pageSize)
        val matchesYourVibeSeries = getMatchesYourVibeSeries(page = page, limit = pageSize)

        return (matchesYourVibeMovies.map { movie ->
            movie.toShowMorePoster(
                getMovieGenresUseCase(
                    movie.genresID
                ).map { it.title })
        } + matchesYourVibeSeries.map { series ->
            series.toShowMorePoster(
                getSeriesGenresUseCase(
                    series.genreIDs
                ).map { it.title })
        })
            .distinctBy { it.id }
    }

    override fun getSectionType() = CategoryMediaSectionType.MATCHES_YOUR_VIBES
}