package com.giraffe.movies.usecase

import com.giraffe.movies.repository.MoviesRepository

class CreateGuestSessionUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(): String{
        return repository.createGuestSession()
    }
}