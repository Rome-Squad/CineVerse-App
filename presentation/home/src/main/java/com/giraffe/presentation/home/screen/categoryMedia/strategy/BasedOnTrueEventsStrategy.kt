package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.movie.usecase.GetMoviesByKeywordsIdUseCase
import com.giraffe.media.movie.usecase.genre.GetMoviesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetSeriesByKeywordsIdUseCase
import com.giraffe.media.series.usecase.genre.GetSeriesGenresByIdsUseCase
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

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
        return withContext(Dispatchers.IO) {
            val keywordIdForTrueEvents = 9672
            val moviesResult = async {
                getMoviesByKeywordsIdUseCase(
                    page = page,
                    keywords = keywordIdForTrueEvents
                ).map { movie ->
                    async {
                        movie.toShowMorePoster(
                            getMoviesGenresByIdsUseCase(movie.genresID).map { it.title }
                        )
                    }
                }
            }

            val seriesResult = async {
                getSeriesByKeywordsIdUseCase(
                    page = page,
                    keywords = keywordIdForTrueEvents
                ).map { series ->
                    async {
                        series.toShowMorePoster(
                            getSeriesGenresByIdsUseCase(series.genreIDs).map { it.title }
                        )
                    }
                }
            }

            moviesResult.await().awaitAll() + seriesResult.await().awaitAll()
        }
    }

    override fun getSectionType() = CategoryMediaSectionType.BASED_ON_TRUE_EVENTS

}