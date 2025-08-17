package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.series.usecase.genre.GetSeriesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster

class TopRatedTvShowsStrategy(
    private val getTopRatedSeries: GetTopRatedSeriesUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(page: Int, pageSize: Int) =
        getTopRatedSeries(page = page, limit = pageSize).map { series ->
            series.toShowMorePoster(
                getSeriesGenresUseCase(series.genreIDs).map { it.title })
        }

    override fun getSectionType() = CategoryMediaSectionType.TOP_RATED_TV_SHOWS
}