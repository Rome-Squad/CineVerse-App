package com.giraffe.person.usecase

import com.giraffe.person.entity.Person
import com.giraffe.person.repository.PersonRepository

class GetPeopleByShowIdUseCase(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(showId: Int): List<Person> {
        return repository.getPeopleByShowId(showId)
    }
}