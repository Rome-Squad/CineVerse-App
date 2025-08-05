package com.giraffe.media.movies.usecase

import javax.inject.Inject

class MovieUseCase @Inject constructor(
    val addGenresUseCase: AddGenresUseCase,
    val addMovieRatingUseCase: AddMovieRatingUseCase,
    val addMoviesUseCase: AddMoviesUseCase,
    val clearMovieCacheWithOutRecentViewedUseCase: ClearMovieCacheWithOutRecentViewedUseCase,
    val clearMoviesCacheUseCase: ClearMoviesCacheUseCase,
    val clearRecentlyMoviesUseCase: ClearRecentlyMoviesUseCase,
    val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    val getMoviesGenresByIdsUseCase: GetMoviesGenresByIdsUseCase,
    val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    val getMoviesByGenresUseCase: GetMoviesByGenresUseCase,
    val getMoviesGenresUseCase: GetMoviesGenresUseCase,
    val getPopularityMoviesUseCase: GetPopularityMoviesUseCase,
    val getRecentlyReleasedMoviesUseCase: GetRecentlyReleasedMoviesUseCase,
    val getRecentlyViewedMoviesUseCase: GetRecentlyViewedMoviesUseCase,
    val getRecommendedMovieUseCase: GetRecommendedMovieUseCase,
    val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    val getUserMovieRatingUseCase: GetUserMovieRatingUseCase,
    val searchMovieByNameUseCase: SearchMovieByNameUseCase,
    val setMovieRecentUseCase: SetMovieRecentUseCase,
)