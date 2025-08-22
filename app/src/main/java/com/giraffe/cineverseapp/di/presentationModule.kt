package com.giraffe.cineverseapp.di

import com.giraffe.media.movie.usecase.GetMoviesByGenreIdsUseCase
import com.giraffe.media.movie.usecase.GetMoviesByKeywordsIdUseCase
import com.giraffe.media.movie.usecase.GetMoviesBySortUseCase
import com.giraffe.media.movie.usecase.matchesYourVibe.GetMatchesYourVibeMoviesUseCase
import com.giraffe.media.movie.usecase.recentlyReleased.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.movie.usecase.recentlyViewed.ObserveRecentlyViewedMoviesUseCase
import com.giraffe.media.movie.usecase.upcoming.GetUpcomingMoviesUseCase
import com.giraffe.media.series.usecase.GetSeriesByGenreIdsUseCase
import com.giraffe.media.series.usecase.GetSeriesByKeywordsIdUseCase
import com.giraffe.media.series.usecase.GetSeriesBySortUseCase
import com.giraffe.media.series.usecase.matchesYourVibe.GetMatchesYourVibeSeriesUseCase
import com.giraffe.media.series.usecase.recentlyReleased.GetRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.recentlyViewed.ObserveRecentlyViewedSeriesUseCase
import com.giraffe.media.series.usecase.topRated.GetTopRatedSeriesUseCase
import com.giraffe.presentation.home.screen.categoryMedia.CategoryMediaFactory
import com.giraffe.presentation.home.screen.categoryMedia.strategy.BasedOnTrueEventsStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.CinematicMasterpiecesStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.FamilyNightPicksStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.FeelGoodPreferencesStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.LateNightThrillsStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.MatchesYourVibesStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.MindBendingStoriesStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.RecentlyReleasedStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.RecentlyViewedStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.TopRatedTvShowsStrategy
import com.giraffe.presentation.home.screen.categoryMedia.strategy.UpcomingMoviesStrategy
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
    ): RecentlyReleasedStrategy =
        RecentlyReleasedStrategy(
            getRecentlyReleasedMovies,
            getRecentlyReleasedSeries
        )

    @Provides
    @Singleton
    fun provideTopRatedTvShowsStrategy(
        getTopRatedSeries: GetTopRatedSeriesUseCase,
    ): TopRatedTvShowsStrategy = TopRatedTvShowsStrategy(getTopRatedSeries)

    @Provides
    @Singleton
    fun provideUpcomingMoviesStrategy(
        getUpcomingMovies: GetUpcomingMoviesUseCase,
    ): UpcomingMoviesStrategy = UpcomingMoviesStrategy(getUpcomingMovies)

    @Provides
    @Singleton
    fun provideRecentlyViewedStrategy(
        observeRecentlyViewedMoviesUseCase: ObserveRecentlyViewedMoviesUseCase,
        observeRecentlyViewedSeriesUseCase: ObserveRecentlyViewedSeriesUseCase
    ): RecentlyViewedStrategy =
        RecentlyViewedStrategy(
            observeRecentlyViewedMoviesUseCase,
            observeRecentlyViewedSeriesUseCase
        )

    @Provides
    @Singleton
    fun provideMatchesYourVibesStrategy(
        getMatchesYourVibeMovies: GetMatchesYourVibeMoviesUseCase,
        getMatchesYourVibeSeries: GetMatchesYourVibeSeriesUseCase
    ): MatchesYourVibesStrategy =
        MatchesYourVibesStrategy(
            getMatchesYourVibeMovies,
            getMatchesYourVibeSeries
        )

    @Provides
    @Singleton
    fun provideLateNightThrillsStrategy(
        getSeriesByGenresUseCase: GetSeriesByGenreIdsUseCase,
        getMoviesByGenresUseCase: GetMoviesByGenreIdsUseCase,
    ): LateNightThrillsStrategy =
        LateNightThrillsStrategy(
            getSeriesByGenresUseCase,
            getMoviesByGenresUseCase
        )

    @Provides
    @Singleton
    fun provideFamilyNightPicksStrategy(
        getSeriesByGenresUseCase: GetSeriesByGenreIdsUseCase,
        getMoviesByGenresUseCase: GetMoviesByGenreIdsUseCase
    ): FamilyNightPicksStrategy =
        FamilyNightPicksStrategy(
            getSeriesByGenresUseCase,
            getMoviesByGenresUseCase
        )

    @Provides
    @Singleton
    fun provideMindBendingStoriesStrategy(
        getSeriesByGenresUseCase: GetSeriesByGenreIdsUseCase,
        getMoviesByGenresUseCase: GetMoviesByGenreIdsUseCase,
    ): MindBendingStoriesStrategy =
        MindBendingStoriesStrategy(
            getSeriesByGenresUseCase,
            getMoviesByGenresUseCase
        )

    @Provides
    @Singleton
    fun provideBasedOnTrueEventsStrategy(
        getSeriesByKeywordsIdUseCase: GetSeriesByKeywordsIdUseCase,
        getMoviesByKeywordsIdUseCase: GetMoviesByKeywordsIdUseCase,
    ): BasedOnTrueEventsStrategy =
        BasedOnTrueEventsStrategy(
            getSeriesByKeywordsIdUseCase,
            getMoviesByKeywordsIdUseCase,
        )

    @Provides
    @Singleton
    fun provideCinematicMasterpiecesStrategy(
        getSeriesBySortUseCase: GetSeriesBySortUseCase,
        getMoviesBySortUseCase: GetMoviesBySortUseCase,
    ): CinematicMasterpiecesStrategy =
        CinematicMasterpiecesStrategy(
            getSeriesBySortUseCase,
            getMoviesBySortUseCase,
        )


    @Provides
    @Singleton
    fun provideFeelGoodPreferencesStrategy(
        getSeriesByGenresUseCase: GetSeriesByGenreIdsUseCase,
        getMoviesByGenresUseCase: GetMoviesByGenreIdsUseCase,
    ): FeelGoodPreferencesStrategy =
        FeelGoodPreferencesStrategy(
            getSeriesByGenresUseCase,
            getMoviesByGenresUseCase,
        )

    @Provides
    @Singleton
    fun provideShowMoreFactory(
        recentlyReleasedStrategy: RecentlyReleasedStrategy,
        topRatedTvShowsStrategy: TopRatedTvShowsStrategy,
        upcomingMoviesStrategy: UpcomingMoviesStrategy,
        recentlyViewedStrategy: RecentlyViewedStrategy,
        matchesYourVibesStrategy: MatchesYourVibesStrategy,
        lateNightThrillsStrategy: LateNightThrillsStrategy,
        familyNightPicksStrategy: FamilyNightPicksStrategy,
        mindBendingStoriesStrategy: MindBendingStoriesStrategy,
        basedOnTrueEventsStrategy: BasedOnTrueEventsStrategy,
        cinematicMasterpiecesStrategy: CinematicMasterpiecesStrategy,
        feelGoodPreferencesStrategy: FeelGoodPreferencesStrategy
    ): CategoryMediaFactory =
        CategoryMediaFactory(
            recentlyReleasedStrategy,
            topRatedTvShowsStrategy,
            upcomingMoviesStrategy,
            recentlyViewedStrategy,
            matchesYourVibesStrategy,
            lateNightThrillsStrategy,
            familyNightPicksStrategy,
            mindBendingStoriesStrategy,
            basedOnTrueEventsStrategy,
            cinematicMasterpiecesStrategy,
            feelGoodPreferencesStrategy
        )
}
