package com.giraffe.movies.usecase

import com.giraffe.movies.repository.MoviesRepository

class ClearRecentlyMoviesUseCase (
    private val repository: MoviesRepository
){
    suspend operator fun invoke(){
        repository.clearRecentlyMovies()
    }
}