package com.giraffe.person.usecase

import com.giraffe.person.repository.PersonRepository

class ClearRecentPeopleUseCase(
    private val repository: PersonRepository
) {
    suspend operator fun invoke() = repository.clearRecentPeople()
}