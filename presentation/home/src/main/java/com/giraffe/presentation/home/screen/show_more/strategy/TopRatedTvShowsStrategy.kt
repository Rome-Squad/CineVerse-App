package com.giraffe.presentation.home.screen.show_more.strategy

import com.giraffe.media.series.usecase.genre.GetSeriesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase
import com.giraffe.presentation.home.navigation.home.routes.MixedMediaSectionType
import com.giraffe.presentation.home.screen.show_more.MixedMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster

class TopRatedTvShowsStrategy(
    private val getTopRatedSeries: GetTopRatedSeriesUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
) : MixedMediaStrategy {
    override suspend fun loadData(page: Int, pageSize: Int) =
        getTopRatedSeries(page = page, limit = pageSize).map { series ->
            series.toShowMorePoster(
                getSeriesGenresUseCase(series.genreIDs).map { it.title })
        }

    override fun getSectionType() = MixedMediaSectionType.TOP_RATED_TV_SHOWS
}