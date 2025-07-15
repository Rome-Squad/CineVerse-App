package com.giraffe.movies.usecase

import com.giraffe.movies.entity.Movie
import com.giraffe.movies.repository.MoviesRepository

class GetMovieDetailsUseCase (
    private val repository : MoviesRepository
){
    suspend operator fun invoke(movieId : Int) : Movie {
        return repository.getMovieDetails(movieId)
    }
}