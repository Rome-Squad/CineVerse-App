package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository

class GetMovieDetailsUseCase (
    private val repository : MoviesRepository
){
    suspend operator fun invoke(movieId : Int) : Movie {
        return repository.getMovieDetails(movieId)
    }
}