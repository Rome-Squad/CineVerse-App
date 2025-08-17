package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.movie.usecase.GetMoviesByKeywordsIdUseCase
import com.giraffe.media.movie.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetSeriesByKeywordsIdUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster

class BasedOnTrueEventsStrategy(
    private val getSeriesByKeywordsIdUseCase: GetSeriesByKeywordsIdUseCase,
    private val getSeriesGenresByIdsUseCase: GetSeriesGenresByIdsUseCase,
    private val getMoviesByKeywordsIdUseCase: GetMoviesByKeywordsIdUseCase,
    private val getMoviesGenresByIdsUseCase: GetMoviesGenresByIdsUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(
        page: Int,
        pageSize: Int
    ): List<PosterMedia> {
        val keywordIdForTrueEvents = 9672
        val moviesResult =
            getMoviesByKeywordsIdUseCase(
                page = page,
                keywords = keywordIdForTrueEvents
            ).map { movie ->
                movie.toShowMorePoster(
                    getMoviesGenresByIdsUseCase(movie.genresID).map { it.title }
                )
            }
        val seriesResult =
            getSeriesByKeywordsIdUseCase(
                page = page,
                keywords = keywordIdForTrueEvents
            ).map { series ->
                series.toShowMorePoster(
                    getSeriesGenresByIdsUseCase(series.genreIDs).map { it.title }
                )
            }
        return moviesResult + seriesResult
    }

    override fun getSectionType() = CategoryMediaSectionType.BASED_ON_TRUE_EVENTS

}