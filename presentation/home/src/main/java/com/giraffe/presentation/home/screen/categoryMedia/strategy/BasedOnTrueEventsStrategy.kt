package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.usecase.GetMoviesByKeywordsIdUseCase
import com.giraffe.media.series.usecase.GetSeriesByKeywordsIdUseCase
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class BasedOnTrueEventsStrategy(
    private val getSeriesByKeywordsIdUseCase: GetSeriesByKeywordsIdUseCase,
    private val getMoviesByKeywordsIdUseCase: GetMoviesByKeywordsIdUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(
        page: Int,
        pageSize: Int,
        seriesGenres: List<Genre>,
        moviesGenres: List<Genre>
    ): List<PosterMedia> {
        return withContext(Dispatchers.IO) {
            val keywordIdForTrueEvents = 9672
            val moviesResult = async {
                getMoviesByKeywordsIdUseCase(
                    page = page,
                    keywords = keywordIdForTrueEvents
                ).map { movie ->
                    movie.toShowMorePoster(
                        moviesGenres.filter { it.id in movie.genresID }.map { it.title }
                    )
                }
            }

            val seriesResult = async {
                getSeriesByKeywordsIdUseCase(
                    page = page,
                    keywords = keywordIdForTrueEvents
                ).map { series ->
                    series.toShowMorePoster(
                        seriesGenres.filter { it.id in series.genreIDs }.map { it.title }
                    )
                }
            }

            moviesResult.await() + seriesResult.await()
        }
    }

    override fun getSectionType() = CategoryMediaSectionType.BASED_ON_TRUE_EVENTS

}