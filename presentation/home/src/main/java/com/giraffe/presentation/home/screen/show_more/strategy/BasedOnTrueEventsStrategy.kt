package com.giraffe.presentation.home.screen.show_more.strategy

import com.giraffe.media.movie.usecase.GetMoviesByKeywordsIdUseCase
import com.giraffe.media.movie.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetSeriesByKeywordsIdUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.presentation.home.model.ShowMorePoster
import com.giraffe.presentation.home.navigation.home.routes.MixedMediaSectionType
import com.giraffe.presentation.home.screen.show_more.MixedMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster

class BasedOnTrueEventsStrategy(
    private val getSeriesByKeywordsIdUseCase: GetSeriesByKeywordsIdUseCase,
    private val getSeriesGenresByIdsUseCase: GetSeriesGenresByIdsUseCase,
    private val getMoviesByKeywordsIdUseCase: GetMoviesByKeywordsIdUseCase,
    private val getMoviesGenresByIdsUseCase: GetMoviesGenresByIdsUseCase
) : MixedMediaStrategy {
    override suspend fun loadData(
        page: Int,
        pageSize: Int
    ): List<ShowMorePoster> {
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

    override fun getSectionType() = MixedMediaSectionType.BASED_ON_TRUE_EVENTS

}