package com.giraffe.cineverseapp.di

import com.giraffe.media.movie.usecase.GetMoviesByGenreIdsUseCase
import com.giraffe.media.movie.usecase.GetMoviesByKeywordsIdUseCase
import com.giraffe.media.movie.usecase.GetMoviesBySortUseCase
import com.giraffe.media.movie.usecase.genre.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.matchesYourVibe.GetMatchesYourVibeMoviesUseCase
import com.giraffe.media.movie.usecase.recentlyReleased.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.movie.usecase.recentlyViewed.ObserveRecentlyViewedMoviesUseCase
import com.giraffe.media.movie.usecase.upcoming.GetUpcomingMoviesUseCase
import com.giraffe.media.series.usecase.GetMatchesYourVibeSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentlyViewedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesByGenreIdsUseCase
import com.giraffe.media.series.usecase.GetSeriesByKeywordsIdUseCase
import com.giraffe.media.series.usecase.GetSeriesBySortUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase
import com.giraffe.presentation.home.screen.show_more.MixedMediaFactory
import com.giraffe.presentation.home.screen.show_more.strategy.BasedOnTrueEventsStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.CinematicMasterpiecesStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.FamilyNightPicksStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.FeelGoodPreferencesStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.LateNightThrillsStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.MatchesYourVibesStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.MindBendingStoriesStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.RecentlyReleasedStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.RecentlyViewedStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.TopRatedTvShowsStrategy
import com.giraffe.presentation.home.screen.show_more.strategy.UpcomingMoviesStrategy
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
        observeRecentlyViewedMoviesUseCase: ObserveRecentlyViewedMoviesUseCase,
        getRecentlySeriesUseCase: GetRecentlyViewedSeriesUseCase,
        getMovieGenresUseCase: GetMoviesGenresByIdsUseCase,
        getSeriesGenresUseCase: GetSeriesGenresByIdsUseCase
    ): RecentlyViewedStrategy =
        RecentlyViewedStrategy(
            observeRecentlyViewedMoviesUseCase,
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
    fun provideLateNightThrillsStrategy(
        getSeriesByGenresUseCase: GetSeriesByGenreIdsUseCase,
        getSeriesGenresByIdsUseCase: GetSeriesGenresByIdsUseCase,
        getMoviesByGenresUseCase: GetMoviesByGenreIdsUseCase,
        getMoviesGenresByIdsUseCase: GetMoviesGenresByIdsUseCase
    ): LateNightThrillsStrategy =
        LateNightThrillsStrategy(
            getSeriesByGenresUseCase,
            getSeriesGenresByIdsUseCase,
            getMoviesByGenresUseCase,
            getMoviesGenresByIdsUseCase
        )

    @Provides
    @Singleton
    fun provideFamilyNightPicksStrategy(
        getSeriesByGenresUseCase: GetSeriesByGenreIdsUseCase,
        getSeriesGenresByIdsUseCase: GetSeriesGenresByIdsUseCase,
        getMoviesByGenresUseCase: GetMoviesByGenreIdsUseCase,
        getMoviesGenresByIdsUseCase: GetMoviesGenresByIdsUseCase
    ): FamilyNightPicksStrategy =
        FamilyNightPicksStrategy(
            getSeriesByGenresUseCase,
            getSeriesGenresByIdsUseCase,
            getMoviesByGenresUseCase,
            getMoviesGenresByIdsUseCase
        )

    @Provides
    @Singleton
    fun provideMindBendingStoriesStrategy(
        getSeriesByGenresUseCase: GetSeriesByGenreIdsUseCase,
        getSeriesGenresByIdsUseCase: GetSeriesGenresByIdsUseCase,
        getMoviesByGenresUseCase: GetMoviesByGenreIdsUseCase,
        getMoviesGenresByIdsUseCase: GetMoviesGenresByIdsUseCase
    ): MindBendingStoriesStrategy =
        MindBendingStoriesStrategy(
            getSeriesByGenresUseCase,
            getSeriesGenresByIdsUseCase,
            getMoviesByGenresUseCase,
            getMoviesGenresByIdsUseCase
        )

    @Provides
    @Singleton
    fun provideBasedOnTrueEventsStrategy(
        getSeriesByKeywordsIdUseCase: GetSeriesByKeywordsIdUseCase,
        getSeriesGenresByIdsUseCase: GetSeriesGenresByIdsUseCase,
        getMoviesByKeywordsIdUseCase: GetMoviesByKeywordsIdUseCase,
        getMoviesGenresByIdsUseCase: GetMoviesGenresByIdsUseCase
    ): BasedOnTrueEventsStrategy =
        BasedOnTrueEventsStrategy(
            getSeriesByKeywordsIdUseCase,
            getSeriesGenresByIdsUseCase,
            getMoviesByKeywordsIdUseCase,
            getMoviesGenresByIdsUseCase
        )

    @Provides
    @Singleton
    fun provideCinematicMasterpiecesStrategy(
        getSeriesBySortUseCase: GetSeriesBySortUseCase,
        getSeriesGenresByIdsUseCase: GetSeriesGenresByIdsUseCase,
        getMoviesBySortUseCase: GetMoviesBySortUseCase,
        getMoviesGenresByIdsUseCase: GetMoviesGenresByIdsUseCase
    ): CinematicMasterpiecesStrategy =
        CinematicMasterpiecesStrategy(
            getSeriesBySortUseCase,
            getSeriesGenresByIdsUseCase,
            getMoviesBySortUseCase,
            getMoviesGenresByIdsUseCase
        )


    @Provides
    @Singleton
    fun provideFeelGoodPreferencesStrategy(
        getSeriesByGenresUseCase: GetSeriesByGenreIdsUseCase,
        getSeriesGenresByIdsUseCase: GetSeriesGenresByIdsUseCase,
        getMoviesByGenresUseCase: GetMoviesByGenreIdsUseCase,
        getMoviesGenresByIdsUseCase: GetMoviesGenresByIdsUseCase
    ): FeelGoodPreferencesStrategy =
        FeelGoodPreferencesStrategy(
            getSeriesByGenresUseCase,
            getSeriesGenresByIdsUseCase,
            getMoviesByGenresUseCase,
            getMoviesGenresByIdsUseCase
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
    ): MixedMediaFactory =
        MixedMediaFactory(
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
