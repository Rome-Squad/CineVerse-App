package com.giraffe.cineverseapp.di

import com.giraffe.media.movie.usecase.GetMatchesYourVibeMoviesUseCase
import com.giraffe.media.movie.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.movie.usecase.GetRecentlyViewedMoviesUseCase
import com.giraffe.media.movie.usecase.GetUpcomingMoviesUseCase
import com.giraffe.media.series.usecase.GetMatchesYourVibeSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentlyViewedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase
import com.giraffe.presentation.home.screen.show_more.MatchesYourVibesStrategy
import com.giraffe.presentation.home.screen.show_more.RecentlyReleasedStrategy
import com.giraffe.presentation.home.screen.show_more.RecentlyViewedStrategy
import com.giraffe.presentation.home.screen.show_more.ShowMoreFactory
import com.giraffe.presentation.home.screen.show_more.TopRatedTvShowsStrategy
import com.giraffe.presentation.home.screen.show_more.UpcomingMoviesStrategy
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
        getRecentlyReleasedSeries: GetRecentlyReleasedSeriesUseCase,
        getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
        getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
    ): RecentlyReleasedStrategy =
        RecentlyReleasedStrategy(
            getRecentlyReleasedMovies,
            getRecentlyReleasedSeries,
            getMovieGenresUseCase,
            getSeriesGenresUseCase
        )

    @Provides
    @Singleton
    fun provideTopRatedTvShowsStrategy(
        getTopRatedSeries: GetTopRatedSeriesUseCase,
        getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
    ): TopRatedTvShowsStrategy = TopRatedTvShowsStrategy(getTopRatedSeries, getSeriesGenresUseCase)

    @Provides
    @Singleton
    fun provideUpcomingMoviesStrategy(
        getUpcomingMovies: GetUpcomingMoviesUseCase,
        getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
    ): UpcomingMoviesStrategy = UpcomingMoviesStrategy(getUpcomingMovies, getMovieGenresUseCase)

    @Provides
    @Singleton
    fun provideRecentlyViewedStrategy(
        getRecentlyViewedMovies: GetRecentlyViewedMoviesUseCase,
        getRecentlySeriesUseCase: GetRecentlyViewedSeriesUseCase,
        getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
        getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
    ): RecentlyViewedStrategy =
        RecentlyViewedStrategy(
            getRecentlyViewedMovies,
            getRecentlySeriesUseCase,
            getMovieGenresUseCase,
            getSeriesGenresUseCase
        )

    @Provides
    @Singleton
    fun provideMatchesYourVibesStrategy(
        getMatchesYourVibeMovies: GetMatchesYourVibeMoviesUseCase,
        getMatchesYourVibeSeries: GetMatchesYourVibeSeriesUseCase,
        getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
        getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
    ): MatchesYourVibesStrategy =
        MatchesYourVibesStrategy(
            getMatchesYourVibeMovies,
            getMatchesYourVibeSeries,
            getMovieGenresUseCase,
            getSeriesGenresUseCase
        )

    @Provides
    @Singleton
    fun provideShowMoreFactory(
        recentlyReleasedStrategy: RecentlyReleasedStrategy,
        topRatedTvShowsStrategy: TopRatedTvShowsStrategy,
        upcomingMoviesStrategy: UpcomingMoviesStrategy,
        recentlyViewedStrategy: RecentlyViewedStrategy,
        matchesYourVibesStrategy: MatchesYourVibesStrategy
    ): ShowMoreFactory =
        ShowMoreFactory(
            recentlyReleasedStrategy,
            topRatedTvShowsStrategy,
            upcomingMoviesStrategy,
            recentlyViewedStrategy,
            matchesYourVibesStrategy
        )
}
