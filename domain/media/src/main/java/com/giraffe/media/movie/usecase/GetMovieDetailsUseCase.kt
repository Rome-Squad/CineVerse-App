package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.repository.MoviesRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int): Movie {
        return repository.getMovieDetails(movieId)
    }
}