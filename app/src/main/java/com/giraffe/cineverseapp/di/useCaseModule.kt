package com.giraffe.cineverseapp.di

import com.giraffe.media.explore.usecase.ClearSearchHistoryUseCase
import com.giraffe.media.explore.usecase.DeleteKeywordUseCase
import com.giraffe.media.explore.usecase.GetSearchKeywordsUseCase
import com.giraffe.media.explore.usecase.InsertSearchKeywordUseCase
import com.giraffe.media.movies.usecase.AddMovieRatingUseCase
import com.giraffe.media.movies.usecase.ClearCacheUseCase
import com.giraffe.media.movies.usecase.ClearRecentlyMoviesUseCase
import com.giraffe.media.movies.usecase.DeleteMovieRatingUseCase
import com.giraffe.media.movies.usecase.GetMovieDetailsUseCase
import com.giraffe.media.movies.usecase.GetMovieGenresUseCase
import com.giraffe.media.movies.usecase.GetMovieReviewsUseCase
import com.giraffe.media.movies.usecase.GetMoviesByGenresUseCase
import com.giraffe.media.movies.usecase.GetMoviesGenresUseCase
import com.giraffe.media.movies.usecase.GetRecentlyMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecommendedMovieUseCase
import com.giraffe.media.movies.usecase.GetUserMovieRatingUseCase
import com.giraffe.media.movies.usecase.InsertGenresUseCase
import com.giraffe.media.movies.usecase.InsertMoviesUseCase
import com.giraffe.media.movies.usecase.SearchMovieByNameUseCase
import com.giraffe.media.movies.usecase.SetMovieRecentUseCase
import com.giraffe.media.person.usecase.ClearRecentPeopleUseCase
import com.giraffe.media.person.usecase.GetPeopleByMovieIdUseCase
import com.giraffe.media.person.usecase.GetPeopleBySeriesIdUseCase
import com.giraffe.media.person.usecase.GetPeopleMediaCreditsUseCase
import com.giraffe.media.person.usecase.GetPersonDetailsUseCase
import com.giraffe.media.person.usecase.GetPersonImagesUseCase
import com.giraffe.media.person.usecase.GetRecentPeopleUseCase
import com.giraffe.media.person.usecase.SearchPeopleByNameUseCase
import com.giraffe.media.person.usecase.StoreRecentPersonUseCase
import com.giraffe.media.series.usecase.AddSeriesRatingUseCase
import com.giraffe.media.series.usecase.ClearRecentSeriesUseCase
import com.giraffe.media.series.usecase.DeleteSeriesRatingUseCase
import com.giraffe.media.series.usecase.GetLastSeasonsUseCase
import com.giraffe.media.series.usecase.GetRecentSeriesUseCase
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesByGenresUseCase
import com.giraffe.media.series.usecase.GetSeriesDetailsUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresUseCase
import com.giraffe.media.series.usecase.GetSeriesReviewsUseCase
import com.giraffe.media.series.usecase.GetUserSeriesRatingUseCase
import com.giraffe.media.series.usecase.SearchSeriesByNameUseCase
import com.giraffe.media.series.usecase.StoreRecentSeriesUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    // Explore UseCases
    singleOf(::GetSearchKeywordsUseCase)
    singleOf(::InsertSearchKeywordUseCase)
    singleOf(::DeleteKeywordUseCase)
    singleOf(::ClearSearchHistoryUseCase)

    // Series UseCases
    singleOf(::ClearRecentSeriesUseCase)
    singleOf(::GetRecentSeriesUseCase)
    singleOf(::GetSeriesGenresUseCase)
    singleOf(::SearchSeriesByNameUseCase)
    singleOf(::StoreRecentSeriesUseCase)
    singleOf(::GetRecommendedSeriesUseCase)
    singleOf(::GetSeriesDetailsUseCase)
    singleOf(::GetLastSeasonsUseCase)
    singleOf(::GetSeriesReviewsUseCase)
    singleOf(::GetSeriesByGenresUseCase)
    singleOf(::GetSeriesGenresByIdsUseCase)
    singleOf(::AddSeriesRatingUseCase)
    singleOf(::GetUserSeriesRatingUseCase)
    singleOf(::DeleteSeriesRatingUseCase)

    // Movie UseCases
    singleOf(::SearchMovieByNameUseCase)
    singleOf(::GetMoviesGenresUseCase)
    singleOf(::GetMoviesByGenresUseCase)
    singleOf(::InsertMoviesUseCase)
    singleOf(::InsertGenresUseCase)
    singleOf(::GetRecommendedMovieUseCase)
    singleOf(::ClearCacheUseCase)
    singleOf(::SetMovieRecentUseCase)
    singleOf(::GetRecentlyMoviesUseCase)
    singleOf(::ClearRecentlyMoviesUseCase)
    singleOf(::GetMovieGenresUseCase)
    singleOf(::DeleteMovieRatingUseCase)

    // NEW: Add UseCases for Movie Details and Rating
    singleOf(::GetMovieDetailsUseCase)
    singleOf(::GetMovieReviewsUseCase)
    singleOf(::AddMovieRatingUseCase)
    singleOf(::GetUserMovieRatingUseCase)

    // Person UseCases
    singleOf(::ClearRecentPeopleUseCase)
    singleOf(::GetRecentPeopleUseCase)
    singleOf(::SearchPeopleByNameUseCase)
    singleOf(::StoreRecentPersonUseCase)
    singleOf(::GetPeopleByMovieIdUseCase)
    singleOf(::GetPeopleBySeriesIdUseCase)
    singleOf(::GetPersonDetailsUseCase)
    singleOf(::GetPeopleMediaCreditsUseCase)
    singleOf(::GetPersonImagesUseCase)
}