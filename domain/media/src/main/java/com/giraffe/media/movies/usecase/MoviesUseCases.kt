package com.giraffe.media.movies.usecase

data class MoviesUseCases(
    val clearCacheUseCase: ClearCacheUseCase,
    val insertMoviesUseCase: InsertMoviesUseCase,
    val insertGenresUseCase: InsertGenresUseCase,
    val searchMovieByNameUseCase: SearchMovieByNameUseCase,
    val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
    val getMoviesGenresUseCase: GetMoviesGenresUseCase,
    val setMovieRecentUseCase: SetMovieRecentUseCase,
    val getRecentlyMovies: GetRecentlyMoviesUseCase,
    val clearRecentlyMovies: ClearRecentlyMoviesUseCase,
    val getMovieGenresUseCase: GetMovieGenresUseCase,
    val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    val getUserMovieRatingUseCase: GetUserMovieRatingUseCase,
    val addMovieRatingUseCase: AddMovieRatingUseCase
)
