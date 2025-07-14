package com.giraffe.movies.usecase

import com.giraffe.movies.entity.AccountStates
import com.giraffe.movies.repository.MoviesRepository

class GetAccountStatesUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int): AccountStates {
        return repository.getAccountStates(movieId)
    }
}