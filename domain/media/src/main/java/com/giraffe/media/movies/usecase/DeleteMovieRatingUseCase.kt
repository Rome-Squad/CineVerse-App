 package com.giraffe.media.movies.usecase
import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject

 class DeleteMovieRatingUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int) = moviesRepository.deleteMovieRating(movieId)
}