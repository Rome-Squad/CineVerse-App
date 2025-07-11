package com.giraffe.person.usecase

import com.giraffe.person.exception.RecentPeopleNotFoundException
import com.giraffe.person.repository.PersonRepository

class GetRecentPeopleUseCase(
    private val repository: PersonRepository
) {
    suspend operator fun invoke() = repository.getRecentPeople().ifEmpty {
        throw RecentPeopleNotFoundException()
    }
}