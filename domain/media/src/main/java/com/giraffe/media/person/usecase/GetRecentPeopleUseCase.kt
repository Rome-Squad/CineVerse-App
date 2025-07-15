package com.giraffe.media.person.usecase

import com.giraffe.media.person.exception.RecentPeopleNotFoundException
import com.giraffe.media.person.repository.PersonRepository

class GetRecentPeopleUseCase(
    private val repository: PersonRepository
) {
    suspend operator fun invoke() = repository.getRecentPeople()
}