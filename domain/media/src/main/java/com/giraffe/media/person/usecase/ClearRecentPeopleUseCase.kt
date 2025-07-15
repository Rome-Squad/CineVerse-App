package com.giraffe.media.person.usecase

import com.giraffe.media.person.repository.PersonRepository

class ClearRecentPeopleUseCase(
    private val repository: PersonRepository
) {
    suspend operator fun invoke() = repository.clearRecentPeople()
}