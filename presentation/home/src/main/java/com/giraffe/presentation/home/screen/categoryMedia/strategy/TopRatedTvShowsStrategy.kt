package com.giraffe.presentation.home.screen.categoryMedia.strategy

import com.giraffe.media.entity.Genre
import com.giraffe.media.series.usecase.topRated.GetTopRatedSeriesUseCase
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaStrategy
import com.giraffe.presentation.home.utils.toShowMorePoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TopRatedTvShowsStrategy(
    private val getTopRatedSeries: GetTopRatedSeriesUseCase
) : CategoryMediaStrategy {
    override suspend fun loadData(
        page: Int,
        pageSize: Int,
        seriesGenres: List<Genre>,
        moviesGenres: List<Genre>
    ) = withContext(Dispatchers.IO) {
        getTopRatedSeries(page = page, limit = pageSize).map { series ->
                series.toShowMorePoster(
                    seriesGenres.filter { it.id in series.genreIDs }.map { it.title }
                )
        }
    }

    override fun getSectionType() = CategoryMediaSectionType.TOP_RATED_TV_SHOWS
}