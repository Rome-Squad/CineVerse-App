package com.giraffe.cineverseapp.di

import com.giraffe.media.explore.usecase.*
import com.giraffe.media.movies.usecase.*
import com.giraffe.media.person.usecase.*
import com.giraffe.media.series.usecase.*
import com.giraffe.user.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    // Explore UseCases
    @Provides
    @Singleton
    fun provideGetSearchKeywordsUseCase(impl: GetSearchKeywordsUseCase) = impl
    @Provides
    @Singleton
    fun provideInsertSearchKeywordUseCase(impl: InsertSearchKeywordUseCase) = impl
    @Provides
    @Singleton
    fun provideDeleteKeywordUseCase(impl: DeleteKeywordUseCase) = impl
    @Provides
    @Singleton
    fun provideClearSearchHistoryUseCase(impl: ClearSearchHistoryUseCase) = impl

    // Series UseCases
    @Provides
    @Singleton
    fun provideClearRecentSeriesUseCase(impl: ClearRecentSeriesUseCase) = impl
    @Provides
    @Singleton
    fun provideGetRecentSeriesUseCase(impl: GetRecentSeriesUseCase) = impl
    @Provides
    @Singleton
    fun provideGetSeriesGenresUseCase(impl: GetSeriesGenresUseCase) = impl
    @Provides
    @Singleton
    fun provideSearchSeriesByNameUseCase(impl: SearchSeriesByNameUseCase) = impl
    @Provides
    @Singleton
    fun provideStoreRecentSeriesUseCase(impl: StoreRecentSeriesUseCase) = impl
    @Provides
    @Singleton
    fun provideGetRecommendedSeriesUseCase(impl: GetRecommendedSeriesUseCase) = impl
    @Provides
    @Singleton
    fun provideGetSeriesDetailsUseCase(impl: GetSeriesDetailsUseCase) = impl
    @Provides
    @Singleton
    fun provideGetLastSeasonsUseCase(impl: GetLastSeasonsUseCase) = impl
    @Provides
    @Singleton
    fun provideGetSeriesReviewsUseCase(impl: GetSeriesReviewsUseCase) = impl
    @Provides
    @Singleton
    fun provideGetSeriesByGenresUseCase(impl: GetSeriesByGenresUseCase) = impl
    @Provides
    @Singleton
    fun provideGetSeriesGenresByIdsUseCase(impl: GetSeriesGenresByIdsUseCase) = impl
    @Provides
    @Singleton
    fun provideGetPopularitySeriesUseCase(impl: GetPopularitySeriesUseCase) = impl
    @Provides
    @Singleton
    fun provideGetRecentlyReleasedSeriesUseCase(impl: GetRecentlyReleasedSeriesUseCase) = impl
    @Provides
    @Singleton
    fun provideGetTopRatedSeriesUseCase(impl: GetTopRatedSeriesUseCase) = impl

    // Movie UseCases
    @Provides
    @Singleton
    fun provideSearchMovieByNameUseCase(impl: SearchMovieByNameUseCase) = impl
    @Provides
    @Singleton
    fun provideGetMoviesGenresUseCase(impl: GetMoviesGenresUseCase) = impl
    @Provides
    @Singleton
    fun provideGetMoviesByGenresUseCase(impl: GetMoviesByGenresUseCase) = impl
    @Provides
    @Singleton
    fun provideInsertMoviesUseCase(impl: InsertMoviesUseCase) = impl
    @Provides
    @Singleton
    fun provideInsertGenresUseCase(impl: InsertGenresUseCase) = impl
    @Provides
    @Singleton
    fun provideGetRecommendedMovieUseCase(impl: GetRecommendedMovieUseCase) = impl
    @Provides
    @Singleton
    fun provideClearCacheUseCase(impl: ClearCacheUseCase) = impl
    @Provides
    @Singleton
    fun provideSetMovieRecentUseCase(impl: SetMovieRecentUseCase) = impl
    @Provides
    @Singleton
    fun provideGetRecentlyMoviesUseCase(impl: GetRecentlyMoviesUseCase) = impl
    @Provides
    @Singleton
    fun provideClearRecentlyMoviesUseCase(impl: ClearRecentlyMoviesUseCase) = impl
    @Provides
    @Singleton
    fun provideGetMovieGenresUseCase(impl: GetMovieGenresUseCase) = impl
    @Provides
    @Singleton
    fun provideGetRecentlyReleasedMoviesUseCase(impl: GetRecentlyReleasedMoviesUseCase) = impl
    @Provides
    @Singleton
    fun provideGetPopularityMoviesUseCase(impl: GetPopularityMoviesUseCase) = impl
    @Provides
    @Singleton
    fun provideGetUpcomingMoviesUseCase(impl: GetUpcomingMoviesUseCase) = impl

    @Provides
    @Singleton
    fun provideGetMovieDetailsUseCase(impl: GetMovieDetailsUseCase) = impl
    @Provides
    @Singleton
    fun provideGetMovieReviewsUseCase(impl: GetMovieReviewsUseCase) = impl
    @Provides
    @Singleton
    fun provideAddMovieRatingUseCase(impl: AddMovieRatingUseCase) = impl
    @Provides
    @Singleton
    fun provideGetUserMovieRatingUseCase(impl: GetUserMovieRatingUseCase) = impl

    // Person UseCases
    @Provides
    @Singleton
    fun provideClearRecentPeopleUseCase(impl: ClearRecentPeopleUseCase) = impl
    @Provides
    @Singleton
    fun provideGetRecentPeopleUseCase(impl: GetRecentPeopleUseCase) = impl
    @Provides
    @Singleton
    fun provideSearchPeopleByNameUseCase(impl: SearchPeopleByNameUseCase) = impl
    @Provides
    @Singleton
    fun provideStoreRecentPersonUseCase(impl: StoreRecentPersonUseCase) = impl
    @Provides
    @Singleton
    fun provideGetPeopleByMovieIdUseCase(impl: GetPeopleByMovieIdUseCase) = impl
    @Provides
    @Singleton
    fun provideGetPeopleBySeriesIdUseCase(impl: GetPeopleBySeriesIdUseCase) = impl
    @Provides
    @Singleton
    fun provideGetPersonDetailsUseCase(impl: GetPersonDetailsUseCase) = impl
    @Provides
    @Singleton
    fun provideGetPeopleMediaCreditsUseCase(impl: GetPeopleMediaCreditsUseCase) = impl
    @Provides
    @Singleton
    fun provideGetPersonImagesUseCase(impl: GetPersonImagesUseCase) = impl

    // Auth UseCase
    @Provides
    @Singleton
    fun provideLoginUseCase(impl: LoginUseCase) = impl
}
