package com.giraffe.home.screen.show_more

import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase

interface MovieStrategy {
    suspend fun loadData()
    fun getTitle(): String
}

class TopRatedMoviesStrategy(
    private val getTopRatedTvShowsUsaCase: GetTopRatedSeriesUseCase,
) : MovieStrategy {
    override suspend fun loadData() {
        getTopRatedTvShowsUsaCase.invoke(
            page = 1,
            limit = 10
        )
    }

    override fun getTitle(): String {
        return "Top Rated Movies"
    }

}

class RecentlyMoviesStrategy(
    private val getTopRatedTvShowsUsaCase: GetTopRatedSeriesUseCase,
) : MovieStrategy {
    override suspend fun loadData() {
        getTopRatedTvShowsUsaCase.invoke(
            page = 1,
            limit = 10
        )
    }

    override fun getTitle(): String {
        return "Top Rated Movies"
    }

}

class MovieFactory(
    private val topRatedMoviesStrategy: TopRatedMoviesStrategy,
    private val recentlyMoviesStrategy: RecentlyMoviesStrategy
) {
    fun create(sectionType: String?): MovieStrategy {
        return when (sectionType) {
            "top rated movies" -> topRatedMoviesStrategy
            "recently movies" -> recentlyMoviesStrategy
            else -> {}
        }
    }
}