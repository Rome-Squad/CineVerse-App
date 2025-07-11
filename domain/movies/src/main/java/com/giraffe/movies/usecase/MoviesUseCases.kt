package com.giraffe.movies.usecase

data class MoviesUseCases(
    val clearCacheUseCase: ClearCacheUseCase,
    val insertMoviesUseCase: InsertMoviesUseCase,
    val insertGenresUseCase: InsertGenresUseCase,
    val searchMovieByNameUseCase: SearchMovieByNameUseCase,
    val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
    val getMovieGenresUseCase: GetMovieGenresUseCase,
    val setMovieRecentUseCase: SetMovieRecentUseCase
)
