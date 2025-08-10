package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MoviesRepository
import com.giraffe.user.usecase.GetUserUseCase
import javax.inject.Inject

class GetRatedMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository,
    private val getUserUseCase: GetUserUseCase
) {
    suspend operator fun invoke() = repository.getRatedMovies(getUserUseCase().id)
}