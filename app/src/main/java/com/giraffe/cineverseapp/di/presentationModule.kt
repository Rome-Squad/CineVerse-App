package com.giraffe.cineverseapp.di

import com.giraffe.presentation.home.screen.show_more.MatchesYourVibesStrategy
import com.giraffe.presentation.home.screen.show_more.RecentlyReleasedStrategy
import com.giraffe.presentation.home.screen.show_more.RecentlyViewedStrategy
import com.giraffe.presentation.home.screen.show_more.ShowMoreFactory
import com.giraffe.presentation.home.screen.show_more.TopRatedTvShowsStrategy
import com.giraffe.presentation.home.screen.show_more.UpcomingMoviesStrategy
import com.giraffe.media.movie.usecase.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.movie.usecase.GetRecentlyViewedMoviesUseCase
import com.giraffe.media.movie.usecase.GetRecommendedMovieUseCase
import com.giraffe.media.movie.usecase.GetUpcomingMoviesUseCase
import com.giraffe.media.series.usecase.GetRecentSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {
    @Provides
    @Singleton
    fun provideRecentlyReleasedStrategy(
        getRecentlyReleasedMovies: GetRecentlyReleasedMoviesUseCase,
        getRecentlyReleasedSeries: GetRecentlyReleasedSeriesUseCase
    ): RecentlyReleasedStrategy {
        return RecentlyReleasedStrategy(getRecentlyReleasedMovies, getRecentlyReleasedSeries)
    }

    @Provides
    @Singleton
    fun provideTopRatedTvShowsStrategy(
        getTopRatedSeries: GetTopRatedSeriesUseCase
    ): TopRatedTvShowsStrategy {
        return TopRatedTvShowsStrategy(getTopRatedSeries)
    }

    @Provides
    @Singleton
    fun provideUpcomingMoviesStrategy(
        getUpcomingMovies: GetUpcomingMoviesUseCase
    ): UpcomingMoviesStrategy {
        return UpcomingMoviesStrategy(getUpcomingMovies)
    }

    @Provides
    @Singleton
    fun provideRecentlyViewedStrategy(
        getRecentlyViewedMovies: GetRecentlyViewedMoviesUseCase,
        getRecentlySeriesUseCase: GetRecentSeriesUseCase
    ): RecentlyViewedStrategy {
        return RecentlyViewedStrategy(getRecentlyViewedMovies, getRecentlySeriesUseCase)
    }

    @Provides
    @Singleton
    fun provideMatchesYourVibesStrategy(
        getRecentlyViewedMovies: GetRecentlyViewedMoviesUseCase,
        getRecentlySeriesUseCase: GetRecentSeriesUseCase,
        getRecommendedMovie: GetRecommendedMovieUseCase,
        getRecommendedSeries: GetRecommendedSeriesUseCase
    ): MatchesYourVibesStrategy {
        return MatchesYourVibesStrategy(
            getRecentlyViewedMovies,
            getRecentlySeriesUseCase,
            getRecommendedMovie,
            getRecommendedSeries
        )
    }

    @Provides
    @Singleton
    fun provideShowMoreFactory(
        recentlyReleasedStrategy: RecentlyReleasedStrategy,
        topRatedTvShowsStrategy: TopRatedTvShowsStrategy,
        upcomingMoviesStrategy: UpcomingMoviesStrategy,
        recentlyViewedStrategy: RecentlyViewedStrategy,
        matchesYourVibesStrategy: MatchesYourVibesStrategy
    ): ShowMoreFactory {
        return ShowMoreFactory(
            recentlyReleasedStrategy,
            topRatedTvShowsStrategy,
            upcomingMoviesStrategy,
            recentlyViewedStrategy,
            matchesYourVibesStrategy
        )
    }
}
